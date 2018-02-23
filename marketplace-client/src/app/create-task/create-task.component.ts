import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskService} from '../task/task.service';
import {Task} from '../task/task';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent {
  @Output()
  onSaved = new EventEmitter<Task>();

  taskForm: FormGroup;

  constructor(formBuilder: FormBuilder, private taskService: TaskService) {
    this.taskForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'startDate': new FormControl('', Validators.required),
      'endDate': new FormControl(''),
      'type': new FormControl('', Validators.required)

    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }
    this.taskService.save(<Task> this.taskForm.value)
      .toPromise()
      .then((task: Task) => this.onSaved.emit(task));
  }
}
