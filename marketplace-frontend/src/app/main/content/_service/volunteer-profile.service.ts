import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Volunteer } from '../_model/volunteer';
import { TaskEntry } from '../_model/task-entry';
import { CompetenceEntry } from '../_model/competence-entry';

@Injectable()
export class VolunteerProfileService {

  private apiUrl = '/marketplace/volunteer';

  constructor(private http: HttpClient) {
  }

  findByVolunteer(volunteer: Volunteer) {
    return this.http.get(`${this.apiUrl}/${volunteer.id}/profile`);
  }

  findTasksByVolunteer(volunteer: Volunteer) {
    return this.http.get(`${this.apiUrl}/${volunteer.id}/profile/task`);
  }

  shareTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry) {
    return this.http.post(`${this.apiUrl}/${volunteer.id}/profile/task`, taskEntry);
  }

  revokeTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry) {
    return this.http.delete(`${this.apiUrl}/${volunteer.id}/profile/task/${taskEntry.id}`);
  }

  findCompetencesByVolunteer(volunteer: Volunteer) {
    console.error("service");
    return this.http.get(`${this.apiUrl}/${volunteer.id}/profile/competence`);
  }

  shareCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry) {
    return this.http.post(`${this.apiUrl}/${volunteer.id}/profile/competence`, competenceEntry);
  }

  revokeCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry) {
    return this.http.delete(`${this.apiUrl}/${volunteer.id}/profile/competence/${competenceEntry.id}`);
  }
}