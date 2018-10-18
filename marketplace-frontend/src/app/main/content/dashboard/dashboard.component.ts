import {Component, OnInit, ViewChild} from '@angular/core';

import {DisplayGrid, GridsterComponent, GridsterConfig} from 'angular-gridster2';
import {CoreDashboardService} from '../_service/core-dashboard.service';
import {Dashboard} from '../_model/dashboard';
import {DashletsConf} from './dashlets.config';
import {isNullOrUndefined} from 'util';
import {LoginService} from '../_service/login.service';
import {ParticipantRole} from '../_model/participant';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit {

  public role: ParticipantRole;

  @ViewChild('gridster')
  private gridsterComponent: GridsterComponent;
  public dashboard: Dashboard;

  public gridConfig: GridsterConfig;
  public inEditMode = false;

  constructor(private loginService: LoginService,
              private dashboardService: CoreDashboardService) {
  }

  ngOnInit() {
    this.updateGridConfig(this.inEditMode);
    this.dashboardService.findCurrent().toPromise().then((dashboard: Dashboard) => {
      if (!isNullOrUndefined(dashboard)) {
        this.dashboard = dashboard;
      } else {
        this.dashboard = new Dashboard();
        this.dashboard.dashlets = [];
      }
    });
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role);
  }

  findDashletComponenet(id: string) {
    return DashletsConf.getDashletEntryById(id).type;
  }

  toggleEditMode() {
    this.inEditMode = !this.inEditMode;
    this.updateGridConfig(this.inEditMode);
    if (!this.inEditMode) {
      this.dashboardService.save(this.dashboard).toPromise().then(() => alert('Dashboard is saved'));
    }
  }

  private updateGridConfig(editable: boolean) {
    this.gridConfig = {
      minCols: 16,
      maxCols: 64,
      minRows: 32,
      maxRows: 128,
      gridType: 'fixed',
      fixedColWidth: 25,
      fixedRowHeight: 25,
      setGridSize: true,
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
