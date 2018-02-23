import {Component, EventEmitter, Output, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskService} from '../task/task.service';
import {Task} from '../task/task';
import {TaskTypeService} from '../task-type/task-type.service';
import {TaskType} from '../task-type/task-type';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent {
  @Output()
  onSaved = new EventEmitter<Task>();

  taskForm: FormGroup;
  taskTypes: TaskType[];

  constructor(formBuilder: FormBuilder, private taskService: TaskService, private taskTypeService: TaskTypeService) {
    this.taskForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl(''),
      'type': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl('')

    });
  }

  ngOnInit() {
    this.taskTypeService.findAll().subscribe((taskTypes: TaskType[]) => this.taskTypes = taskTypes);
  }

  save() {
    console.log('asdf');
    if (!this.taskForm.valid) {
      return;
    }
    console.log('after');

    this.taskService.save(<Task> this.taskForm.value)
      .toPromise()
      .then((task: Task) => this.onSaved.emit(task));
  }
}
