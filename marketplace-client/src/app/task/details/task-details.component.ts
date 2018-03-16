import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskType} from '../../task-type/task-type';
import {LoginService} from '../../login/login.service';
import {RepositoryService} from '../../_service/repository.service';
import {TaskInteraction} from '../../task-interaction/task-interaction';
import {DateAdapter} from '@angular/material';
import {MessageService} from '../../_service/message.service';

@Component({
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  task: Task;
  taskDetailsForm: FormGroup;
  taskTypes: TaskType[];

  role;
  isAlreadyReserved: boolean;
  isAlreadyAssigned: boolean;
  isAlreadyImported: boolean;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private taskService: TaskService,
              private router: Router,
              private loginService: LoginService,
              private repositoryService: RepositoryService,
              private taskInteractionService: TaskInteractionService,
              private dateAdapter: DateAdapter<Date>,
              private messageService: MessageService) {
    this.taskDetailsForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'status': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('', Validators.required)
    });

    this.dateAdapter.setLocale('de');
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadData(params['id']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadData(id: string) {
    this.taskService.findById(id)
      .toPromise()
      .then((task: Task) => {
        this.task = task;
        this.taskDetailsForm.setValue({
          name: task.type.name,
          description: task.type.description,
          status: task.status,
          startDate: new Date(task.startDate),
          endDate: new Date(task.endDate)
        });

        this.repositoryService.isTaskImported(this.task).toPromise().then((isAlreadyImported: boolean) => {
          this.isAlreadyImported = isAlreadyImported;
        });

        this.taskInteractionService.isTaskAlreadyReserved(this.task).toPromise().then((isAlreadyReserved: boolean) => {
          this.isAlreadyReserved = isAlreadyReserved;
        });

        this.taskInteractionService.isTaskAlreadyAssigned(this.task).toPromise().then((isAlreadyAssigned: boolean) => {
          this.isAlreadyAssigned = isAlreadyAssigned;
        });

      });
  }


  save() {
    this.task.startDate = new Date((<Task> (this.taskDetailsForm.value)).startDate);
    this.task.endDate = new Date((<Task> (this.taskDetailsForm.value)).endDate);

    this.taskService.save(this.task).toPromise().then(() => this.loadData(this.task.id));
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
    this.taskInteractionService.unreserve(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  reserve() {
    this.taskInteractionService.reserve(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  start() {
    this.taskService.start(this.task).toPromise().then(() => {
      this.loadData(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  suspend() {
    this.taskService.suspend(this.task).toPromise().then(() => {
      this.loadData(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  resume() {
    this.taskService.resume(this.task).toPromise().then(() => {
      this.loadData(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  finish() {
    this.taskService.finish(this.task).toPromise().then(() => {
      this.loadData(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  abort() {
    this.taskService.abort(this.task).toPromise().then(() => {
      this.loadData(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }

  sync() {
    this.taskService.sync(this.task).toPromise()
      .then((value) => {
        console.dirxml(value);
      });
  }
}
