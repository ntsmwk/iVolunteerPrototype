import {Component} from '@angular/core';
import {TaskType} from '../task-type/task-type';
import {Task} from './task';

@Component({
  templateUrl: './task.component.html'
})
export class TaskComponent {

  onTaskSaved(task: Task){
    console.dirxml(task);
  }

  onTaskTypeSaved(taskType: TaskType) {
    console.dirxml(taskType);
  }
}
