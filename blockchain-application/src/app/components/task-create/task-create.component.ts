import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CreateTask, Task} from 'app/model/at.jku.cis';
import {CreateTaskService} from '../../providers/create-task.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskService} from '../../providers/task.service';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.scss']
})
export class TaskCreateComponent {
  @Input()
  personId: string;
  @Input()
  titleKey: string;
  @Output()
  onSave = new EventEmitter<Task>();

  taskForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private taskService: TaskService,
              private createTaskService: CreateTaskService) {
    this.taskForm = formBuilder.group({
      'taskId': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required)
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }
    const createTask = new CreateTask();
    createTask.creator = this.personId;
    createTask.taskId = this.taskForm.value.taskId;
    createTask.description = this.taskForm.value.description;
    this.createTaskService.addAsset(createTask).subscribe(() => {
      this.taskService.getAsset(createTask.taskId).subscribe((task: Task) => this.onSave.emit(task));
    });
  }

}
