import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {TaskType} from '../../task-type/task-type';
import {TaskTypeService} from '../../task-type/task-type.service';
import {ActivatedRoute, Router} from '@angular/router';
import {isNullOrUndefined} from 'util';

@Component({
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  taskForm: FormGroup;

  taskTypes: TaskType[];

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private taskService: TaskService,
              private taskTypeService: TaskTypeService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(''),
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'type': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('')
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.findTask(params['id']));
    this.taskTypeService.findAll().toPromise().then((taskTypes: TaskType[]) => this.taskTypes = taskTypes);
  }

  private findTask(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      this.taskForm.value.id = undefined;
      return;
    }
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.taskForm.setValue({
        id: task.id,
        name: task.name,
        description: task.description,
        type: task.type,
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate)
      });
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    const task = <Task> this.taskForm.value;
    task.id = task.id.length === 0 ? undefined : task.id;
    this.taskService.save(task).toPromise().then(() => this.router.navigate(['/tasks']));
  }
}
