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
        'project': 'Project X', 'date': '10.09.2018', 'volunteers': [{'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'},
          {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg'},
          {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg'}]
      },
      {
        'project': 'Project Y', 'date': '10.05.2018', 'volunteers': [{'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'},
          {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg'}]
      }


    ]
  };

  constructor() {

  }
}
