import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl }        from '@angular/forms';
 
import { QuestionBase }     from '../../../_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { QuestionService } from 'app/main/content/_service/question.service';

 
@Component({
  selector: 'app-question',
  templateUrl: './dynamic-form-question.component.html',
  styleUrls: ['./dynamic-form-question.component.scss']
})
export class DynamicFormQuestionComponent implements OnInit{
  @Input() question: QuestionBase<any>;

  @Input() form: FormGroup;
  date: FormControl;

  get isValid() { return this.form.controls[this.question.key].valid; }

  ngOnInit() {
//    this.prepareDatePicker();
    if (this.question.controlType === 'slidetoggle') {
     // this.form.controls[this.question.key].setValue(this.question.value);
    }
    
  }
  
  prepareDatePicker() {

    if (this.question.controlType === 'datepicker' && !isNullOrUndefined(this.question.value)) {
      this.date = new FormControl(this.question.value);



      this.form.setControl(this.question.key, this.date);
    }
  }
   

}