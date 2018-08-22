import {AfterViewInit, Component} from '@angular/core';
import * as Chart from 'chart.js';
import {fuseAnimations} from '../../../../../../@fuse/animations';

@Component({
  selector: 'fuse-profile-repository',
  templateUrl: './repository.component.html',
  styleUrls: ['./repository.component.scss'],
  animations: fuseAnimations

})
export class FuseProfileRepositoryComponent implements AfterViewInit {
  canvas: any;
  ctx: any;

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
      options: {
      }
    });
  }

}
