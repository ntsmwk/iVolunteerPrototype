import { Injectable }   from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

import { QuestionBase } from '../_model/dynamic-forms/questions';

@Injectable()
export class QuestionControlService {
  constructor() { }

  toFormGroup(questions: QuestionBase<any>[] ) {
    const fb: FormBuilder = new FormBuilder();
    const parent = fb.group({});
    const ret = this.addChildToGroup(fb, questions, parent);

    // this.displayFormGroup(ret);

    return ret;
  }

  //step into questions recursively, and create FormGroup according to question-layout
  private addChildToGroup(fb: FormBuilder, questions: QuestionBase<any>[], parent: FormGroup): FormGroup {
    questions.forEach((question: QuestionBase<any>) => {
      if (question.controlType == 'multiple') {
        //do nested stuff
        const nested = fb.group({});
        const ret = this.addChildToGroup(fb, question.subQuestions, nested);

        ret.setValidators(question.validators);
        parent.addControl(question.key, ret);

      } else {
        parent.addControl(question.key, fb.control(question.value, question.validators));
      }
    });
    return parent;
  }

 
}