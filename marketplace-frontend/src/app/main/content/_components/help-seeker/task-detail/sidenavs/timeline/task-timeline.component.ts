import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../../../../../_model/task';
import {TaskInteraction} from '../../../../../_model/task-interaction';

import {TaskService} from '../../../../../_service/task.service';
import {TaskInteractionService} from '../../../../../_service/task-interaction.service';
import {DatePipe} from '@angular/common';
import {CoreMarketplaceService} from '../../../../../_service/core-marketplace.service';
import {Marketplace} from '../../../../../_model/marketplace';
import {MessageService} from '../../../../../_service/message.service';
import {isNullOrUndefined} from 'util';
import {Subscription} from 'rxjs';

@Component({
  selector: 'fuse-task-timeline',
  templateUrl: './task-timeline.component.html',
  styleUrls: ['./task-timeline.component.scss'],
  providers: [DatePipe]
})
export class FuseTaskTimelineComponent implements OnInit, OnDestroy {

  private taskId: string;
  private marketplaceId: string;

  private taskHistoryChangedSubscription: Subscription;

  public days: Set<string>;
  public date2Interactions: Map<string, TaskInteraction[]>;

  constructor(private datePipe: DatePipe,
              private route: ActivatedRoute,
              private messageService: MessageService,
              private marketplaceService: CoreMarketplaceService,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.marketplaceId = params['marketplaceId'];
      this.loadTaskInteractions();
    });
    this.taskHistoryChangedSubscription = this.messageService.subscribe('taskHistoryChanged', this.loadTaskInteractions.bind(this));
  }

  ngOnDestroy() {
    this.taskHistoryChangedSubscription.unsubscribe();
  }

  private loadTaskInteractions() {
    this.days = new Set<string>();
    this.date2Interactions = new Map<string, TaskInteraction[]>();

    this.marketplaceService.findById(this.marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.taskInteractionService.findByTask(marketplace, <Task>{id: this.taskId}).toPromise().then((taskInteractions: TaskInteraction[]) => {
        taskInteractions.forEach((taskInteraction: TaskInteraction) => {
          const day = this.datePipe.transform(taskInteraction.timestamp, 'dd.MM.yyyy');
          if (!this.date2Interactions.has(day)) {
            this.days.add(day);
            this.date2Interactions.set(day, new Array<TaskInteraction>());
          }
          this.date2Interactions.get(day).push(taskInteraction);
        });
      });
    });
  }

  getTaskInteractionsByDay(dayAsString: string) {
    return isNullOrUndefined(this.date2Interactions) ? [] : this.date2Interactions.get(dayAsString);
  }
}
