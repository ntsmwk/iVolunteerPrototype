import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {TaskTemplate} from '../_model/task-template';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class TaskTemplateService {

  constructor(private http: HttpClient) {
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/taskTemplate`);
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/taskTemplate/${id}`);
  }

  save(marketplace: Marketplace, taskTemplate: TaskTemplate) {
    if (isNullOrUndefined(taskTemplate.id)) {
      return this.http.post(`${marketplace.url}/taskTemplate`, taskTemplate);
    }
    return this.http.put(`${marketplace.url}/taskTemplate/${taskTemplate.id}`, taskTemplate);
  }


  findAllMinimal(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/minimalTaskTemplate`);
  }
}
