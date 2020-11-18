import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { DynamicFormItemBase } from '../../../../_model/dynamic-forms/item';
import { DynamicFormItemControlService } from '../../../../_service/dynamic-form-item-control.service';
import { isNullOrUndefined } from 'util';
import { FormEntryReturnEventData, FormConfiguration } from 'app/main/content/_model/configurator/form';
import { trigger, state, transition, style, animate } from '@angular/animations';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import 'jquery';
import { isNgTemplate } from '@angular/compiler';

declare var $: JQueryStatic;

@Component({
  selector: 'app-dynamic-form-block',
  templateUrl: './dynamic-form-block.component.html',
  styleUrls: ['./dynamic-form-block.component.scss'],
  providers: [DynamicFormItemControlService],
  animations: [
    // the fade-in/fade-out animation.
    trigger('simpleFadeAnimation', [

      // the "in" style determines the "resting" state of the element when it is visible.
      state('in', style({ opacity: 1 })),

      // fade in when created. this could also be written as transition('void => *')
      transition(':enter', [
        style({ opacity: 0 }),
        animate(600)
      ]),

      // fade out when destroyed. this could also be written as transition('void => *')
      transition(':leave',
        animate(600, style({ opacity: 0 })))
    ])
  ]
})
export class DynamicFormBlockComponent implements OnInit, OnChanges {

  @Input() formItems: DynamicFormItemBase<any>[] = [];
  @Input() hideButtons: boolean;
  @Input() formConfiguration: FormConfiguration;
  @Input() form: FormGroup;
  @Input() finishClicked: boolean;

  submitPressed: boolean;

  @Output() resultEvent: EventEmitter<FormEntryReturnEventData> = new EventEmitter();
  @Output() cancelEvent: EventEmitter<any> = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();
  @Output() errorEvent: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private formItemControlService: DynamicFormItemControlService
  ) { }

  ngOnInit() {
    if (isNullOrUndefined(this.form)) {
      this.form = this.formItemControlService.toFormGroup(this.formItems);
    }

    this.submitPressed = false;
  }

  ngOnChanges() {
    if (this.finishClicked) {
      this.onSubmit();
    }
  }

  onSubmit() {
    this.submitPressed = true;
    this.form.updateValueAndValidity();

    if (this.form.valid) {

      for (const item of this.formItems) {
        if (item.controlType.startsWith('tree')) {
          this.form.controls[item.key].setValue(item.value);
        }
      }
      this.fireResultEvent();

    } else {
      // Mark errornous Fields
      this.markFormAsTouched(this.formItems, this.form);
      this.errorEvent.emit(true);

      // focus on first error using jQuery
      $('input.ng-invalid').first().focus();

    }
    this.submitPressed = false;
  }



  private markFormAsTouched(formItem: DynamicFormItemBase<any>[], control: FormGroup) {
    for (const item of formItem) {
      control.controls[item.key].markAsTouched();
    }
  }

  fireResultEvent() {
    this.resultEvent.emit(new FormEntryReturnEventData(this.formConfiguration.id, this.form.value));
  }

  handleCancel() {
    this.cancelEvent.emit('cancel');
  }

  navigateBack() {
    window.history.back();
  }

  handleTupleSelection(evt: { selection: { id: any, label: any }, formGroup: FormGroup }) {
    this.tupleSelected.emit(evt);
  }
}
