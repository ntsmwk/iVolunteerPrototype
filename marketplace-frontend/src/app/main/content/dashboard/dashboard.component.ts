import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';

import {DisplayGrid, GridsterComponent, GridsterConfig} from 'angular-gridster2';
import {Dashlet} from '../_model/dashlet';
import {CoreDashboardService} from '../_service/core-dashboard.service';
import {Dashboard} from '../_model/dashboard';
import {isNullOrUndefined} from 'util';

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

  inEditMode = false;

  constructor(private dashboardService: CoreDashboardService) {
  }

  ngOnInit() {
    this.updateGridConfig(this.inEditMode);
    this.dashboardService.findAll().toPromise().then((dashboard: Dashboard) => {
      if (!isNullOrUndefined(dashboard)) {
        this.gridContent = dashboard.dashlets;
      } else {
        this.gridContent = new Array<Dashlet>();
        const dashlet = new Dashlet();
        dashlet.x = 0;
        dashlet.y = 0;
        dashlet.cols = 1;
        dashlet.rows = 1;
        this.gridContent.push(dashlet);
      }
    });
  }

  toggleEditMode() {
    this.inEditMode = !this.inEditMode;
    this.updateGridConfig(this.inEditMode);
  }

  ngOnDestroy() {
  }

  private updateGridConfig(editable: boolean) {
    this.gridConfig = {
      minCols: 80,
      maxCols: 80,
      minRows: 32,
      maxRows: 32,
      margin: 5,
      displayGrid: editable ? DisplayGrid.Always : DisplayGrid.None,
      draggable: {
        enabled: editable
      },
      resizable: {
        enabled: editable
      }
    };
  }
}
