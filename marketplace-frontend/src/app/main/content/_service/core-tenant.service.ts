import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreTenantService {

  constructor(private http: HttpClient) {
  }

  findByName(tenantName: string) {
    return this.http.get(`/core/tenant/${tenantName}`, {responseType: 'text'});
  }

}
