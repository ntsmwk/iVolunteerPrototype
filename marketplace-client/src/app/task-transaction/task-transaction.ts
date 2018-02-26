import {Task} from '../task/task';
import {TransactionType} from './transaction-type';
import {Volunteer} from '../participant/volunteer';

export class TaskTransaction {
  task: Task;
  volunteer: Volunteer;
  transactionType: TransactionType;
  timestamp: Date;

}
