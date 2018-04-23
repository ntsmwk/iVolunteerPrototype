import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class VolunteerService {

  private apiUrl = '/rest/volunteer';

  constructor(private http: HttpClient) {
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }
}
