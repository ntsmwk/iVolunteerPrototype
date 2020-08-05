import { Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { SingleSelectionEnumFormItem } from 'app/main/content/_model/dynamic-forms/item';
import { isNullOrUndefined } from 'util';
import { FormGroup } from '@angular/forms';
import { EnumEntry } from 'app/main/content/_model/meta/enum';
import { MatTableDataSource } from '@angular/material';


declare var $: JQueryStatic;

@Component({
  selector: 'app-single-enum-item',
  templateUrl: './single-enum-item.component.html',
  styleUrls: ['./single-enum-item.component.scss'],

})
export class SingleEnumItemComponent implements OnInit, AfterViewInit {

  @Input() formItem: SingleSelectionEnumFormItem;
  @Input() form: FormGroup;

  listOptions: EnumEntry[];
  showList: boolean;
  datasource: MatTableDataSource<EnumEntry> = new MatTableDataSource();

  constructor(private renderer: Renderer2) { }

  @ViewChild('enumListContainer', { static: true }) listContainerDom: ElementRef;
  @ViewChild('enumFormItemContainer', { static: true }) formItemContainerDom: ElementRef;



  ngOnInit() {
    this.listOptions = [];
    this.listOptions.push(...this.formItem.options);
    this.datasource.data = this.listOptions;
  }

  ngAfterViewInit() {
    this.listContainerDom.nativeElement.style.display = 'none';
  }

  onShowList() {
    this.listContainerDom.nativeElement.style.display = '';
    this.renderer.removeClass(this.formItemContainerDom.nativeElement, 'form-item-container-closed');
    this.renderer.addClass(this.formItemContainerDom.nativeElement, 'form-item-container-open');
    this.showList = true;
  }

  onHideList() {
    this.listContainerDom.nativeElement.style.display = 'none';
    this.renderer.removeClass(this.formItemContainerDom.nativeElement, 'form-item-container-open');
    this.renderer.addClass(this.formItemContainerDom.nativeElement, 'form-item-container-closed');

    this.showList = false;
  }

  onSelectOption(option: EnumEntry) {
    this.formItem.value = option;
    this.onHideList();
  }

  onSelectClear() {
    this.formItem.value = null;
    this.onHideList();
  }

  getFormItemValue() {
    return isNullOrUndefined(this.formItem.value) ? null : this.formItem.value.value;
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

}
