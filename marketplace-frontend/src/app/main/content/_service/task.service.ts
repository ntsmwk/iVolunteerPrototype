import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Task} from '../_model/task';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private endpoint = 'task';

  constructor(private http: HttpClient) {
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/task/${id}`);
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/task`);
  }

  findAllByProjectId(marketplace: Marketplace, projectId: string) {
    return this.http.get(`${marketplace.url}/task?projectId=${projectId}`);
  }
  
  findAvailableByProjectId(marketplace: Marketplace, projectId: string) {
    return this.http.get(`${marketplace.url}/task?projectId=${projectId}&availableOnly=true`);
  }

  findByParticipantAndState(marketplace: Marketplace, volunteerId: string, state: string) {
    return this.http.get(`${marketplace.url}/task/volunteer/${volunteerId}/${state}`);
  }

  save(marketplace: Marketplace, task: Task) {
    if (isNullOrUndefined(task.id)) {
      return this.http.post(`${marketplace.url}/task`, task);
    }
    return this.http.put(`${marketplace.url}/task/${task.id}`, task);
  }


}
