import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreVolunteerService {

  private apiUrl = '/core/volunteer';

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(volunterId: string) {
    return this.http.get(`${this.apiUrl}/${volunterId}/marketplace`);
  }
}
