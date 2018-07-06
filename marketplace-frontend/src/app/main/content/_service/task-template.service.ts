import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskTemplate} from '../_model/task-template';
import {isNullOrUndefined} from 'util';

@Injectable()
export class TaskTemplateService {

  private endpoint = 'taskTemplate';

  constructor(private http: HttpClient) {
  }

  findAll(url: string) {
    return this.http.get(`${url}/${this.endpoint}`);
  }

  findById(id: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${id}`);
  }

  save(taskTemplate: TaskTemplate, url: string) {
    if (isNullOrUndefined(taskTemplate.id)) {
      return this.http.post(`${url}/${this.endpoint}`, taskTemplate);
    }
    return this.http.put(`${url}/${this.endpoint}/${taskTemplate.id}`, taskTemplate);
  }

  remove(taskTemplate: TaskTemplate, url: string) {
    return this.http.delete(`${url}/${this.endpoint}/${taskTemplate.id}`);
  }
}