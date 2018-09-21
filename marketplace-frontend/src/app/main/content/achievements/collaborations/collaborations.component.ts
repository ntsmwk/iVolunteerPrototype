import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss'],
  animations: fuseAnimations

})
export class CollaborationsComponent implements OnInit {
  about = {
    'friends': [
      {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg', 'number': '6', 'hours': '12,4'},
      {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/mweissenbek.jpg', 'number': '5', 'hours': '6,4'},
      {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg', 'number': '2', 'hours': '1,4'}
    ]
  };

  constructor() { }

  ngOnInit() {
  }

}
