import {Component} from '@angular/core';
import {TaskType} from '../task-type/task-type';

@Component({
  templateUrl: './task.component.html'
})
export class TaskComponent {

  onTaskTypeSaved(taskType: TaskType) {
    console.dirxml(taskType);
  }
}
