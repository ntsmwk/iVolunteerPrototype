import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Task} from '../_model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private endpoint = '/task';

  constructor(private http: HttpClient) {
  }

  findAll(url: string) {
    return this.http.get(this.endpoint);
  }

  findAllPublished(url: string) {
    return this.http.get(`${url}/${this.endpoint}?status=PUBLISHED`);
  }

  findAllByParticipant(id: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/volunteer/${id}`);
  }

  findById(id: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${id}`);
  }

  save(task: Task, url: string) {
    if (isNullOrUndefined(task.id)) {
      return this.http.post(`${url}/${this.endpoint}`, task);
    }
    return this.http.put(`${url}/${this.endpoint}/${task.id}`, task);
  }

  remove(task: Task, url: string) {
    return this.http.delete(`${url}/${this.endpoint}/${task.id}`);
  }

  publish(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/publish`, {});
  }

  start(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/start`, {});
  }

  suspend(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/suspend`, {});
  }

  resume(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/resume`, {});
  }

  finish(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/finish`, {});
  }

  abort(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/abort`, {});
  }

  sync(task: Task, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${task.id}/sync`, {});
  }

  getTree(id: string, url: string) {
    return this.http.get(`${this.endpoint}/${id}/tree`, {});

  }

}
