import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskTypeService} from '../task-type/task-type.service';
import {TaskType} from '../task-type/task-type';

@Component({
  selector: 'app-create-task-type',
  templateUrl: './create-task-type.component.html',
  styleUrls: ['./create-task-type.component.css']
})
export class CreateTaskTypeComponent {
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
