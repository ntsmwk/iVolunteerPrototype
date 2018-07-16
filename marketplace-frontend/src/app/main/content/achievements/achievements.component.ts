import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Chart from 'chart.js'

@Component({
  selector: 'fuse-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.scss']
})
export class FuseAchievementsComponent implements OnInit, AfterViewInit {
  canvas: any;
  ctx: any;

  constructor() { }

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
        title: {
          display: true,
          text: 'Finished Tasks per Marketplace'
        }
      }
    });
  }

}

