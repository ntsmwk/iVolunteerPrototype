import { Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { FormGroup } from '@angular/forms';
import { EnumEntry } from 'app/main/content/_model/meta/enum';


declare var $: JQueryStatic;

@Component({
  selector: 'app-single-enum',
  templateUrl: './single-enum.component.html',
  styleUrls: ['./single-enum.component.scss'],

})
export class SingleEnumComponent implements OnInit, AfterViewInit {

  @Input() question: QuestionBase<any>;
  @Input() form: FormGroup;

  showList = true;

  constructor() { }

  @ViewChild('enumListContainer', { static: true }) selectionDom: ElementRef;

  ngOnInit() {
    console.log(this.question);
    console.log(this.form);

    // this.form.get(this.question.key).disable();
  }

  ngAfterViewInit() {
    console.log(this.selectionDom);
    this.selectionDom.nativeElement.style.display = 'none';

  }

  onShowList() {
    this.selectionDom.nativeElement.style.display = '';
  }

  onHideList() {
    this.selectionDom.nativeElement.style.display = 'none';
  }

  onSelectOption(option: EnumEntry) {
    console.log(option);
    this.question.value = option;
    this.onHideList();
  }

  onSelectClear() {
    this.question.value = null;
  }

  getQuestionValue() {
    return isNullOrUndefined(this.question.value) ? null : this.question.value.value;
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
