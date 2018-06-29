import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Task} from '../_model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskInteractionService {

  constructor(private http: HttpClient) {
  }

  findByTask(task: Task, url: string) {
    return this.http.get(url + '/task/' + task.id + '/interaction');
  }

  findFinishedByTask(task: Task, url: string) {
    return this.http.get(url + '/task/' + task.id + '/interaction?operation=FINISHED');
  }

  findReservedVolunteersByTask(task: Task, url: string) {
    return this.http.get(`${url}/task/${task.id}/participant?operation=RESERVED`);
  }

  findAssignedVolunteersByTask(task: Task, url: string) {
    return this.http.get(`${url}/task/${task.id}/participant?operation=ASSIGNED`);
  }

}
