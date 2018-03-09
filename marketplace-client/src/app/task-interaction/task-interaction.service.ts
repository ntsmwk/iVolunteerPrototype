import {Injectable} from '@angular/core';
import {Task} from '../task/task';
import {HttpClient} from '@angular/common/http';

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

  reserve(task: Task) {
    return this.http.post([this.apiUrl, 'volunteer/reserve'].join('/'), task.id);
  }
}
