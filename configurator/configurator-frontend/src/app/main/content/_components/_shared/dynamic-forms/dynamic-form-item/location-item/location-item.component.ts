import { Component, OnInit, Input, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { LocationFormItem } from 'app/main/content/_model/dynamic-forms/item';
import { isNullOrUndefined } from 'util';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TreePropertyEntry } from 'app/main/content/_model/configurator/property/tree-property';
import { MatTableDataSource } from '@angular/material';


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
  // listOptions: TreePropertyEntry[];
  // showList: boolean;
  // datasource: MatTableDataSource<TreePropertyEntry> = new MatTableDataSource();

  constructor() { }


  ngOnInit() {
    this.loaded = false;
    this.locationGroup = this.form.controls[this.formItem.key] as FormGroup;
    this.loaded = true;
  }

  handleLongLatEnabledChange() {
    // if (this.locationGroup.value.longLatEnabled) {
    //   console.log("enabled");
    //   this.locationGroup.controls['longitude'].setValidators(Validators.required);
    //   this.locationGroup.controls['latitude'].setValidators(Validators.required);
    // } else {
    //   console.log("disabled");
    //   this.locationGroup.controls['longitude'].clearValidators();
    //   this.locationGroup.controls['latitude'].clearValidators();
    // }
    // this.locationGroup.updateValueAndValidity();
  }


}
