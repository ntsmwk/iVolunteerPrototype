import { Injectable }   from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';

import { QuestionBase } from '../_model/dynamic-forms/questions';
import { isNull, isNullOrUndefined } from 'util';

@Injectable()
export class QuestionControlService {
  constructor() { }


  //TODO use Form Builder - might be easier to get support nested FormGroups
  // toFormGroup(questions: QuestionBase<any>[] ) {
  //   let group: any = {};
  //     questions.forEach(question => {

        
  //       group[question.key] = !isNullOrUndefined(question.validators) ? new FormControl(question.value || '', question.validators) 
  //       : new FormControl(question.value || '');
        

        
  //     });

  //     console.log(group);
  //   return new FormGroup(group);
  // }


  toFormGroup(questions: QuestionBase<any>[] ) {
    const fb: FormBuilder = new FormBuilder();
    const parent = fb.group({});
    const ret = this.addChildToGroup(fb, questions, parent);

    
    console.log("ROOT created");
    console.log(ret);

    // this.displayFormGroup(ret);

    return ret;

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
  private addChildToGroup(fb: FormBuilder, questions: QuestionBase<any>[], parent: FormGroup): FormGroup {
    questions.forEach((question: QuestionBase<any>) => {
      if (question.controlType == 'multiple') {
        //do nested stuff
        const nested = fb.group({});
        // question.subQuestions.forEach((question: QuestionBase<any>) => {
        //   nested.addControl(question.key, fb.control('', question.validators));
        // });
        const ret = this.addChildToGroup(fb, question.subQuestions, nested);

        parent.addControl(question.key, ret);
        
        
        // console.log("NESTED");
        // console.log(ret);

      } else {
        parent.addControl(question.key, fb.control(question.value, question.validators));
      }
    });
    return parent;
  }

 
}