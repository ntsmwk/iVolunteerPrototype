import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl }        from '@angular/forms';
 
import { QuestionBase }     from '../../../_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { QuestionService } from 'app/main/content/_service/question.service';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';


@Component({
  selector: 'app-question',
  templateUrl: './dynamic-form-question.component.html',
  styleUrls: ['./dynamic-form-question.component.scss'],
})
export class DynamicFormQuestionComponent implements OnInit{
  @Input() question: QuestionBase<any>;
  @Input() form: FormGroup;

  date: FormControl;
  requiredMarker: string;
  errorMessage: string;

  get isValid() { 
    return this.form.controls[this.question.key].valid; 
  }

  ngOnInit() {
    if (this.question.required) {
      this.requiredMarker = '*';
    }
    // console.log("=========================================================================================");
    // console.log(this.form);
    // console.log("QUESTION");
    // console.log(this.question);

    
    // console.log("form controls for" + this.question.label + ":");
    // console.log(this.form.controls);

    

         //TODO create a shared service
         //fire a value changed event
         //register the changed value and the key
         //add a listener to the validator (or question service)
         //search if key of validator == key here - update validator
         //then do this: (re-set the validators)
         
        //  this.form.controls[this.question.key].setValidators(null);
        //  this.form.controls[this.question.key].setValidators(this.question.validators);
        //  //this.form.controls[this.question.key].updateValueAndValidity();
  

    
    //console.log(this.form.controls[this.question.key].errors);

  }
  
  prepareDatePicker() {

    if (this.question.controlType === 'datepicker' && !isNullOrUndefined(this.question.value)) {
      this.date = new FormControl(this.question.value);
      this.form.setControl(this.question.key, this.date);
    }
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
        return 'Invalid Date'
      default:
        return '';
    }
  }

  getNestedFormGroups() {
    // console.log("----------------" +this.question.key + "---:");
    // console.log(this.form.get(this.question.key).get('nested1'));
    return this.form.get(this.question.key);
  }

}