import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Task} from '../task';
import {TaskService} from '../task.service';

import {LoginService} from '../../login/login.service';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {MessageService} from '../../_service/message.service';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {

  task: Task;

  role;
  isAlreadyReserved: boolean;
  isAlreadyAssigned: boolean;

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['id']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadTask(id: string) {
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;

      this.taskInteractionService.isTaskAlreadyReserved(this.task).toPromise().then((isAlreadyReserved: boolean) => {
        this.isAlreadyReserved = isAlreadyReserved;
      });

      this.taskInteractionService.isTaskAlreadyAssigned(this.task).toPromise().then((isAlreadyAssigned: boolean) => {
        this.isAlreadyAssigned = isAlreadyAssigned;
      });
    });
  }

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  unreserve() {
    this.taskInteractionService.unreserve(this.task).toPromise().then(() => this.loadTask(this.task.id));
  }

  reserve() {
    this.taskInteractionService.reserve(this.task).toPromise().then(() => this.loadTask(this.task.id));
  }

  start() {
    this.taskService.start(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  suspend() {
    this.taskService.suspend(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  resume() {
    this.taskService.resume(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  finish() {
    this.taskService.finish(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  abort() {
    this.taskService.abort(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }
}
