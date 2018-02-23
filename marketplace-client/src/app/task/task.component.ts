import {Component} from '@angular/core';
import {Task} from './task';

@Component({
  templateUrl: './task.component.html'
})
export class TaskComponent {

  onTaskSaved(task: Task) {
    console.dirxml(task);
  }
}
