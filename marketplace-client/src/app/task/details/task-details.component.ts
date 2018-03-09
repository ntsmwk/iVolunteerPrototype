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
  isAlreadyImported: boolean;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private taskService: TaskService,
              private router: Router,
              private loginService: LoginService,
              private repositoryService: RepositoryService,
              private taskInteractionService: TaskInteractionService) {
    this.taskDetailsForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'status': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('', Validators.required)
    });
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
          name: task.name,
          description: task.description,
          status: task.status,
          startDate: new Date(task.startDate),
          endDate: new Date(task.endDate)
        });

        this.repositoryService.isTaskAlreadyImported(this.task).toPromise().then((isImported: boolean) => {
          this.isAlreadyImported = isImported;
        });

        this.taskInteractionService.isTaskAlreadyReserved(this.task).toPromise().then((isReserved: boolean) => {
          this.isAlreadyReserved = isReserved;
        });
      });
  }


  save() {
    this.task.name = (<Task> (this.taskDetailsForm.value)).name;
    this.task.description = (<Task> (this.taskDetailsForm.value)).description;
    this.task.startDate = new Date((<Task> (this.taskDetailsForm.value)).startDate);
    this.task.endDate = new Date((<Task> (this.taskDetailsForm.value)).endDate);

    this.taskService.save(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  import() {
    this.taskInteractionService.findFinishedByTask(this.task)
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => {
        this.repositoryService.importTask(taskInteractions[0])
          .toPromise()
          .then(() => {
            alert('Task is imported');
            this.isAlreadyImported = true;
          });
      });
  }

  reserve() {
    this.taskInteractionService.reserve(this.task).toPromise().then(() => this.loadData(this.task.id));
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
