import {Injectable} from '@angular/core';
import {Task} from '../task/task';
import {HttpClient} from '@angular/common/http';
import {Participant} from '../participant/participant';

@Injectable()
export class TaskInteractionService {

  private apiUrl = '/rest';

  constructor(private http: HttpClient) {
  }

  findByTask(task: Task) {
    return this.http.get('/rest/task/' + task.id + '/interaction');
  }

  findFinishedByTask(task: Task) {
    return this.http.get('/rest/task/' + task.id + '/interaction?operation=FINISHED');
  }

  findReservedVolunteersByTaskId(task: Task) {
    return this.http.get(`${this.apiUrl}/task/${task.id}/participant?operation=RESERVED`);
  }

  getLatestTaskOperation(task: Task, participant: Participant) {
    return this.http.get(`${this.apiUrl}/task/${task.id}/participant/${participant.id}`);
  }

  reserve(task: Task) {
    return this.http.post([this.apiUrl, 'task', task.id, 'reserve'].join('/'), {});
  }

  unreserve(task: Task) {
    return this.http.post([this.apiUrl, 'task', task.id, 'unreserve'].join('/'), {});
  }

  findAssignedVolunteersByTaskId(task: Task) {
    return this.http.get(`${this.apiUrl}/task/${task.id}/participant?operation=ASSIGNED`);
  }

  assign(task: Task, participant: Participant) {
    return this.http.post([this.apiUrl, 'task', task.id, 'assign', participant.id].join('/'), []);
  }

  unassign(task: Task, participant: Participant) {
    return this.http.post([this.apiUrl, 'task', task.id, 'unassign', participant.id].join('/'), []);
  }
}
