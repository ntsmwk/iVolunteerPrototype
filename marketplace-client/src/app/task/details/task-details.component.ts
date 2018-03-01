import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {ActivatedRoute} from '@angular/router';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {TaskInteraction} from '../../task-interaction/task-interaction';

@Component({
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  task: Task;
  private taskInteractions: TaskInteraction[];

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadData(params['id']));
  }

  private loadData(id: string) {
    this.taskService.findById(id)
      .toPromise()
      .then((task: Task) => this.task = task);
    this.taskInteractionService.findById(<Task>{ id: id })
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => this.taskInteractions = taskInteractions);
  }

  start() {
    this.taskService.start(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  finish() {
    this.taskService.finish(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  cancel() {
    this.taskService.cancel(this.task).toPromise().then(() => this.loadData(this.task.id));
  }


}
