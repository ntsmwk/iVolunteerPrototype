import {Injectable} from '@angular/core';
import {Task} from '../task/task';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskInteractionService {

  private apiUrl = '/rest/task';

  constructor(private http: HttpClient) {
  }

  findById(task: Task) {
    return this.http.get([this.apiUrl, task.id, 'interaction'].join('/'));
  }
}
