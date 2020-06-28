import { Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
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

  showList: boolean;

  constructor(private renderer: Renderer2) { }

  @ViewChild('enumListContainer', { static: true }) listContainerDom: ElementRef;
  @ViewChild('enumQuestionContainer', { static: true }) questionContainerDom: ElementRef;



  ngOnInit() {
  }

  ngAfterViewInit() {
    this.listContainerDom.nativeElement.style.display = 'none';
  }

  onShowList() {
    this.listContainerDom.nativeElement.style.display = '';
    this.renderer.removeClass(this.questionContainerDom.nativeElement, 'question-container-closed');
    this.renderer.addClass(this.questionContainerDom.nativeElement, 'question-container-open');
    this.showList = true;
  }

  onHideList() {
    this.listContainerDom.nativeElement.style.display = 'none';
    this.renderer.removeClass(this.questionContainerDom.nativeElement, 'question-container-open');
    this.renderer.addClass(this.questionContainerDom.nativeElement, 'question-container-closed');

    this.showList = false;
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
