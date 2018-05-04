import {Task} from './task';
import {Participant} from './participant';

export class TaskInteraction {
  id: string;
  task: Task;
  participant: Participant;
  operation: string;
  timestamp: Date;
  comment: string;
}
