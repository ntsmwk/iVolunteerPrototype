import { Component, OnInit, Input } from '@angular/core';
import { LocationFormItem } from 'app/main/content/_model/dynamic-forms/item';
import { FormGroup } from '@angular/forms';

declare var $: JQueryStatic;

@Component({
  selector: 'app-location-item',
  templateUrl: './location-item.component.html',
  styleUrls: ['./location-item.component.scss'],

})
export class LocationItemComponent implements OnInit {

  @Input() formItem: LocationFormItem;
  @Input() form: FormGroup;

  locationGroup: FormGroup;
  loaded: boolean;

  constructor() { }


  ngOnInit() {
    this.loaded = false;
    this.locationGroup = this.form.controls[this.formItem.key] as FormGroup;
    this.loaded = true;
  }

  handleLongLatEnabledChange() {

  }


}
