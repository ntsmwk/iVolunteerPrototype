import {Component, OnDestroy, OnInit} from '@angular/core';

import {Task} from '../_model/task';
import {Volunteer} from '../_model/volunteer';

import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Marketplace} from '../_model/marketplace';
import {isArray} from 'util';
import {CoreVolunteerService} from '../_service/core-volunteer.service';
import {MessageService} from '../_service/message.service';
import {Subscription} from 'rxjs';
import { ParticipantRole } from '../_model/participant';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit {

  role: ParticipantRole;

  constructor(
    private coreVolunteerService: CoreVolunteerService,
    private loginService: LoginService,
  ) {
  }

  ngOnInit() {
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
      this.role = role;
    })
  }

}
