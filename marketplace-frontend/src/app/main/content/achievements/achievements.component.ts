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
      {'name': 'Leadership', 'marketplace': 'Marketplace 1',  'date': '03.08.2018', 'task': 'Project A, Task B'}
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
      'schedule': {
        'W': [
          {
            'title': 'Marketplaces engaged:',
            'value' : '2'
          },
          {
            'title': 'Projects involved:',
            'value' : '2'
          },
          {
            'title': 'Tasks completed:',
            'value' : '2'
          },
          {
            'title': 'Likes received:',
            'value' : '2'
          },
          {
            'title': 'Positive feedback received:',
            'value' : '2'
          }
        ],
        'M': [
          {
            'title': 'Marketplaces engaged:',
            'value' : '3'
          },
          {
            'title': 'Projects involved:',
            'value' : '3'
          },
          {
            'title': 'Tasks completed:',
            'value' : '3'
          },
          {
            'title': 'Likes received:',
            'value' : '3'
          },
          {
            'title': 'Positive feedback received:',
            'value' : '3'
          }
        ],
        'SM': [
          {
            'title': 'Marketplaces engaged:',
            'value' : '4'
          },
          {
            'title': 'Projects involved:',
            'value' : '4'
          },
          {
            'title': 'Tasks completed:',
            'value' : '4'
          },
          {
            'title': 'Likes received:',
            'value' : '4'
          },
          {
            'title': 'Positive feedback received:',
            'value' : '4'
          }
        ],
        'T': [
          {
            'title': 'Marketplaces engaged:',
            'value' : '5'
          },
          {
            'title': 'Projects involved:',
            'value' : '5'
          },
          {
            'title': 'Tasks completed:',
            'value' : '5'
          },
          {
            'title': 'Likes received:',
            'value' : '5'
          },
          {
            'title': 'Positive feedback received:',
            'value' : '5'
          }
        ]
      }
    }

  };


/*
    'ranges': [
      'Past Week', 'Past Month', 'Past 6 Months', 'Total',
    ],
    'hardFacts': [
      {'name': 'Marketplaces engaged', 'valuePastWeek': '0', 'valuePastMonth': '2', 'valuePast6Months': '4', 'valueTotal': '5' },
      {'name': 'Projects involved', 'valuePastWeek': '0', 'valuePastMonth': '2', 'valuePast6Months': '4', 'valueTotal': '5' },
      {'name': 'Tasks completed', 'valuePastWeek': '0', 'valuePastMonth': '2', 'valuePast6Months': '4', 'valueTotal': '5' },
      {'name': 'Likes earned', 'valuePastWeek': '0', 'valuePastMonth': '2', 'valuePast6Months': '4', 'valueTotal': '5' },
      {'name': 'Positive feedback earned', 'valuePastWeek': '0', 'valuePastMonth': '2', 'valuePast6Months': '4', 'valueTotal': '5' }
    ]

*/


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
          data: [0, 0, 0, 5, 4, 8, 12, 13, 10, 8, 6, 7],
          borderColor: '#3e95cd',
          fill: false
        }, {
          label: 'Marketplace 2',
          data: [3, 2, 1, 5, 4, 2, 4, 2, 0, 0, 0, 1],
          borderColor: '#8e5ea2',
          fill: false
        }, {
          label: 'Marketplace 3',
          data: [1, 3, 7, 14, 13, 12, 14, 5, 3, 1, 1, 5],
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

