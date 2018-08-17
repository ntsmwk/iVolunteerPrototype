import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Chart from 'chart.js';
import {fuseAnimations} from '../../../../@fuse/animations';

@Component({
  selector: 'fuse-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.scss'],
  animations: fuseAnimations

})
export class FuseAchievementsComponent implements OnInit, AfterViewInit {
  canvas: any;
  ctx: any;

  about = {
    'friends': [
      {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg', 'number': '6', 'hours': '12,4'},
      {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg', 'number': '5', 'hours': '6,4'},
      {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg', 'number': '2', 'hours': '1,4'}
    ]
  };

  badges = {
    'badges': [
      {'name': 'Badge 1', 'image': 'assets/badges/badge 1.svg', 'source': 'Marketplace 1', 'description': 'Description of badge 1'},
      {'name': 'Badge 2', 'image': 'assets/badges/badge 2.svg', 'source': 'Marketplace 1', 'description': 'Description of badge 2'},
      {'name': 'Badge 3', 'image': 'assets/badges/badge 3.svg', 'source': 'Marketplace 2', 'description': 'Description of badge 3'}
    ]
  };

  competencies = {
    'competencies': [
      {'name': 'Planning', 'marketplace': 'Marketplace 1', 'date': '28.07.2018', 'task': 'Project X, Task Y'},
      {'name': 'Leadership', 'marketplace': 'Marketplace 1', 'date': '03.08.2018', 'task': 'Project A, Task B'}
    ]
  };


  widgetFacts: any = {};
  widgets = {
    'widgetFacts': {
      'title': 'Important Stats',
      'ranges': {
        'W': 'Past Week',
        'M': 'Past Month',
        'SM': 'Past 6 Month',
        'T': 'Total'
      },
      'entries': {
        'W': [
          {
            'title': 'Marketplaces engaged',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '1'
          },
          {
            'title': 'Projects involved',
            'icon': 'assets/icons/important_stats/project.svg',

            'value': '1'
          },
          {
            'title': 'Tasks completed',
            'icon': 'assets/icons/important_stats/task.svg',
            'value': '2'
          },
          {
            'title': 'Likes received',
            'icon': 'assets/icons/important_stats/like.svg',
            'value': '3'
          },
          {
            'title': 'Personal Rating',
            'icon': 'assets/icons/important_stats/rating.svg',
            'value': '3.5'
          }
        ],
        'M': [
          {
            'title': 'Marketplaces engaged',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '2'
          },
          {
            'title': 'Projects involved',
            'icon': 'assets/icons/important_stats/project.svg',
            'value': '3'
          },
          {
            'title': 'Tasks completed',
            'icon': 'assets/icons/important_stats/task.svg',
            'value': '9'
          },
          {
            'title': 'Likes received',
            'icon': 'assets/icons/important_stats/like.svg',
            'value': '8'
          },
          {
            'title': 'Personal Rating',
            'icon': 'assets/icons/important_stats/rating.svg',
            'value': '4.0'
          }
        ],
        'SM': [
          {
            'title': 'Marketplaces engaged',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '3'
          },
          {
            'title': 'Projects involved',
            'icon': 'assets/icons/important_stats/project.svg',
            'value': '6'
          },
          {
            'title': 'Tasks completed',
            'icon': 'assets/icons/important_stats/task.svg',
            'value': '11'
          },
          {
            'title': 'Likes received',
            'icon': 'assets/icons/important_stats/like.svg',
            'value': '10'
          },
          {
            'title': 'Personal Rating',
            'icon': 'assets/icons/important_stats/rating.svg',
            'value': '4.0'
          }
        ],
        'T': [
          {
            'title': 'Marketplaces engaged',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '3'
          },
          {
            'title': 'Projects involved',
            'icon': 'assets/icons/important_stats/project.svg',
            'value': '8'
          },
          {
            'title': 'Tasks completed',
            'icon': 'assets/icons/important_stats/task.svg',
            'value': '14'
          },
          {
            'title': 'Likes received',
            'icon': 'assets/icons/important_stats/like.svg',
            'value': '18'
          },
          {
            'title': 'Personal Rating',
            'icon': 'assets/icons/important_stats/rating.svg',
            'value': '4.5'
          }
        ]
      }
    }
  };

  finishedProjects = {
    'finishedProjects': [
      {'name': 'Project L', 'task1': 'Task 1', 'task2': 'Task 2'},
      {'name': 'Project X', 'task1': 'Task 4'},
      {'name': 'Project R', 'task1': 'Task 2', 'task2': 'Task 6'},

    ]
  };


  constructor() {
    this.widgetFacts = {
      currentRange: 'W'
    };
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    const myChart = new Chart(this.ctx, {
      type: 'line',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [{
          label: 'Marketplace 1',
          data: [0, 0, 0, 3, 4, 6, 5, 2, 1, 1, 1, 1],
          borderColor: '#3e95cd',
          fill: false
        }, {
          label: 'Marketplace 2',
          data: [3, 2, 1, 5, 4, 4, 4, 2, 0, 0, 0, 0],
          borderColor: '#8e5ea2',
          fill: false
        }, {
          label: 'Marketplace 3',
          data: [1, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4, 6],
          borderColor: '#c45850',
          fill: false
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });
  }

}

