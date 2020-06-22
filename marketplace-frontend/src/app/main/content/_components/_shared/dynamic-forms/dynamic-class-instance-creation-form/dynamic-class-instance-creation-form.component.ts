import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { QuestionBase } from '../../../../_model/dynamic-forms/questions';
import { QuestionControlService } from '../../../../_service/question-control.service';
import { isNullOrUndefined } from 'util';
import { FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { trigger, state, transition, style, animate } from '@angular/animations';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import 'jquery';

declare var $: JQueryStatic;

@Component({
  selector: 'app-dynamic-class-instance-creation-form',
  templateUrl: './dynamic-class-instance-creation-form.component.html',
  providers: [QuestionControlService],
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
export class DynamicClassInstanceCreationFormComponent implements OnInit, OnChanges {

  @Input() questions: QuestionBase<any>[] = [];
  @Input() hideButtons: boolean;
  @Input() formConfigurationId: string;
  @Input() form: FormGroup;
  @Input() lastEntry: boolean;
  @Input() finishClicked: boolean;
  @Input() ignoreValidity: boolean;

  submitPressed: boolean;

  @Output() resultEvent: EventEmitter<any> = new EventEmitter();
  @Output() cancelEvent: EventEmitter<any> = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();

  constructor(private qcs: QuestionControlService,
    private classDefinitionService: ClassDefinitionService) { }

  ngOnInit() {
    if (!isNullOrUndefined(this.form)) {
      this.form = this.qcs.toFormGroup(this.questions);
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

    if (this.ignoreValidity || this.form.valid) {
      // this.form.disable();
      this.fireResultEvent();

    } else {
      // Mark errornous Fields
      this.markFormAsTouched(this.questions, this.form);


      // focus on first error using jQuery
      $('input.ng-invalid').first().focus();

    }
    this.submitPressed = false;
  }

  private markFormAsTouched(questions: QuestionBase<any>[], control: FormGroup) {
    for (const q of questions) {
      control.controls[q.key].markAsTouched();
      // if (q.controlType === 'multiple' && !isNullOrUndefined(q.subQuestions)) {
      //   this.markFormAsTouched(q.subQuestions, control.get(q.key));
      // }
    }

  }

  fireResultEvent() {
    this.resultEvent.emit(new FormEntryReturnEventData(this.form, this.formConfigurationId));
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