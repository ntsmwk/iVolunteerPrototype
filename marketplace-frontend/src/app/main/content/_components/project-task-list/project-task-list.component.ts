import {Component, Input, OnInit} from '@angular/core';

import {Task} from '../../_model/task';
import {Marketplace} from '../../_model/marketplace';

import {TaskService} from '../../_service/task.service';
import {CoreMarketplaceService} from '../../_service/core-marketplace.service';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {TaskInteraction} from '../../_model/task-interaction';
import {Participant} from '../../_model/participant';

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
  @Input('participantId')
  public participantId: string;

  @Input('availableOnly')
  public availableOnly: boolean;
  @Input('engagedOnly')
  public engagedOnly: boolean;
  @Input('finishedOnly')
  public finishedOnly: boolean;


  public tasks: Array<Task>;
  public taskInteractions: Array<TaskInteraction>;

  constructor(private taskService: TaskService,
              private marketplaceService: CoreMarketplaceService,
              private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {
    this.marketplaceService.findById(this.marketplaceId).toPromise().then((marketplace: Marketplace) => {

      if (this.availableOnly === true) {
        this.taskService.findAvailableByProjectId(marketplace, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);

      } else if (this.engagedOnly === true) {
        this.taskService.findEngagedByParticipant(marketplace, this.participantId, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);

      } else if (this.finishedOnly === true) {
        this.taskService.findFinishedByParticipant(marketplace, this.participantId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);

      } else {
        this.taskService.findByProjectId(marketplace, this.projectId).toPromise().then((tasks: Array<Task>) => this.tasks = tasks);
      }
    });
  }

  getInteractions(marketplace: Marketplace, task: Task) {
    //
    // this.taskInteractionService.findByTask(marketplace, task).toPromise().then(taskInteractions: TaskInteraction[]) => {
    //  this.taskInteractions = taskInteractions;
    // });
  }

}

