import {TaskType} from '../task-type/task-type';

export class Task {
  id: string;
  name: string;
  description: string;
  startDate: Date;
  endDate: Date;

  type: TaskType;
}


