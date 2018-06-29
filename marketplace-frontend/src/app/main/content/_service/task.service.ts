import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Task} from '../_model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl = '/marketplace/task';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findAllPublished() {
    return this.http.get(this.apiUrl + '?status=PUBLISHED');
  }

  findByParticipantAndState(id: string, state: string) {
    return this.http.get([[this.apiUrl, id].join('/volunteer/'), state].join('/'));
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

  publish(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/publish`, {});
  }

  start(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/start`, {});
  }

  suspend(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/suspend`, {});
  }

  resume(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/resume`, {});
  }

  finish(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/finish`, {});
  }

  abort(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/abort`, {});
  }

  sync(task: Task) {
    return this.http.post(`${this.apiUrl}/${task.id}/sync`, {});
  }

  getTree(id: string) {
    return this.http.get(`${this.apiUrl}/${id}/tree`, {});

  }

}
