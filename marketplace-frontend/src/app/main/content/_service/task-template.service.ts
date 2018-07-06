import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskTemplate} from '../_model/task-template';
import {isNullOrUndefined} from 'util';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class TaskTemplateService {

  private endpoint = 'taskTemplate';

  constructor(private http: HttpClient) {
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/${this.endpoint}`);
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/${id}`);
  }

  save(marketplace: Marketplace, taskTemplate: TaskTemplate) {
    if (isNullOrUndefined(taskTemplate.id)) {
      return this.http.post(`${marketplace.url}/${this.endpoint}`, taskTemplate);
    }
    return this.http.put(`${marketplace.url}/${this.endpoint}/${taskTemplate.id}`, taskTemplate);
  }
}
