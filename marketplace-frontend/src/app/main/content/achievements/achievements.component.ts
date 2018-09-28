import {AfterViewInit, Component, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../@fuse/animations';

@Component({
  selector: 'fuse-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.scss'],
  animations: fuseAnimations

})
export class FuseAchievementsComponent implements OnInit {
  constructor() {
  }

  ngOnInit() {
  }

}

