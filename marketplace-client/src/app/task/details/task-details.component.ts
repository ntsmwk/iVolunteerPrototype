import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {ActivatedRoute} from '@angular/router';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {TaskInteraction} from '../../task-interaction/task-interaction';
import {MatTableDataSource} from '@angular/material';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskType} from '../../task-type/task-type';
import {LoginService} from '../../login/login.service';

@Component({
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  task: Task;
  dataSource = new MatTableDataSource<TaskInteraction>();
  displayedColumns = ['operation', 'timestamp', 'comment'];
  taskDetailsForm: FormGroup;
  taskTypes: TaskType[];
  participantRole;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private taskService: TaskService,
              private loginService: LoginService,
              private taskInteractionService: TaskInteractionService) {
    this.taskDetailsForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'status': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('', Validators.required)
    });

    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.participantRole = role);
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadData(params['id']));
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
      });
    this.taskInteractionService.findById(<Task>{id: id})
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => this.dataSource.data = taskInteractions);
  }

  save() {
    this.task.name = (<Task> (this.taskDetailsForm.value)).name;
    this.task.description = (<Task> (this.taskDetailsForm.value)).description;
    this.task.startDate = new Date((<Task> (this.taskDetailsForm.value)).startDate);
    this.task.endDate = new Date((<Task> (this.taskDetailsForm.value)).endDate);

    this.taskService.save(this.task).toPromise().then(() => this.loadData(this.task.id));
  }

  register() {
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
