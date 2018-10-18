import {Component, Input} from '@angular/core';

@Component({
  selector: 'fuse-dashlet',
  templateUrl: './dashlet.component.html',
  styleUrls: ['./dashlet.component.scss']
})
export class FuseDashletComponent {

  @Input('name')
  name: string;
  @Input('inEditMode')
  inEditMode: boolean;

  constructor() {
  }

}









