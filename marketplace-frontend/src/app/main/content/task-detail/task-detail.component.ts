import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../_model/task';
import {TaskService} from '../_service/task.service';
import {CoreMarketplaceService} from '../_service/core-marketplace.service';
import {Marketplace} from '../_model/marketplace';
import {LoginService} from '../_service/login.service';
import {isNullOrUndefined} from 'util';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class FuseTaskDetailComponent implements OnInit {

  task: Task;
  role: string;

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private loginService: LoginService,
              private marketplaceService: CoreMarketplaceService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['marketplaceId'], params['taskId']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: string) => this.role = role);
  }

  private loadTask(marketplaceId: string, taskId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => this.task = task);
    });
  }

  showEdit() {
    return !isNullOrUndefined(this.role) && this.role === 'EMPLOYEE' && !isNullOrUndefined(this.task);
  }
}
