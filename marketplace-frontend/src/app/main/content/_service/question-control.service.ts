import { Injectable }   from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { QuestionBase } from '../_model/dynamic-forms/questions';
import { isNull, isNullOrUndefined } from 'util';

@Injectable()
export class QuestionControlService {
  constructor() { }

  toFormGroup(questions: QuestionBase<any>[] ) {
    let group: any = {};
      questions.forEach(question => {
        group[question.key] = !isNullOrUndefined(question.validators) ? new FormControl(question.value || '', question.validators) 
                                                                      : new FormControl(question.value || '');
      });
    
    return new FormGroup(group);
  }


 
}