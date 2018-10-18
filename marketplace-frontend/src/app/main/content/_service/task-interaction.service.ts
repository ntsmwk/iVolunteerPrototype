import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Task} from '../_model/task';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class TaskInteractionService {

  constructor(private http: HttpClient) {
  }

  findByTask(marketplace: Marketplace, task: Task) {
    return this.http.get(`${marketplace.url}/task/${task.id}/interaction`);
  }

}
