import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Volunteer} from './volunteer';

@Injectable()
export class VolunteerProfileService {

  private apiUrl = '/rest/volunteer';

  constructor(private http: HttpClient) {
  }

  findByVolunteer(volunteer: Volunteer) {
    return this.http.get(`/rest/volunteer/${volunteer.id}/profile/`);
  }


}
