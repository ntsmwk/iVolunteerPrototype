import { Component, OnInit, Input } from '@angular/core';
import { Property } from '../../_model/meta/Property';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-multi-property-detail',
  templateUrl: './multi-property-detail.component.html',
  styleUrls: ['./multi-property-detail.component.scss']
})
export class MultiPropertyDetailComponent implements OnInit {
 
  @Input() property: Property<any>;

  constructor() { }

  ngOnInit() {

    
  }

  displayPropertyValue(property: Property<any>): string {    
    // if (!isNullOrUndefined(property.values) && property.values.length >= 1) {
    //   return property.values[0].value;
    // } else {
    //     return undefined;
    // }

    return Property.getValue(property);
  }

}
