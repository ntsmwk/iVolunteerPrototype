import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskTypeService} from '../task-type.service';
import {TaskType} from '../task-type';

@Component({
  selector: 'app-task-type-create',
  templateUrl: './task-type-create.component.html',
  styleUrls: ['./task-type-create.component.css']
})
export class TaskTypeCreateComponent {
  @Output()
  onSaved = new EventEmitter<TaskType>();

  taskTypeForm: FormGroup;

  constructor(formBuilder: FormBuilder, private taskTypeService: TaskTypeService) {
    this.taskTypeForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required)
    });
  }

  save() {
    if (!this.taskTypeForm.valid) {
      return;
    }
    this.taskTypeService.save(<TaskType> this.taskTypeForm.value)
      .toPromise()
      .then((taskType: TaskType) => this.onSaved.emit(taskType));
  }
}
