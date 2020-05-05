import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Volunteer } from '../_model/volunteer';
import { TaskEntry } from '../_model/task-entry';
import { CompetenceEntry } from '../_model/competence-entry';

@Injectable({
  providedIn: 'root'
})
export class VolunteerProfileService {

  constructor(private http: HttpClient) {
  }

  findByVolunteer(volunteer: Volunteer, url: string) {
    return this.http.get(`${url}/volunteer/${volunteer.id}/profile`);
  }

  shareTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry, url: string) {
    return this.http.post(`${url}/volunteer/${volunteer.id}/profile/task`, taskEntry);
  }

  revokeTaskByVolunteer(volunteer: Volunteer, taskEntry: TaskEntry, url: string) {
    return this.http.delete(`${url}/volunteer/${volunteer.id}/profile/task/${taskEntry.id}`);
  }

  shareCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry, url: string) {
    return this.http.post(`${url}/volunteer/${volunteer.id}/profile/competence`, competenceEntry);
  }

  revokeCompetenceByVolunteer(volunteer: Volunteer, competenceEntry: CompetenceEntry, url: string) {

    return this.http.delete(`${url}/volunteer/${volunteer.id}/profile/competence/${competenceEntry.competenceId}`);
  }
}
