import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss'],
  animations: fuseAnimations

})
export class CollaborationsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
