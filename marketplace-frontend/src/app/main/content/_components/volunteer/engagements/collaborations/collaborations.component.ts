import {Component, Input, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../../../@fuse/animations';
import {Project} from '../../../../_model/project';

@Component({
  selector: 'fuse-collaborations',
  templateUrl: './collaborations.component.html',
  styleUrls: ['./collaborations.component.scss'],
  animations: fuseAnimations

})
export class CollaborationsComponent implements OnInit {
  @Input('projects')
  public projects: Array<Project>;

  dummyProjects = {
    'project1':
      {'name': 'Project A'}
  }

  me = {'name': 'You', 'avatar': 'assets/images/avatars/profile.jpg'};

  friends = {
    'follower': [
      {'name': 'Danielle Jackson',
        'avatar': 'assets/images/avatars/danielle.jpg',
        'groups': ''},
      {
        'name': 'Garry Newman',
        'avatar': 'assets/images/avatars/garry.jpg',
        'groups': [{'name': 'Group 1', 'avatar': 'assets/images/backgrounds/spring.jpg'}, {'name': 'Group 3', 'avatar': 'assets/images/backgrounds/autumn.jpg'}]
      }

    ],
    'following': [
      {'name': 'Vincent Killdow',
        'avatar': 'assets/images/avatars/vincent.jpg',
        'groups': ''},
      {
        'name': 'Katherine Green',
        'avatar': 'assets/images/avatars/katherine.jpg',
        'groups': [{'name': 'Group 1', 'avatar': 'assets/images/backgrounds/spring.jpg'}, {'name': 'Group 2', 'avatar': 'assets/images/backgrounds/winter.jpg'}]
      },
      {
        'name': 'James Houser',
        'avatar': 'assets/images/avatars/james.jpg',
        'groups': [{'name': 'Group 2', 'avatar': 'assets/images/backgrounds/winter.jpg'}]
      }

    ],
    'others': [
      {
        'name': 'Carl Henderson',
        'avatar': 'assets/images/avatars/carl.jpg',
        'groups': ''
      }
    ]
  };


  constructor() {
  }

  ngOnInit() {

  }

}
