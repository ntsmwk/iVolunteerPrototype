import {Component, OnInit} from '@angular/core';
import {ArrayService} from '../../_service/array.service';
import {LoginService} from '../../_service/login.service';
import {TaskEntry} from '../../_model/task-entry';
import {CompetenceEntry} from '../../_model/competence-entry';
import {Volunteer} from '../../_model/volunteer';
import {VolunteerProfile} from '../../_model/volunteer-profile';
import {VolunteerProfileService} from '../../_service/volunteer-profile.service';
import {VolunteerRepositoryService} from '../../_service/volunteer-repository.service';
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

  containsCompetenceInPublic(competenceEntry: CompetenceEntry) {
    return !isNullOrUndefined(this.publicProfile) && this.arrayService.contains(this.publicProfile.competenceList, competenceEntry);
  }

  containsCompetenceInPrivate(competenceEntry: CompetenceEntry) {
    return !isNullOrUndefined(this.privateProfile) && this.arrayService.contains(this.privateProfile.competenceList, competenceEntry);
  }

  shareTask(taskEntry: TaskEntry) {
    this.volunteerProfileService.shareTaskByVolunteer(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is published to marketplace.');
      this.publicProfile.taskList.push(taskEntry);
    });
  }

  revokeTask(taskEntry: TaskEntry) {
    this.volunteerProfileService.revokeTaskByVolunteer(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is revoked from marketplace.');
      this.publicProfile.taskList = this.publicProfile.taskList.filter((task: TaskEntry) => task.id !== taskEntry.id);
    });
  }

  synchronizeTask(taskEntry: TaskEntry) {
    this.volunteerRepositoryService.synchronizeTask(this.volunteer, taskEntry).toPromise().then(() => {
      alert('Task is synchronized to local repository.');
      this.privateProfile.taskList.push(taskEntry);
    });
  }

  shareCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerProfileService.shareCompetenceByVolunteer(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is published to marketplace.');
      this.publicProfile.competenceList.push(competenceEntry);
    });
  }

  revokeCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerProfileService.revokeCompetenceByVolunteer(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is revoked from marketplace.');
      this.publicProfile.competenceList = this.publicProfile.competenceList
        .filter((competence: CompetenceEntry) => competence.id !== competenceEntry.id);
    });
  }

  synchronizeCompetence(competenceEntry: CompetenceEntry) {
    this.volunteerRepositoryService.synchronizeCompetence(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is synchronized to local repository.');
      this.privateProfile.competenceList.push(competenceEntry);
    });
  }
}
