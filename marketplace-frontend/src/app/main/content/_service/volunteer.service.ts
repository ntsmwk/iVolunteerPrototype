import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class VolunteerService {

  private endpoint = '/volunteer';

  constructor(private http: HttpClient) {
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/${id}`);
  }

  findCompetencies(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace. url}/${this.endpoint}/${id}/competencies`);
  }
}
