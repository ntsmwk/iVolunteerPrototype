import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { DynamicFormItemBase } from '../../../../_model/dynamic-forms/item';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-form-item',
  templateUrl: './dynamic-form-item.component.html',
  styleUrls: ['./dynamic-form-item.component.scss'],
})
export class DynamicFormItemComponent implements OnInit {
  @Input() formItem: DynamicFormItemBase<any>;
  @Input() form: FormGroup;
  @Input() submitPressed: boolean;
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter<any>();

  date: FormControl;
  requiredMarker: string;
  errorMessage: string;

  expanded: boolean;

  get isValid() {
    return this.form.controls[this.formItem.key].valid;
  }

  ngOnInit() {
    if (this.formItem.required) {
      this.requiredMarker = '*';
    }
    this.expanded = false;
  }

  calculateSpaces(level: number) {
    level = 10 * level;
    return level + 'px';
  }

  displayErrorMessage() {
    return this.form.controls[this.formItem.key].hasError('required') ? this.getErrorMessage('required') :
      this.form.controls[this.formItem.key].hasError('requiredtrue') ? this.getErrorMessage('requiredtrue') :
        this.form.controls[this.formItem.key].hasError('pattern') ? this.getErrorMessage('pattern') :
          this.form.controls[this.formItem.key].hasError('minlength') ? this.getErrorMessage('minlength') :
            this.form.controls[this.formItem.key].hasError('maxlength') ? this.getErrorMessage('maxlength') :
              this.form.controls[this.formItem.key].hasError('max') ? this.getErrorMessage('max') :
                this.form.controls[this.formItem.key].hasError('min') ? this.getErrorMessage('min') :
                  this.form.controls[this.formItem.key].hasError('mindate') ? this.getErrorMessage('mindate') :
                    this.form.controls[this.formItem.key].hasError('requiredother') ? this.getErrorMessage('requiredother') :
                      this.form.controls[this.formItem.key].hasError('maxother') ? this.getErrorMessage('maxother') :
                        this.form.controls[this.formItem.key].hasError('minother') ? this.getErrorMessage('minother') :
                          '';
  }

  private getRemainingLength(errorName: string) {
    return this.getRequiredLength(errorName) - this.getActualLength(errorName);
  }

  private getRequiredLength(errorName: string) {
    return this.form.controls[this.formItem.key].getError(errorName).requiredLength;
  }

  private getActualLength(errorName: string) {
    return this.form.controls[this.formItem.key].getError(errorName).actualLength;
  }

  private getPattern() {
    return this.form.controls[this.formItem.key].getError('pattern').requiredPattern;
  }

  private getErrorMessage(errorName: string) {
    if (isNullOrUndefined(this.formItem.messages)) {
      return this.getStandardMessage(errorName);
    }
    const msg = this.formItem.messages.get(errorName);

    if (!isNullOrUndefined(msg)) {
      return msg;
    } else {
      return this.getStandardMessage(errorName);
    }
  }

  private getStandardMessage(errorName: string) {

    switch (errorName) {
      case 'required':
        return 'You must enter a value';
      case 'requiredtrue':
        return this.formItem.label + ' has to be true';
      case 'pattern':
        return 'Not a valid ' + this.formItem.label + ' -- Requried Pattern: ' + this.getPattern();
      case 'minlength':
        return 'String not long enough - minimum length: ' + this.getRequiredLength('minlength') + ' characters required: ' + this.getRemainingLength('minlength');
      case 'maxlength':
        return 'String too long - maximum length: ' + this.getRequiredLength('maxlength') + 'characters to remove: ' + (this.getRemainingLength('maxlength') * -1);
      case 'min':
        return 'Value below minimum';
      case 'max':
        return 'Value exceeds maximum';
      case 'mindate':
        return 'Invalid Date';
      case 'requiredother':
        return `Field '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('requiredother').keyThis) +
          `' requires '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('requiredother').keyOther) + `' to be filled in`;
      case 'maxother':
        return `Value ` + this.form.controls[this.formItem.key].getError('maxother').valueOther +
          ` in Field '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('maxother').keyOther) +
          `' exceeds ` + this.form.controls[this.formItem.key].getError('maxother').valueThis +
          ` in Field '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('maxother').keyThis) +
          `' `;
      case 'minother':
        return `Value ` + this.form.controls[this.formItem.key].getError('minother').valueOther +
          ` in Field '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('minother').keyOther) +
          `' is below ` + this.form.controls[this.formItem.key].getError('minother').valueThis +
          ` in Field '` + this.getFormItemLabel(this.form.controls[this.formItem.key].getError('minother').keyThis) +
          `' `;
      default:
        return '';

    }
  }

  getNestedFormGroups() {
    return this.form.get(this.formItem.key);
  }

  private getFormItemLabel(key: string): string {
    const ret: string = this.formItem.subItems.find((q: DynamicFormItemBase<any>) => {
      return q.key === key;
    }).label;

    return ret;
  }

  handleTupleSelection(opt: any) {
    this.tupleSelected.emit({ selection: opt, formGroup: this.form });
  }


}
