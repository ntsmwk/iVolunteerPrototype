import {Component, Input, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../../../@fuse/animations';
import {Project} from '../../../../_model/project';

@Component({
  selector: 'fuse-contributions',
  templateUrl: './contributions.component.html',
  styleUrls: ['./contributions.component.scss'],
  animations: fuseAnimations

})
export class ContributionsComponent implements OnInit {
  @Input('projects')
  public projects: Array<Project>;

  dummyProjects = {
    'project1':
      {'name': 'Project A'}
  }

  me = {'name': 'You',
        'avatar': 'assets/images/avatars/profile.jpg',
        'time': '20,8',
        'resources': [{'name': 'Resource B'}, {'name': 'Resource C'}]
  };

  friends = {
    'friends': [
      {'name': 'Danielle Jackson',
        'avatar': 'assets/images/avatars/danielle.jpg',
        'time': '14,5',
        'resources': [{'name': 'Resource A'}]
      },
      {
        'name': 'Garry Newman',
        'avatar': 'assets/images/avatars/garry.jpg',
        'time': '12,0',
        'resources': [{'name': 'Resource B'}, {'name': 'Resource C'}]
      },
      {
        'name': 'James Houser',
        'avatar': 'assets/images/avatars/james.jpg',
        'time': '8,2',
        'resources': [{'name': 'Resource C'}]
      },
      {
        'name': 'Carl Henderson',
        'avatar': 'assets/images/avatars/carl.jpg',
        'time': '4,0',
        'resources': [{'name': 'Resource A'}, {'name': 'Resource B'}]
      }

    ]};


  constructor() { }

  ngOnInit() {
  }

}
