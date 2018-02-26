import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {isNullOrUndefined} from 'util';
import {TaskTransaction} from './task-transaction';

@Injectable()
export class TaskTransactionService {

  private apiUrl = '/rest/taskTransaction';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  findByTask(taskId: string) {
    return this.http.get([this.apiUrl, 'task', taskId].join('/'));
  }

  save(taskTransaction: TaskTransaction) {
    console.log('service...');
    /*if (isNullOrUndefined(taskTransaction.task)
      || isNullOrUndefined(taskTransaction.volunteer)
      || isNullOrUndefined(taskTransaction.transactionType)) {
      console.log('service call');
      */
      return this.http.post(this.apiUrl, taskTransaction);
    // }
    // console.log('no service call');
  }
}
