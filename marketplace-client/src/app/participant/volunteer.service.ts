import {Injectable} from '@angular/core';
import {isNullOrUndefined} from 'util';
import {HttpClient} from '@angular/common/http';
import {Volunteer} from './volunteer';

@Injectable()
export class VolunteerService {

  private apiUrl = '/rest/volunteer';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

  save(volunteer: Volunteer) {
    if (isNullOrUndefined(volunteer.id)) {
      return this.http.post(this.apiUrl, volunteer);
    }
    return this.http.put([this.apiUrl, volunteer.id].join('/'), volunteer);
  }

  remove(volunteer: Volunteer) {
    return this.http.delete([this.apiUrl, volunteer.id].join('/'));
  }
}
