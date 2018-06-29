import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../../../_model/task';
import {TaskInteraction} from '../../../_model/task-interaction';

import {TaskService} from '../../../_service/task.service';
import {TaskInteractionService} from '../../../_service/task-interaction.service';
import {DatePipe} from '@angular/common';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'fuse-task-timeline',
  templateUrl: './task-timeline.component.html',
  styleUrls: ['./task-timeline.component.scss'],
  providers: [DatePipe]
})
export class FuseTaskTimelineComponent implements OnInit {

  private date2Interactions;

  constructor(private route: ActivatedRoute,
              private datePipe: DatePipe,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTaskInteractions(params['taskId']));
  }

  private loadTaskInteractions(taskId: string) {
    this.taskService.findById(taskId).toPromise().then((task: Task) => {
      this.taskInteractionService.findByTask(task).toPromise().then((taskInteractions: TaskInteraction[]) => {
        const date2Interactions = new Map<string, TaskInteraction[]>();
        taskInteractions.forEach((taskInteraction: TaskInteraction) => {
          const dayAsString = this.datePipe.transform(taskInteraction.timestamp, 'dd.MM.yyyy');
          if (!date2Interactions.has(dayAsString)) {
            date2Interactions.set(dayAsString, new Array<TaskInteraction>());
          }
          date2Interactions.get(dayAsString).push(taskInteraction);
        });
        this.date2Interactions = date2Interactions;
      });
    });
  }

  getDays() {
    return isNullOrUndefined(this.date2Interactions) ? [] : this.date2Interactions.keys();
  }

  getTaskInteractionsByDay(dayAsString: string) {
    return isNullOrUndefined(this.date2Interactions) ? [] : this.date2Interactions.get(dayAsString);
  }
}
