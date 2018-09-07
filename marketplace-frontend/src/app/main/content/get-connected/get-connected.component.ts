import {Component} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';

@Component({
  templateUrl: './get-connected.component.html',
  styleUrls: ['./get-connected.component.scss'],
  animations: fuseAnimations
})
export class FuseGetConnectedComponent {

  about = {
    'workedWith': [
      {
        'project': 'Project X', 'volunteers': [{'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'},
          {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/mweissenbek.jpg'},
          {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg'}]
      },
      {
        'project': 'Project Y', 'volunteers': [{'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'},
          {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/mweissenbek.jpg'}]
      }


    ]
  };

  constructor() {

  }
}
