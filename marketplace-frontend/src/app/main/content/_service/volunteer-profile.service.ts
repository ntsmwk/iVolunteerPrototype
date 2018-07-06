import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Volunteer} from '../_model/volunteer';
import {TaskEntry} from '../_model/task-entry';
import {CompetenceEntry} from '../_model/competence-entry';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class VolunteerProfileService {

  private endpoint = '/volunteer';

  constructor(private http: HttpClient) {
  }

  findByVolunteer(volunteer: Volunteer, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${volunteer.id}/profile`);
  }

  findTasksByVolunteer(volunteer: Volunteer, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${volunteer.id}/profile/task`);
  }

  shareTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${volunteer.id}/profile/task`, taskEntry);
  }

  revokeTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry, url: string) {
    return this.http.delete(`${url}/${this.endpoint}/${volunteer.id}/profile/task/${taskEntry.id}`);
  }

  findCompetencesByVolunteer(marketplace: Marketplace, volunteer: Volunteer) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/${volunteer.id}/profile/competence`);
  }

  shareCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${volunteer.id}/profile/competence`, competenceEntry);
  }

  revokeCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry, url: string) {
    return this.http.delete(`${url}/${this.endpoint}/${volunteer.id}/profile/competence/${competenceEntry.id}`);
  }
}
