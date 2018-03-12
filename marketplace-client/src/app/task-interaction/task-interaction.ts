import {Task} from '../task/task';
import {Participant} from '../participant/participant';

export class TaskInteraction {
  id: string;
  task: Task;
  participant: Participant;
  operation: string;
  timestamp: Date;
  comment: string;
}
