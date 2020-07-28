import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Form, FormControl } from '@angular/forms';

import { QuestionBase } from '../_model/dynamic-forms/questions';

@Injectable()
export class QuestionControlService {

  constructor() { }

  toFormGroup(questions: QuestionBase<any>[]) {
    const fb: FormBuilder = new FormBuilder();
    const outerGroup = fb.group({});
    const array = fb.array([]);
    const innerGroup = fb.group({});
    const ret = this.addChildToGroup(fb, questions, innerGroup);
    array.push(innerGroup);

    //Test with 2nd group
    // const innerGroup2 = fb.group({});
    // this.addChildToGroup(fb, questions, innerGroup2);
    // array.push(innerGroup2);
    //....

    outerGroup.setControl("entries", array);
    // console.log(ret.controls);
    return outerGroup;
  }

  getControlForSubEntry(questions: QuestionBase<any>[]) {
    const fb: FormBuilder = new FormBuilder();
    return this.addChildToGroup(fb, questions, fb.group({}));
  }


  private displayFormGroup(fg: FormGroup): void {

    console.log("DISPLAYING FORMGROUP: ");
    console.log("RAW: ");
    console.log(fg);

    console.log("CONTROLS: ");
    console.log(fg.controls);

    console.log("VALUES");
    console.log(fg.value);

  }

  //step into questions recursively, and create FormGroup according to question-layout
  // private addChildToGroup(fb: FormBuilder, questions: QuestionBase<any>[], parent: FormGroup): FormGroup {
  private addChildToGroup(fb: FormBuilder, questions: QuestionBase<any>[], parent: FormGroup): FormGroup {

    questions.forEach((question: QuestionBase<any>) => {
      // if (question.controlType === 'multiple') {
      //   //do nested stuff
      //   const nested = fb.group({});
      //   const ret = this.addChildToGroup(fb, question.subQuestions, nested);

      //   ret.setValidators(question.validators);
      //   parent.addControl(question.key, ret);

      // } else {
      parent.addControl(question.key, fb.control(question.value, question.validators));
      // }
    });
    return parent;
  }


}