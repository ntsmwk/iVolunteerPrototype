import { Component, OnInit, Input } from '@angular/core';
import { fuseAnimations } from '../../../../../@fuse/animations';
import * as d3 from "d3";


@Component({
  selector: 'fuse-contributions',
  templateUrl: './contributions.component.html',
  styleUrls: ['./contributions.component.scss'],
  animations: fuseAnimations

})
export class ContributionsComponent implements OnInit {
  width: number = 700;
  height: number = 300;
  view = [this.width, this.height];
  colorScheme = 'cool';
  schemeType: string = 'ordinal';
  showGridLines = true;
  animations: boolean = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = false
  showYAxisLabel = false
  xAxisLabel = 'Country'
  yAxisLabel = 'Executions'
  noBarWhenZero = true;

  timelineFilterBarData = [
    {
      "name": "2016-09-12T17:08:25.009Z",
      "value": 275
    },
    {
      "name": "2016-09-13T17:08:25.009Z",
      "value": 86
    },
    {
      "name": "2016-09-14T17:08:25.009Z",
      "value": 114
    },
    {
      "name": "2016-09-15T17:08:25.009Z",
      "value": 77
    },
    {
      "name": "2016-09-16T17:08:25.009Z",
      "value": 109
    },
    {
      "name": "2016-09-17T17:08:25.009Z",
      "value": 141
    },
    {
      "name": "2016-09-18T17:08:25.009Z",
      "value": 207
    },
    {
      "name": "2016-09-19T17:08:25.009Z",
      "value": 297
    },
    {
      "name": "2016-09-20T17:08:25.009Z",
      "value": 78
    },
    {
      "name": "2016-09-21T17:08:25.009Z",
      "value": 109
    },
    {
      "name": "2016-09-22T17:08:25.009Z",
      "value": 0
    },
    {
      "name": "2016-09-23T17:08:25.009Z",
      "value": 162
    },
    {
      "name": "2016-09-24T17:08:25.009Z",
      "value": 105
    },
    {
      "name": "2016-09-25T17:08:25.009Z",
      "value": 12
    },
    {
      "name": "2016-09-26T17:08:25.009Z",
      "value": 213
    },
    {
      "name": "2016-09-27T17:08:25.009Z",
      "value": 293
    },
    {
      "name": "2016-09-28T17:08:25.009Z",
      "value": 256
    },
    {
      "name": "2016-09-29T17:08:25.009Z",
      "value": 88
    },
    {
      "name": "2016-09-30T17:08:25.009Z",
      "value": 104
    },
    {
      "name": "2016-10-01T17:08:25.009Z",
      "value": 239
    },
    {
      "name": "2016-10-02T17:08:25.009Z",
      "value": 106
    },
    {
      "name": "2016-10-03T17:08:25.009Z",
      "value": 216
    },
    {
      "name": "2016-10-04T17:08:25.009Z",
      "value": 52
    },
    {
      "name": "2016-10-05T17:08:25.009Z",
      "value": 61
    },
    {
      "name": "2016-10-06T17:08:25.009Z",
      "value": 15
    },
    {
      "name": "2016-10-07T17:08:25.009Z",
      "value": 172
    },
    {
      "name": "2016-10-08T17:08:25.009Z",
      "value": 271
    },
    {
      "name": "2016-10-09T17:08:25.009Z",
      "value": 100
    },
    {
      "name": "2016-10-10T17:08:25.009Z",
      "value": 65
    },
    {
      "name": "2016-10-11T17:08:25.009Z",
      "value": 62
    }
  ]

  onFilter(event) {
    console.log('timeline filter', event);
  }

  sunburstChartData = [
    {
      name: 'Calcutta',
      children: [
        { name: 'Gariahat', value: 120 },
        {
          name: 'Salt Lake', children: [
            { name: 'Sector 1', value: 50 },
            { name: 'Sector 2', value: 60 },
            { name: 'Sector 3', value: 20 }
          ]
        },
        { name: 'Tollygunge', value: 60 }
      ]
    },
    {
      name: 'Madras',
      children: [
        { name: 'Adyar', value: 120 },
        {
          name: 'Anna Nagar', children: [
            { name: 'Sector 1', value: 50 },
            { name: 'Sector 2', value: 60 },
            { name: 'Sector 3', value: 20 }
          ]
        },
        { name: 'T Nagar', value: 60 }
      ]
    },
    {
      name: 'Bombay',
      children: [
        { name: 'Andheri', value: 120 },
        {
          name: 'Bandra', children: [
            { name: 'West', value: 50 },
            { name: 'East', value: 60 }
          ]
        },
        { name: 'Colaba', value: 60 }
      ]
    },
    {
      name: 'Delhi',
      value: 150
    },
    {
      name: 'Bangalore',
      children: [
        { name: 'Koramangala', value: 120 },
        {
          name: 'Indira Nagar', children: [
            { name: 'Sector 1', value: 50 },
            { name: 'Sector 2', value: 60 },
            { name: 'Sector 3', value: 20 }
          ]
        },
        { name: 'Marathahalli', value: 60 }
      ]
    }
  ];

  constructor() { }

  ngOnInit() {
  }

}
