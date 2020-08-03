import { Component, OnInit, Input } from '@angular/core';
import { DynamicFormItemBase } from 'app/main/content/_model/dynamic-forms/item';
import { isNullOrUndefined } from 'util';

declare var $: JQueryStatic;

@Component({
  selector: 'app-multiple-enum',
  templateUrl: './multiple-enum.component.html',
  styleUrls: ['./multiple-enum.component.scss'],

})
export class MultipleEnumComponent implements OnInit {

  @Input() formItem: DynamicFormItemBase<any>;

  constructor() { }

  ngOnInit() {

  }

  calculateSpaces(level: number) {
    level = 10 * level;

    return level + 'px';
  }

}
