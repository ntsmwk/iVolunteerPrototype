import {Component} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';
import { GroupService } from '../_service/group.service';
import { Group } from '../_model/group';

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
        'project': 'Project Y', 'date': '10.05.2018', 'volunteers': [{'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg'},
          {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'}]
      }
    ]
  };

  groups;

  friends = {
    'follower': [
      {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg'},
      {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg'},
      {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'}
    ],
    'following': [
      {'name': 'Alice Newman', 'avatar': 'assets/images/avatars/alice.jpg'},
      {'name': 'Andrew Miller', 'avatar': 'assets/images/avatars/andrew.jpg'},
      {'name': 'James Houser', 'avatar': 'assets/images/avatars/james.jpg'},
      {'name': 'Jane Doe', 'avatar': 'assets/images/avatars/jane.jpg'},
      {'name': 'Joyce Springfield', 'avatar': 'assets/images/avatars/joyce.jpg'},
      {'name': 'Katherine Green', 'avatar': 'assets/images/avatars/katherine.jpg'},
      {'name': 'Vincent Killdow', 'avatar': 'assets/images/avatars/vincent.jpg'}
    ]
  };

  constructor(private groupService: GroupService) {
    this.groups = groupService.getGroups().toPromise().then<Group[]>();
    console.log(this.groups);

  }
}
