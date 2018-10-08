import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {

  constructor(private http: HttpClient) {
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/competence`);
  }
}
