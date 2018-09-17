import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreEmployeeService {

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(employeeId: string) {
    return this.http.get(`/core/employee/${employeeId}/marketplace`);
  }
}
