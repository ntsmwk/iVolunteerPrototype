import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskTemplate} from '../_model/task-template';
import {isNullOrUndefined} from 'util';

@Injectable()
export class TaskTemplateService {

  private apiUrl = '/marketplace/taskTemplate';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  save(taskTemplate: TaskTemplate) {
    if (isNullOrUndefined(taskTemplate.id)) {
      return this.http.post(this.apiUrl, taskTemplate);
    }
    return this.http.put([this.apiUrl, taskTemplate.id].join('/'), taskTemplate);
  }

  remove(taskTemplate: TaskTemplate) {
    return this.http.delete([this.apiUrl, taskTemplate.id].join('/'));
  }
}
