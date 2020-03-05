import { Component, OnInit, Input} from '@angular/core';
import { QuestionBase, MultipleSelectionEnumQuestion } from 'app/main/content/_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';

declare var $: JQueryStatic;

@Component({
  selector: 'app-multiple-enum',
  templateUrl: './multiple-enum.component.html',
  styleUrls: ['./multiple-enum.component.scss'],

})
export class MultipleEnumComponent implements OnInit {

  @Input() question: QuestionBase<any>;

  constructor() { }

  ngOnInit() {
    
  }

  calculateSpaces(level: number) {
    level = 10*level;

    return level+"px";
  }

  getMultipleValues(question: MultipleSelectionEnumQuestion) {
    let ret = '';
    

    
    if (!isNullOrUndefined(question.values)) {
      for (let val of question.values) {
        ret = ret + ", " + val;
      }
    }

    return ret;
  }
}
