import {TaskType} from './task-type';

export class Task {
  id: string;
  type: TaskType;
  startDate: Date;
  endDate: Date;
  status: string;
}


