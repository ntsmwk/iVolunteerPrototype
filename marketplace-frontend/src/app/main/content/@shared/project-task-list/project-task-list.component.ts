import {Component, Input, OnInit} from '@angular/core';

import {Task} from '../../_model/task';
import {Marketplace} from '../../_model/marketplace';

import {TaskService} from '../../_service/task.service';
import {CoreMarketplaceService} from '../../_service/core-marketplace.service';

@Component({
  selector: 'fuse-project-task-list',
  templateUrl: './project-task-list.component.html',
  styleUrls: ['./project-task-list.component.scss']
})
export class FuseProjectTaskListComponent implements OnInit {


  @Input('projectId')
  private projectId: string;
  @Input('marketplaceId')
  public marketplaceId: string;
  @Input('availableOnly')
  public availableOnly: boolean;
  @Input('engagedOnly')
  public engagedOnly: boolean;
  @Input('participantId')
  public participantId: string;

  public tasks: Array<Task>;

  constructor(private taskService: TaskService, private marketplaceService: CoreMarketplaceService) {
  }

  ngOnInit() {
    this.marketplaceService.findById(this.marketplaceId).toPromise().then((marketplace: Marketplace) => {

      if (this.availableOnly === true && this.engagedOnly === false) {
        this.taskService.findAvailableByProjectId(marketplace, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);
      } else if (this.engagedOnly === true && this.availableOnly === false) {
        this.taskService.findEngagedByParticipant(marketplace, this.participantId, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);

      } else {
        this.taskService.findByProjectId(marketplace, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);
      }
    });
  }


}

