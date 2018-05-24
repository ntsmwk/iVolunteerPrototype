import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class WorkflowService {

  private apiUrl = '/marketplace/workflow';

  constructor(private http: HttpClient) {
  }

  findAllTypes() {
    return this.http.get(`${this.apiUrl}/type`);
  }
}
