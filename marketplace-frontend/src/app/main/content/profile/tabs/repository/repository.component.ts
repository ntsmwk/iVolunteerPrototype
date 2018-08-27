///<reference path="../../../../../../../node_modules/@angular/core/src/metadata/lifecycle_hooks.d.ts"/>
import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Chart from 'chart.js';
import {fuseAnimations} from '../../../../../../@fuse/animations';
import * as Punchcard from '../../../../../../assets/punchcard.js';
import d3 from 'd3';


@Component({
  selector: 'fuse-profile-repository',
  templateUrl: './repository.component.html',
  styleUrls: ['./repository.component.scss'],
  animations: fuseAnimations

})
export class FuseProfileRepositoryComponent implements AfterViewInit, OnInit {
  canvas: any;
  ctx: any;

  ngOnInit() {

  }


  ngAfterViewInit() {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    const myChart = new Chart(this.ctx, {
      type: 'radar',
      data: {
        labels: ['Professional skills', 'Methodical expertise', 'Social skills', 'Self competences'],
        datasets: [{
          label: 'You',
          backgroundColor: 'rgba(200,0,0,0.2)',
          data: [14, 10, 10, 8]
        }]
      },
      options: {}
    });
  }


}
