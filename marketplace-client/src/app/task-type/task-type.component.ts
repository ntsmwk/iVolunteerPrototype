import {Component} from '@angular/core';
import {TaskType} from './task-type';

@Component({
  templateUrl: './task-type.component.html',
})
export class TaskTypeComponent {

  onTaskTypeSaved(taskType: TaskType) {
    console.dirxml(taskType);
  }

}
