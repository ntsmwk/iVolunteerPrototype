import {Component, OnInit} from '@angular/core';
import {ArrayService} from '../../_service/array.service';
import {LoginService} from '../../login/login.service';
import {TaskEntry} from '../task-entry';
import {CompetenceEntry} from '../competence-entry';
import {Volunteer} from '../volunteer';
import {VolunteerProfile} from '../volunteer-profile';
import {VolunteerProfileService} from '../volunteer-profile.service';
import {VolunteerRepositoryService} from '../volunteer-repository.service';
import {isNullOrUndefined} from 'util';

@Component({
  templateUrl: './volunteer-profile.component.html',
  styleUrls: ['./volunteer-profile.component.css']
})
export class VolunteerProfileComponent implements OnInit {
  private volunteer: Volunteer;
  private publicProfile: VolunteerProfile;
  private privateProfile: VolunteerProfile;

  public commonProfile: VolunteerProfile;

  constructor(private arrayService: ArrayService,
              private loginService: LoginService,
              private volunteerProfileService: VolunteerProfileService,
              private volunteerRepositoryService: VolunteerRepositoryService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {
      this.volunteer = volunteer;
      this.loadPublicVolunteerProfile(this.volunteer);
      this.loadPrivateVolunteerProfile(this.volunteer);
    });
  }

  private loadPublicVolunteerProfile(volunteer: Volunteer) {
    return this.volunteerProfileService.findByVolunteer(volunteer)
      .toPromise()
      .then((volunteerProfile: VolunteerProfile) => {
        this.publicProfile = volunteerProfile;
        this.onLoadingComplete();
      });
  }

  private loadPrivateVolunteerProfile(volunteer: Volunteer) {
    return this.volunteerRepositoryService.findByVolunteer(volunteer)
      .toPromise()
      .then((volunteerProfile: VolunteerProfile) => {
        this.privateProfile = volunteerProfile;
        this.onLoadingComplete();
      });
  }

  private onLoadingComplete() {
    if (isNullOrUndefined(this.publicProfile)) {
      return;
    }
    if (isNullOrUndefined(this.privateProfile)) {
      this.commonProfile = this.publicProfile;
    } else {
      this.commonProfile = new VolunteerProfile();
      this.commonProfile.volunteer = this.volunteer;
      this.commonProfile.taskList = this.arrayService.concat(this.publicProfile.taskList, this.privateProfile.taskList);
      this.commonProfile.competenceList = this.arrayService.concat(this.publicProfile.competenceList, this.privateProfile.competenceList);
    }
  }

  containsTaskInPublic(taskEntry: TaskEntry) {
    return !isNullOrUndefined(this.publicProfile) && this.arrayService.contains(this.publicProfile.taskList, taskEntry);
  }

  containsTaskInPrivate(taskEntry: TaskEntry) {
    return !isNullOrUndefined(this.privateProfile) && this.arrayService.contains(this.privateProfile.taskList, taskEntry);
  }

  containsComptenceInPublic(competenceEntry: CompetenceEntry) {
    return !isNullOrUndefined(this.publicProfile) && this.arrayService.contains(this.publicProfile.competenceList, competenceEntry);
  }

  containsComptenceInPrivate(competenceEntry: CompetenceEntry) {
    return !isNullOrUndefined(this.privateProfile) && this.arrayService.contains(this.privateProfile.competenceList, competenceEntry);
  }

  shareTask(taskEntry: TaskEntry) {
    this.volunteerProfileService.shareTaskByVolunteer(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is shared.');
      this.publicProfile.taskList.push(taskEntry);
    });
  }

  revokeTask(taskEntry: TaskEntry) {
    this.volunteerProfileService.revokeTaskByVolunteer(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is revoked.');
      this.publicProfile.taskList = this.publicProfile.taskList.filter((task: TaskEntry) => task.id !== taskEntry.id);
    });
  }

  synchronizeTask(taskEntry: TaskEntry) {
    this.volunteerRepositoryService.synchronizeTask(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is synchronized.');
      this.privateProfile.taskList.push(taskEntry);
    });
  }

  shareCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerProfileService.shareCompetenceByVolunteer(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is shared.');
      this.publicProfile.competenceList.push(competenceEntry);
    });
  }

  revokeCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerProfileService.revokeCompetenceByVolunteer(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is revoked.');
      this.publicProfile.competenceList = this.publicProfile.competenceList
        .filter((competence: CompetenceEntry) => competence.id !== competenceEntry.id);
    });
  }

  synchronizeCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerRepositoryService.synchronizeCompetence(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is synchronized.');
      this.privateProfile.competenceList.push(competenceEntry);
    });
  }
}
