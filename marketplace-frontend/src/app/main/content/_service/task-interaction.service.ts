import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Task} from '../_model/task';

@Injectable()
export class TaskInteractionService {

  constructor(private http: HttpClient) {
  }

  findByTask(task: Task) {
    return this.http.get('/marketplace/task/' + task.id + '/interaction');
  }

  findFinishedByTask(task: Task) {
    return this.http.get('/marketplace/task/' + task.id + '/interaction?operation=FINISHED');
  }

  findReservedVolunteersByTask(task: Task) {
    return this.http.get(`/marketplace/task/${task.id}/participant?operation=RESERVED`);
  }

  findAssignedVolunteersByTask(task: Task) {
    return this.http.get(`/marketplace/task/${task.id}/participant?operation=ASSIGNED`);
  }

}
