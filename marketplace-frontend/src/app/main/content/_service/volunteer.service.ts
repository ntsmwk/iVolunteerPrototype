import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VolunteerService {

  private endpoint = '/volunteer';

  constructor(private http: HttpClient) {
  }

  findById(id: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${id}`);
  }

  findCompetencies(id: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${id}/competencies`);
  }
}
