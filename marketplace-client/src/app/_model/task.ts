import {TaskType} from './task-type';

export class Task {
  id: string;
  taskType: TaskType;
  workflowKey: string;
  startDate: Date;
  endDate: Date;
  status: string;
}


