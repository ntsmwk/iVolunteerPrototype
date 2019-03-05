import { Component, Input, OnInit, Output, EventEmitter }  from '@angular/core';
import { FormGroup }                 from '@angular/forms';
 
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

  @Output() resultEvent: EventEmitter<any> = new EventEmitter();
 
  constructor(private qcs: QuestionControlService) {  }
 
  ngOnInit() {
    this.form = this.qcs.toFormGroup(this.questions);
    if (this.formDisabled) {
      console.log("Disabling form");
      this.form.disable();
    }
  }
 
  onSubmit() {
    this.form.updateValueAndValidity();

    if (this.form.valid) {
      this.output = JSON.stringify(this.form.value);
      

      console.log("Values")
      console.log(this.form.value);
  
      this.fireResultEvent();
      
    } else {
      console.log("not valid - try again");

      let firstKey: string;
      
      //Mark errornous Fields
      Object.keys(this.form.controls).forEach(key => {
        this.form.get(key).markAsTouched();
        if (isNullOrUndefined(firstKey)) {
          firstKey = key;
        }
      });

      //focus on first error using jQuery
      $('input.ng-invalid').first().focus();


      
      
    }



  }

  fireResultEvent() {
    this.resultEvent.emit(this.form);
  }

  navigateBack() {
    window.history.back();
  }

  removeProperty(question: QuestionBase<any>) {
    console.log("clicked Remove Property")
  }
}