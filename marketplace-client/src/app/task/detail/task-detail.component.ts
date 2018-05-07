import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';

import {LoginService} from '../../_service/login.service';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {MessageService} from '../../_service/message.service';
import {Participant} from '../../_model/participant';
import {SourceService} from '../../_service/source.service';
import {ContractorService} from '../../_service/contractor.service';
import {Source} from '../../_model/source';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {

  source: Source;
  task: Task;
  participant: Participant;
  role;
  isAlreadyReserved = false;
  isAlreadyAssigned = false;

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['id']));
    this.sourceService.find().toPromise().then((source: Source) => this.source = source);
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadTask(id: string) {
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant).then(() => {
        this.taskInteractionService.getLatestTaskOperation(this.task, (this.participant)).toPromise().then((taskOperation) => {
          if (taskOperation === 'ASSIGNED') {
            this.isAlreadyReserved = false;
            this.isAlreadyAssigned = true;
          } else if (taskOperation === 'RESERVED' || taskOperation === 'UNASSIGNED') {
            this.isAlreadyReserved = true;
            this.isAlreadyAssigned = false;
          } else {
            this.isAlreadyReserved = false;
            this.isAlreadyAssigned = false;
          }
        });
      });
    });
  }

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private sourceService: SourceService,
              private contractorService: ContractorService,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  unreserve() {
    this.taskInteractionService.unreserve(this.task).toPromise().then(() => this.loadTask(this.task.id));
  }

  reserve() {
    this.contractorService.reserve(this.source, this.task).toPromise().then(() => this.loadTask(this.task.id));
  }

  publish() {
    this.taskService.publish(this.task).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
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
    this.contractorService.finish(this.source, this.task).toPromise().then(() => {
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
