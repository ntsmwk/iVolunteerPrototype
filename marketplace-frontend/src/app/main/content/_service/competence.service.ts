import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {

  private endpoint = '/competence';

  constructor(private http: HttpClient) {
  }

  findAll(url: string) {
    return this.http.get(`${url}/${this.endpoint}`);
  }
}
