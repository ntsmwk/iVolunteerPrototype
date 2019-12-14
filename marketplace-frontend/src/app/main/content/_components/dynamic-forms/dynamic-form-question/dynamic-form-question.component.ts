import { Component, Input, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormControl }        from '@angular/forms';
 
import { QuestionBase, MultipleSelectionEnumQuestion }     from '../../../_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-question',
  templateUrl: './dynamic-form-question.component.html',
  styleUrls: ['./dynamic-form-question.component.scss'],
})
export class DynamicFormQuestionComponent implements OnInit{
  @Input() question: QuestionBase<any>;
  @Input() form: FormGroup;
  @Input() submitPressed: boolean;

  date: FormControl;
  requiredMarker: string;
  errorMessage: string;

  expanded: boolean;

  get isValid() { 
    return this.form.controls[this.question.key].valid; 
  }

  ngOnInit() {
    if (this.question.required) {
      this.requiredMarker = '*';
    }
    this.expanded = false;
  }
  
  prepareDatePicker() {
    if (this.question.controlType === 'datepicker' && !isNullOrUndefined(this.question.value)) {
      this.date = new FormControl(this.question.value);
      this.form.setControl(this.question.key, this.date);
    }
  } 

  calculateSpaces(level: number) {
    level = 10*level;
    return level+"px";
  }

  getMultipleValues(question: MultipleSelectionEnumQuestion) {
    let ret = '';
    
    if (!isNullOrUndefined(question.values)) {
      for (let val of question.values) {
        ret = ret + ", " + val;
      }
    }

    return ret;
  }

  
  displayErrorMessage() {
    return this.form.controls[this.question.key].hasError('required') ?  this.getErrorMessage('required'):
      this.form.controls[this.question.key].hasError('requiredtrue') ? this.getErrorMessage('requiredtrue'):
      this.form.controls[this.question.key].hasError('pattern') ? this.getErrorMessage('pattern'):
      this.form.controls[this.question.key].hasError('minlength') ? this.getErrorMessage('minlength'):
      this.form.controls[this.question.key].hasError('maxlength') ? this.getErrorMessage('maxlength'):
      this.form.controls[this.question.key].hasError('max') ? this.getErrorMessage('max'):
      this.form.controls[this.question.key].hasError('min') ? this.getErrorMessage('min'):
      this.form.controls[this.question.key].hasError('mindate') ? this.getErrorMessage('mindate'):
      this.form.controls[this.question.key].hasError('requiredother') ? this.getErrorMessage('requiredother'):
      this.form.controls[this.question.key].hasError('maxother') ? this.getErrorMessage('maxother'):
      this.form.controls[this.question.key].hasError('minother') ? this.getErrorMessage('minother'):
      '';
  }

  private getRemainingLength(errorName: string) {
    return this.getRequiredLength(errorName)-this.getActualLength(errorName);
  }

  private getRequiredLength(errorName: string) {
    return this.form.controls[this.question.key].getError(errorName).requiredLength;
  }

  private getActualLength(errorName: string) {
    return this.form.controls[this.question.key].getError(errorName).actualLength;
  }

  private getPattern() {
    return this.form.controls[this.question.key].getError('pattern').requiredPattern;
  }

  private getErrorMessage(errorName: string) {
    if (isNullOrUndefined(this.question.messages)) {
      return this.getStandardMessage(errorName);
    }
    let msg = this.question.messages.get(errorName);
   
    if (!isNullOrUndefined(msg)) {
      return msg
    } else {
      return this.getStandardMessage(errorName);
    }
  }
  
  private getStandardMessage(errorName: string) {
    
    switch(errorName) {
      case 'required': 
        return "You must enter a value";
      case 'requiredtrue':
        return this.question.label + " has to be true";
      case 'pattern':
        return 'Not a valid ' + this.question.label + ' -- Requried Pattern: ' + this.getPattern();
      case 'minlength':
        return 'String not long enough - minimum length: ' + this.getRequiredLength('minlength') + ' characters required: ' + this.getRemainingLength('minlength');
      case 'maxlength':
        return 'String too long - maximum length: ' +  this.getRequiredLength('maxlength') + 'characters to remove: ' + (this.getRemainingLength('maxlength')*-1);
      case 'min':
        return 'Value below minimum';
      case 'max':
        return 'Value exceeds maximum';
      case 'mindate':
        return 'Invalid Date';
      case 'requiredother':
        // console.log(this.form.controls[this.question.key].getError('requiredother').keyThis);
        return `Field '` +  this.getQuestionLabel(this.form.controls[this.question.key].getError('requiredother').keyThis) + `' requires '` + this.getQuestionLabel(this.form.controls[this.question.key].getError('requiredother').keyOther) + `' to be filled in`;
      case 'maxother':
        return `Value ` + this.form.controls[this.question.key].getError('maxother').valueOther + ` in Field '` + this.getQuestionLabel(this.form.controls[this.question.key].getError('maxother').keyOther) + 
              `' exceeds ` + this.form.controls[this.question.key].getError('maxother').valueThis + ` in Field '` + this.getQuestionLabel(this.form.controls[this.question.key].getError('maxother').keyThis) +
              `' `; 
      case 'minother':
          return `Value ` + this.form.controls[this.question.key].getError('minother').valueOther + ` in Field '` + this.getQuestionLabel(this.form.controls[this.question.key].getError('minother').keyOther) + 
          `' is below ` + this.form.controls[this.question.key].getError('minother').valueThis + ` in Field '` + this.getQuestionLabel(this.form.controls[this.question.key].getError('minother').keyThis) +
          `' `; 
      default:
        return '';

    }
  }

  getNestedFormGroups() {
    return this.form.get(this.question.key);
  }

  public getQuestionValue(question: QuestionBase<any>) {
    let ret = question.values[0];
    return ret
  }

  private getQuestionLabel(key: string): string {
    let ret: string = this.question.subQuestions.find((q: QuestionBase<any> ) => {
      return q.key == key;
    }).label;

    return ret;
  }


}