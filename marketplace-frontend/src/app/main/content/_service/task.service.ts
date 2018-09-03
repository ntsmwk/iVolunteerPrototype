import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Task} from '../_model/task';
import {Marketplace} from '../_model/marketplace';
import {Participant} from '../_model/participant';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) {
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/task/${id}`);
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/task`);
  }

  findByProjectId(marketplace: Marketplace, projectId: string) {
    return this.http.get(`${marketplace.url}/task?projectId=${projectId}`);
  }

  findByParticipant(marketplace: Marketplace, participant: Participant) {
    return this.http.get(`${marketplace.url}/task?participantId=${participant.id}`);
  }

  save(marketplace: Marketplace, task: Task) {
    if (isNullOrUndefined(task.id)) {
      return this.http.post(`${marketplace.url}/task`, task);
    }
    return this.http.put(`${marketplace.url}/task/${task.id}`, task);
  }


}
