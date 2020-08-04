import { Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { QuestionBase, SingleSelectionEnumQuestion } from 'app/main/content/_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { FormGroup } from '@angular/forms';
import { EnumEntry } from 'app/main/content/_model/meta/enum';
import { MatTableDataSource } from '@angular/material';


declare var $: JQueryStatic;

@Component({
  selector: 'app-single-enum',
  templateUrl: './single-enum.component.html',
  styleUrls: ['./single-enum.component.scss'],

})
export class SingleEnumComponent implements OnInit, AfterViewInit {

  @Input() question: SingleSelectionEnumQuestion;
  @Input() form: FormGroup;

  listOptions: EnumEntry[];
  showList: boolean;
  datasource: MatTableDataSource<EnumEntry> = new MatTableDataSource();

  constructor(private renderer: Renderer2) { }

  @ViewChild('enumListContainer', { static: true }) listContainerDom: ElementRef;
  @ViewChild('enumQuestionContainer', { static: true }) questionContainerDom: ElementRef;



  ngOnInit() {
    this.listOptions = [];
    this.listOptions.push(...this.question.options);
    this.datasource.data = this.listOptions;
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
    this.question.value = option;
    this.form.controls[this.question.key].setValue(option); 
    this.onHideList();
  }

  onSelectClear() {
    this.question.value = null;
    this.onHideList();
  }

  getQuestionValue() {
    return isNullOrUndefined(this.question.value) ? null : this.question.value.value;
  }

  calculateSpaces(level: number) {
    level = 8 * level + 8;

    return level + 'px';
  }

  calculateIndent(level: number) {
    return level + 'px';
  }

  getChevrons(level: number) {
    let s = '>';
    s = s.repeat(level);
    return s;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
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
