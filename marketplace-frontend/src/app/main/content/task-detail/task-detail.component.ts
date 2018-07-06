import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../_model/task';
import {TaskService} from '../_service/task.service';
import {MessageService} from '../_service/message.service';

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
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['taskId']));
  }

  private loadTask(taskId: string) {
    // TODO
    this.taskService.findById(undefined, taskId).toPromise().then((task: Task) => this.task = task);
  }

}
