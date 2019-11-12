import {Component, OnInit} from '@angular/core';

import {DisplayGrid, GridsterConfig} from 'angular-gridster2';
import {CoreDashboardService} from '../_service/core-dashboard.service';
import {Dashboard} from '../_model/dashboard';
import {isNullOrUndefined} from 'util';
import {LoginService} from '../_service/login.service';
import {ParticipantRole} from '../_model/participant';
import {MatDialog} from '@angular/material';
import {FuseDashletSelectorDialog} from './dashlet-selector.dialog';
import {Dashlet} from '../_model/dashlet';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from '../_service/message.service';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit {

  public role: ParticipantRole;

  public dashboard: Dashboard;

  public gridConfig: GridsterConfig;
  public inEditMode = false;

  constructor(private dialog: MatDialog,
              private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private dashboardService: CoreDashboardService) {
  }

  ngOnInit() {
    this.updateGridConfig(this.inEditMode);
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
      this.role = role;
      if ('VOLUNTEER' === role) {
        this.route.params.subscribe(params => {
          const dashboardId = params['dashboardId'];
          if (!isNullOrUndefined(dashboardId) && 0 < dashboardId.length) {
            this.dashboardService.findById(dashboardId).toPromise().then((dashboard: Dashboard) => this.dashboard = dashboard);
          } else {
            this.dashboard = new Dashboard();
            this.dashboard.dashlets = [];
          }
        });
      }
    });
  }

  openDashboardSelectionDialog(): void {
    const dialogRef = this.dialog.open(FuseDashletSelectorDialog, {
      width: '768px',
      data: {dashlet: undefined}
    });

    dialogRef.afterClosed().toPromise().then((dashlet: Dashlet) => {
      this.dashboard.dashlets.push(dashlet);
    });
  }

  updateDashlet(dashlet: Dashlet) {
    this.removeDashlet(dashlet);
    this.dashboard.dashlets.push(dashlet);
  }

  removeDashlet(dashlet: Dashlet) {
    this.dashboard.dashlets.splice(this.dashboard.dashlets.findIndex((current: Dashlet) => current.id === dashlet.id), 1);
  }


  toggleEditMode() {
    this.inEditMode = !this.inEditMode;
    this.updateGridConfig(this.inEditMode);
    if (!this.inEditMode) {
      this.dashboardService.save(this.dashboard)
        .toPromise()
        .then((dashboard: Dashboard) => {
          this.router.navigate(['main', 'dashboard', dashboard.id]);
          this.messageService.broadcast('dashboardChanged', {});
        });
    }
  }

  removeDashboard() {
    this.dashboardService.remove(this.dashboard).toPromise().then(() => {
      this.router.navigate(['main', 'dashboard']);
      this.messageService.broadcast('dashboardChanged', {});
    });
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
