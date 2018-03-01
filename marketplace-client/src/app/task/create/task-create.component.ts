import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {TaskType} from '../../task-type/task-type';
import {TaskTypeService} from '../../task-type/task-type.service';
import {Router} from '@angular/router';

@Component({
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent {
  taskForm: FormGroup;

  taskTypes: TaskType[];

  constructor(formBuilder: FormBuilder,
              private router: Router,
              private taskService: TaskService,
              private taskTypeService: TaskTypeService) {
    this.taskForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl(''),
      'type': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('')

    });
  }

  ngOnInit() {
    this.taskTypeService.findAll()
      .toPromise()
      .then((taskTypes: TaskType[]) => this.taskTypes = taskTypes);
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    this.taskService.save(<Task> this.taskForm.value)
      .toPromise()
      .then((task: Task) => this.router.navigate(['/tasks']));
  }
}
