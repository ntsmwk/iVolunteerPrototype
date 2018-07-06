import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreVolunteerService {

  private apiUrl = '/core/volunteer';

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(volunteerId: string) {
    return this.http.get(`${this.apiUrl}/${volunteerId}/marketplaces`);
  }

  registerMarketplace(volunteerId: string, marketplaceId: string) {
    return this.http.post(`${this.apiUrl}/${volunteerId}/register/${marketplaceId}`, {});
  }
}
