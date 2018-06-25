import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class VolunteerService {

  private apiUrl = '/marketplace/volunteer';

  constructor(private http: HttpClient) {
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  findCompetencies(id: string) {
    return this.http.get([this.apiUrl, id, "competencies"].join('/'))
  }
}
