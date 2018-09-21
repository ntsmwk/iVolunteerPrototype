import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Chart from 'chart.js';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-opportunities',
  templateUrl: './opportunities.component.html',
  styleUrls: ['./opportunities.component.scss'],
  animations: fuseAnimations

})
export class OpportunitiesComponent implements OnInit, AfterViewInit {
  canvas: any;
  ctx: any;

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
            'title': 'Marketplaces engaged in',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '1'
          },
          {
            'title': 'Projects involved in',
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
            'title': 'Marketplaces engaged in',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '2'
          },
          {
            'title': 'Projects involved in',
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
            'title': 'Marketplaces engaged in',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '3'
          },
          {
            'title': 'Projects involved in',
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
            'title': 'Marketplaces engaged in',
            'icon': 'assets/icons/important_stats/marketplace.svg',
            'value': '3'
          },
          {
            'title': 'Projects involved in',
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
      {
        'name': 'Training Course: Paramedic (10.08.2018)',
        'task1': 'Practical part',
        'task1Hours': '120h',
        'task1Material': '',
        'task2': 'Examination',
        'task2Hours': '2h',
        'task2Material': ''
      },
      {
        'name': 'Advent bazaar (18.12.2017)',
        'task1': 'Shift 1',
        'task1Hours': '2h',
        'task1Material': 'Strawberry Cake (2x)',
        'task2': 'Shift 2',
        'task2Hours': '2h',
        'task2Material': 'Apple Pie (1x)'
      },
      {'name': 'Firefighter competition (24.05.2018)', 'task1': 'Training', 'task1Hours': '16h', 'task1Material': ''}
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
      type: 'bar',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [{
          label: 'Marketplace 1',
          data: [0, 0, 0, 3, 4, 6, 5, 2, 1, 1, 1, 1],
          borderColor: '#3e95cd',
          backgroundColor: '#3e95cd',
          fill: false
        }, {
          label: 'Marketplace 2',
          data: [3, 2, 1, 5, 4, 4, 4, 2, 0, 0, 0, 0],
          borderColor: '#8e5ea2',
          backgroundColor: '#8e5ea2',
          fill: false
        }, {
          label: 'Marketplace 3',
          data: [1, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4, 6],
          borderColor: '#c45850',
          backgroundColor: '#c45850',
          fill: false
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [{ stacked: true }],
          yAxes: [{ stacked: true }]
        }
      }
    });
  }

}
