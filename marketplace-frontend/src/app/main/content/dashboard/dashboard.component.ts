import {Component, OnInit} from '@angular/core';

import {DisplayGrid, GridsterConfig} from 'angular-gridster2';
import {CoreDashboardService} from '../_service/core-dashboard.service';
import {Dashboard} from '../_model/dashboard';
import {isNullOrUndefined} from 'util';
import {LoginService} from '../_service/login.service';
import {ParticipantRole} from '../_model/participant';
import {MatDialog} from '@angular/material';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from '../_service/message.service';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public role: ParticipantRole;

  constructor(
              private loginService: LoginService,
              ) {
  }

  ngOnInit() {
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
      this.role = role;
    });
  }


}