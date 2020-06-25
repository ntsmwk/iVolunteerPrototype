import { Component, OnInit, Input } from '@angular/core';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { FormGroup } from '@angular/forms';

declare var $: JQueryStatic;

@Component({
  selector: 'app-single-enum',
  templateUrl: './single-enum.component.html',
  styleUrls: ['./single-enum.component.scss'],

})
export class SingleEnumComponent implements OnInit {

  @Input() question: QuestionBase<any>;
  @Input() form: FormGroup;

  constructor() { }

  ngOnInit() {

  }

  calculateSpaces(level: number) {
    level = 10 * level;

    return level + "px";
  }

  // getMultipleValues(question: MultipleSelectionEnumQuestion) {
  //   let ret = '';



  //   if (!isNullOrUndefined(question.values)) {
  //     for (let val of question.values) {
  //       ret = ret + ", " + val;
  //     }
  //   }

  //   return ret;
  // }
}
