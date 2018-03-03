import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskService {

  private apiUrl = '/rest/task';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  findCreated() {
    return this.http.get([this.apiUrl].join('/created'));
  }

  findByVolunteerId(id: string) {
    return this.http.get([this.apiUrl, id].join('/volunteer/'));
  }
}
