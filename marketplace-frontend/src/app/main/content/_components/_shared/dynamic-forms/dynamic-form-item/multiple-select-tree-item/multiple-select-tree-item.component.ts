import { Component, OnInit, Input } from '@angular/core';
import { DynamicFormItemBase } from 'app/main/content/_model/dynamic-forms/item';

declare var $: JQueryStatic;

@Component({
  selector: 'app-multiple-select-tree-item',
  templateUrl: './multiple-select-tree-item.component.html',
  styleUrls: ['./multiple-select-tree-item.component.scss'],

})
export class MultipleSelectTreeItemComponent implements OnInit {

  @Input() formItem: DynamicFormItemBase<any>;

  constructor() { }

  ngOnInit() {

  }

  calculateSpaces(level: number) {
    level = 10 * level;

    return level + 'px';
  }

}
