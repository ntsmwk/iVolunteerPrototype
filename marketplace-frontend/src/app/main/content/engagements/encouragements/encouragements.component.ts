import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-encouragements',
  templateUrl: './encouragements.component.html',
  styleUrls: ['./encouragements.component.scss'],
  animations: fuseAnimations

})
export class EncouragementsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
