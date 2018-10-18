import {Component, OnInit, ViewChild} from '@angular/core';

import {DisplayGrid, GridsterComponent, GridsterConfig} from 'angular-gridster2';
import {CoreDashboardService} from '../_service/core-dashboard.service';
import {Dashboard} from '../_model/dashboard';
import {DashletsConf} from './dashlets.config';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit {

  @ViewChild('gridster')
  private gridsterComponent: GridsterComponent;

  private dashboard: Dashboard;
  public gridConfig: GridsterConfig;

  public inEditMode = false;

  constructor(private dashboardService: CoreDashboardService) {
  }

  get gridContent() {
    return isNullOrUndefined(this.dashboard) ? [] : this.dashboard.dashlets;
  }

  ngOnInit() {
    this.updateGridConfig(this.inEditMode);
    this.dashboardService.findCurrent().toPromise().then((dashboard: Dashboard) => {
      this.dashboard = dashboard;
    });
  }

  toggleEditMode() {
    this.inEditMode = !this.inEditMode;
    this.updateGridConfig(this.inEditMode);
  }

  saveDashboard() {
    this.dashboardService.save(this.dashboard).toPromise().then(() => alert('Dashboard is saved'));
  }

  findDashlet(id: string) {
    return DashletsConf.getDashletEntryById(id);
  }

  private updateGridConfig(editable: boolean) {
    this.gridConfig = {
      minCols: 40,
      maxCols: 40,
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
