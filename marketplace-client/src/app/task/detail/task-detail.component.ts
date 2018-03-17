import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {LoginService} from '../../login/login.service';
import {RepositoryService} from '../../_service/repository.service';
import {TaskInteraction} from '../../task-interaction/task-interaction';
import {MessageService} from '../../_service/message.service';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {

  task: Task;

  role;
  isAlreadyReserved: boolean;
  isAlreadyImported: boolean;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private taskService: TaskService,
              private loginService: LoginService,
              private repositoryService: RepositoryService,
              private taskInteractionService: TaskInteractionService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['id']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadTask(id: string) {
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;

      this.repositoryService.isTaskImported(this.task).toPromise().then((isAlreadyImported: boolean) => {
        this.isAlreadyImported = isAlreadyImported;
      });

      this.taskInteractionService.isTaskAlreadyReserved(this.task).toPromise().then((isAlreadyReserved: boolean) => {
        this.isAlreadyReserved = isAlreadyReserved;
      });
    });
  }

  import() {
    this.taskInteractionService.findFinishedByTask(this.task)
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => {
        this.repositoryService.saveTask(taskInteractions[0])
          .toPromise()
          .then(() => {
            alert('Task is imported');
            this.isAlreadyImported = true;
          });
      });
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
