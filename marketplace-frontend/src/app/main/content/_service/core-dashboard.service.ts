import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreDashboardService {

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(`/core/dashboard`);
  }

  findById(dashboardId: string) {
    return this.http.get(`/core/dashboard/${dashboardId}`);
  }
}
