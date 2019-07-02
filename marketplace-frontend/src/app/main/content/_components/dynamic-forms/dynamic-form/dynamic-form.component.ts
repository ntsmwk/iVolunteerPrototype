import { Component, Input, OnInit, Output, EventEmitter }  from '@angular/core';
import { FormGroup, AbstractControl }                 from '@angular/forms';
 
import { QuestionBase }              from '../../../_model/dynamic-forms/questions';
import { QuestionControlService }    from '../../../_service/question-control.service';
import { isNullOrUndefined } from 'util';
declare var $:JQueryStatic;

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  providers: [ QuestionControlService ]
})
export class DynamicFormComponent implements OnInit {
 
  @Input() questions: QuestionBase<any>[] = [];
  @Input() formDisabled: boolean;
  
  form: FormGroup;
  output = '';
  submitPressed: boolean;

  @Output() resultEvent: EventEmitter<any> = new EventEmitter();
 
  constructor(private qcs: QuestionControlService) {  }
 
  ngOnInit() {
    this.form = this.qcs.toFormGroup(this.questions);


    if (this.formDisabled) {
      this.form.disable();
    }

    this.submitPressed = false;
  }
 
  onSubmit() {
    this.submitPressed = true;
    this.form.updateValueAndValidity();

    if (this.form.valid) {
      this.output = JSON.stringify(this.form.value);
      this.fireResultEvent();
      
    } else {
      let firstKey: string;
      
      //Mark errornous Fields
      this.markFormAsTouched(this.questions, this.form);
      

      //focus on first error using jQuery
      $('input.ng-invalid').first().focus();

    }
  }

  private markFormAsTouched(questions: QuestionBase<any>[], control: AbstractControl) {
    for (let q of questions) {
      control.get(q.key).markAsTouched()
      if (q.controlType == 'multiple' && !isNullOrUndefined(q.subQuestions)) {
        this.markFormAsTouched(q.subQuestions, control.get(q.key));
      }
    }    

  }

  fireResultEvent() {
    this.resultEvent.emit(this.form);
  }

  navigateBack() {
    window.history.back();
  }

}