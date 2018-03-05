import {Injectable} from '@angular/core';
import {Task} from '../task/task';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskInteractionService {

  private apiUrl = '/rest';

  constructor(private http: HttpClient) {
  }

  findByTask(task: Task) {
    return this.http.get('/rest/task/interaction');
  }

  findFinishedByTask(task: Task) {
    return this.http.get('/rest/task/interaction?operation=FINISHED');
  }

  reserve(task: Task) {
    return this.http.post([this.apiUrl, 'volunteer/reserve'].join('/'), task.id);
  }
}
