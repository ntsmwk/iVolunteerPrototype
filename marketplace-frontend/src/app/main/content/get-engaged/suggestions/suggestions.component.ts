import {Component, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.scss'],
  animations: fuseAnimations

})
export class FuseSuggestionsComponent implements OnInit {
  radioOptions = 'test1';

  marketplaces = {
    'suggested': [
      {'name': 'Marketplace 1', 'description': 'Description of Marketplace 1'},
      {'name': 'Marketplace 2', 'description': 'Description of Marketplace 2'},
      {'name': 'Marketplace 3', 'description': 'Description of Marketplace 3'},
      {'name': 'Marketplace 4', 'description': 'Description of Marketplace 4'}

    ]
  };

  projects = {
    'projects': [
      {
        'name': 'Project 1',
        'description': 'Description of Project 1',
        'marketplace': 'Marketplace 1',
        'startDate': '10.08.2018  07:00',
        'endDate': '10.08.2018  18:00',
        'tasks': [{'name': 'Task 1'}, {'name': 'Task 2'}]
      },
      {
        'name': 'Project 2',
        'description': 'Description of Project 1',
        'marketplace': 'Marketplace 1',
        'startDate': '21.08.2018  08:00',
        'endDate': '23.08.2018  12:00',
        'tasks': [{'name': 'Task 1'}, {'name': 'Task 2'}]
      }

    ]
  };

  constructor() {
  }

  ngOnInit() {
  }

}
