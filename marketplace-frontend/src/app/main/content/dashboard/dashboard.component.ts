import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';

import {GridsterComponent, GridsterConfig} from 'angular-gridster2';
import {Dashlet} from '../_model/dashlet';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit, OnDestroy {

  @ViewChild('gridster')
  gridsterComponent: GridsterComponent;

  gridConfig: GridsterConfig;
  gridContent: Dashlet[] = [];

  isInEditMode = true;

  constructor() {
  }

  ngOnInit() {
    this.updateGridConfig(this.isInEditMode);

  }

  ngOnDestroy() {
  }

  private updateGridConfig(isEditable: boolean) {
    this.gridConfig = {
      minCols: 80,
      maxCols: 80,
      minRows: 32,
      maxRows: 32,
      margin: 5,

      draggable: {
        enabled: isEditable
      },
      resizable: {
        enabled: isEditable
      }
    };
  }
}
