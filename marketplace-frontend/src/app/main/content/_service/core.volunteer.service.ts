import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class CoreVolunteerService {

  private apiUrl = '/core/volunteer';

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(id: string) {
    console.error(`${this.apiUrl}/${id}/marketplaces`);
    return this.http.get(`${this.apiUrl}/${id}/marketplaces`);
  }
}
