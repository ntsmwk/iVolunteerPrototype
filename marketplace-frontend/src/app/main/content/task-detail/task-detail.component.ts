import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../_model/task';
import {TaskService} from '../_service/task.service';
import {MessageService} from '../_service/message.service';
import {CoreMarketplaceService} from '../_service/core-marketplace.service';
import {Marketplace} from '../_model/marketplace';

@Component({
  selector: 'fuse-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss'],
  providers: [TaskService]
})
export class FuseTaskDetailComponent implements OnInit {

  task: Task;

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private marketplaceService: CoreMarketplaceService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['shortName'], params['taskId']));
  }

  private loadTask(marketplaceId: string, taskId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => this.task = task);
    });
  }

}
