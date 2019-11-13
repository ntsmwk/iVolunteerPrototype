import {Component, OnInit, ViewChild} from '@angular/core';

import { FuseTranslationLoaderService } from '@fuse/services/translation-loader.service';

import { Chart } from "chart.js";
 


@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {

  @ViewChild('myChart1') private chartRef1;
  chart1: any;

  @ViewChild('myChart2') private chartRef2;
  chart2: any;

  @ViewChild('myChart3') private chartRef3;
  chart3: any;
  chart3meta: any;

  widget0: any;
  widget1: any; 
  widget2: any;
  widget3: any;


  ngOnInit() {

    this.chart1 = new Chart(this.chartRef1.nativeElement, {
    type: 'pie',
    data: {
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
      responsive: false,
    }
  });

  this.chart2 = new Chart(this.chartRef2.nativeElement, {

  type: 'doughnut',
    data: {
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
      responsive: false,
    }
  });

  this.chart3 = new Chart(this.chartRef3.nativeElement, {
    type: 'bar',
    data: {
      datasets: [{
        label: 'Visits',
        data: [432, 428, 327, 363, 456, 267, 231],
        borderColor: '#f44336',
        backgroundColor: '#f44336',
      }],
      labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
    },
    options: {
      responsive: false,
      spanGaps: false,
      legend: {
        display: false
      },
      maintainAspectRatio: false,
      layout: {
        padding: {
          top: 24,
          left: 16,
          right: 16,
          bottom: 16
        }
      },
      scales: {
        xAxes: [
          {
            display: false
          }
        ],
        yAxes: [
          {
            display: false,
            ticks: {
              min: 150,
              max: 500
            }
          }
        ]
      }
    }
  });

  this.chart3meta = {
    visits: {
      value: 882,
      ofTarget: -9
    },
  };


  this.widget0 = {
    'title' : 'Aufgaben abgeschlossen',
    'data'  : {
        'label': 'AUFGABEN',
        'count': 50,
        'extra': {
            'label': 'Gestern',
            'count': 47
        }
    },
    'detail': 'You can show some detailed information about this widget in here.'
  };

  this.widget1 = {
    'title' : 'Aufgaben noch offen',
    'data'  : {
        'label': 'AUFGABEN',
        'count': 22,
        'extra': {
            'label': 'Gestern',
            'count': 25
        }
    },
    'detail': 'You can show some detailed information about this widget in here.'
  };

  this.widget2 = {
    'title' : 'Aufgaben mit hoher Priorität',
    'data'  : {
        'label': 'AUFGABEN',
        'count': 10,
        'extra': {
            'label': 'Gestern',
            'count': 8
        }
    },
    'detail': 'You can show some detailed information about this widget in here.'
  };

  this.widget3 = {
    'title' : 'Aufgaben die in Kürze auslaufen',
    'data'  : {
        'label': 'AUFGABEN',
        'count': 5,
        'extra': {
            'label': 'Gestern',
            'count': 5
        }
    },
    'detail': 'You can show some detailed information about this widget in here.'
  };

    console.log(this.chart3);
    console.log(this.widget1);
    console.log("Dashboard");
  }

}
