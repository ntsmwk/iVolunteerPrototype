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
    return this.http.get([this.apiUrl, 'task', task.id, 'reserved'].join('/'));
  }

  isTaskAlreadyReserved(task: Task) {
    return this.http.get([this.apiUrl, 'volunteer/isReserved', task.id].join('/'));
  }

  isTaskAlreadyAssigned(task: Task) {
    return this.http.get([this.apiUrl, 'volunteer/isAssigned', task.id].join('/'));
  }

  reserve(task: Task) {
    return this.http.post([this.apiUrl, 'volunteer/reserve'].join('/'), task.id);
  }

  unreserve(task: Task) {
    return this.http.post([this.apiUrl, 'volunteer/unreserve'].join('/'), task.id);
  }

  findAssignedVolunteersByTaskId(task: Task) {
    return this.http.get([this.apiUrl, 'task', task.id, 'assigned'].join('/'));
  }

  isVolunteerAlreadyAssigned(task: Task, participant: Participant) {
    return this.http.get([this.apiUrl, 'volunteer/isAssigned', participant.id, 'task', task.id].join('/'));
  }

  assign(task: Task, participant: Participant) {
    return this.http.post([this.apiUrl, 'task', task.id, 'assign', participant.id].join('/'), []);
  }

  unassign(task: Task, participant: Participant) {
    return this.http.post([this.apiUrl, 'task', task.id, 'unassign', participant.id].join('/'), []);
  }
}
