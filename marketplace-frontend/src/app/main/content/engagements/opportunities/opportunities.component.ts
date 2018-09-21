import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-opportunities',
  templateUrl: './opportunities.component.html',
  styleUrls: ['./opportunities.component.scss'],
  animations: fuseAnimations

})
export class OpportunitiesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
