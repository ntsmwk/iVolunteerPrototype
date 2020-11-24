import { Component, OnInit, Input } from '@angular/core';
import { ComputedFormItem } from 'app/main/content/_model/dynamic-forms/item';
import { FormGroup } from '@angular/forms';


declare var $: JQueryStatic;

@Component({
  selector: 'app-computed-item',
  templateUrl: './computed-item.component.html',
  styleUrls: ['./computed-item.component.scss'],

})
export class ComputedItemComponent implements OnInit {

  @Input() formItem: ComputedFormItem;
  @Input() form: FormGroup;

  loaded: boolean;

  constructor() { }


  ngOnInit() {
    this.loaded = false;
    this.loaded = true;
  }
}
