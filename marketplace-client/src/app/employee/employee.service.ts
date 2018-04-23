import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class EmployeeService {

  private apiUrl = '/rest/employee';

  constructor(private http: HttpClient) {
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }
}
