import {Injectable} from '@angular/core';
import {isNullOrUndefined} from 'util';
import {Task} from './task';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskService {

  private apiUrl = '/rest/task';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findAllCreated() {
    return this.http.get(this.apiUrl + '?status=CREATED');
  }

  findAllByParticipant(id: string) {
    return this.http.get([this.apiUrl, id].join('/volunteer/'));
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  save(task: Task) {
    if (isNullOrUndefined(task.id)) {
      return this.http.post(this.apiUrl, task);
    }
    return this.http.put([this.apiUrl, task.id].join('/'), task);
  }

  remove(task: Task) {
    return this.http.delete([this.apiUrl, task.id].join('/'));
  }

  start(task: Task) {
    return this.http.post([this.apiUrl, task.id, 'start'].join('/'), {});
  }

  finish(task: Task) {
    return this.http.post([this.apiUrl, task.id, 'finish'].join('/'), {});
  }

  cancel(task: Task) {
    return this.http.post([this.apiUrl, task.id, 'cancel'].join('/'), {});
  }
}
