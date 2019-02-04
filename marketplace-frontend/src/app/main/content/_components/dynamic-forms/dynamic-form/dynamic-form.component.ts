import { Component, Input, OnInit, Output, EventEmitter }  from '@angular/core';
import { FormGroup }                 from '@angular/forms';
 
import { QuestionBase }              from '../../../_model/dynamic-forms/questions';
import { QuestionControlService }    from '../../../_service/question-control.service';

 
@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  providers: [ QuestionControlService ]
})
export class DynamicFormComponent implements OnInit {
 
  @Input() questions: QuestionBase<any>[] = [];
  @Input() disabled: boolean;
  
  form: FormGroup;
  payLoad = '';

  @Output() resultEvent: EventEmitter<any> = new EventEmitter();
 
  constructor(private qcs: QuestionControlService) {  }
 
  ngOnInit() {
    this.form = this.qcs.toFormGroup(this.questions);
    if (this.disabled) {
      console.log("Disabling form");
      this.form.disable();
    }
  }
 
  onSubmit() {
    this.payLoad = JSON.stringify(this.form.value);

    console.log("Values")
    console.log(this.form.value);

    this.fireResultEvent();


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