import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreEmployeeService {

  private apiUrl = '/core/employee';

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(employeeId: string) {
    return this.http.get(`${this.apiUrl}/${employeeId}/marketplace`);
  }
}