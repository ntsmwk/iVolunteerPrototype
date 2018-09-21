import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-contributions',
  templateUrl: './contributions.component.html',
  styleUrls: ['./contributions.component.scss'],
  animations: fuseAnimations

})
export class ContributionsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
