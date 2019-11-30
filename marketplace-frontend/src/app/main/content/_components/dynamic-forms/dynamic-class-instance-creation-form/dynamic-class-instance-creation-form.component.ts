import { Component, Input, OnInit, Output, EventEmitter }  from '@angular/core';
import { FormGroup, AbstractControl }                 from '@angular/forms';
 
import { QuestionBase }              from '../../../_model/dynamic-forms/questions';
import { QuestionControlService }    from '../../../_service/question-control.service';
import { isNullOrUndefined } from 'util';
import { FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
declare var $:JQueryStatic;

@Component({
  selector: 'app-dynamic-class-instance-creation-form',
  templateUrl: './dynamic-class-instance-creation-form.component.html',
  providers: [ QuestionControlService ]
})
export class DynamicClassInstanceCreationFormComponent implements OnInit {
 
  @Input() questions: QuestionBase<any>[] = [];
  @Input() formDisabled: boolean;
  @Input() formConfigurationId: string;
  @Input() form: FormGroup;
  
  
  output = '';
  submitPressed: boolean;

  @Output() resultEvent: EventEmitter<any> = new EventEmitter();
  @Output() cancelEvent: EventEmitter<any> = new EventEmitter();
 
  constructor(private qcs: QuestionControlService) {  }
 
  ngOnInit() {
    console.log("test");
    if (!isNullOrUndefined(this.form)) {
      this.form = this.qcs.toFormGroup(this.questions);
    }

    if (this.formDisabled) {
      console.log("Disabling form");
      this.form.disable();
    }

    this.submitPressed = false;
  }
 
  onSubmit() {
    this.submitPressed = true;
    this.form.updateValueAndValidity();

    if (this.form.valid) {
      this.output = JSON.stringify(this.form.value);
      

      console.log(this.form.value);
  
      this.form.disable();
      this.fireResultEvent();
      
    } else {
      console.log("not valid - try again");

      let firstKey: string;
      
      //Mark errornous Fields
      this.markFormAsTouched(this.questions, this.form);
      

      //focus on first error using jQuery
      $('input.ng-invalid').first().focus();

    }
  }

  private markFormAsTouched(questions: QuestionBase<any>[], control: AbstractControl) {
    for (let q of questions) {
      // console.log("Q: " + q.key);
      // console.log(q);
      // console.log("========");
      control.get(q.key).markAsTouched()
      if (q.controlType == 'multiple' && !isNullOrUndefined(q.subQuestions)) {
        this.markFormAsTouched(q.subQuestions, control.get(q.key));
      }
    }    

  }

  fireResultEvent() {
    this.resultEvent.emit(new FormEntryReturnEventData(this.form, this.formConfigurationId));
  }

  navigateBack() {
    window.history.back();
  }

  removeProperty(question: QuestionBase<any>) {
    console.log("clicked Remove Property")
  }
}