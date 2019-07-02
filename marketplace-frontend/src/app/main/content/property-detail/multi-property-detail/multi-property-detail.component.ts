import { Component, OnInit, Input } from '@angular/core';
import { Property } from '../../_model/properties/Property';

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
    return Property.getValue(property);
  }

}
