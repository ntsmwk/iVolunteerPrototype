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

  calculateTaskClass(taskEntry: TaskEntry) {
    const containsInPublic = this.arrayService.contains(this.publicProfile.taskList, taskEntry);
    const containsInPrivate = !isNullOrUndefined(this.privateProfile) && this.arrayService.contains(this.privateProfile.taskList, taskEntry);
    if (containsInPublic && containsInPrivate) {
      return {};
    }
    return {
      public: containsInPublic,
      private: containsInPrivate
    };
  }

  calculateCompetenceClass(competenceEntry: CompetenceEntry) {
    const containsInPublic = this.arrayService.contains(this.publicProfile.competenceList, competenceEntry);
    const containsInPrivate = !isNullOrUndefined(this.privateProfile) && this.arrayService.contains(this.privateProfile.competenceList, competenceEntry);
    if (containsInPublic && containsInPrivate) {
      return {};
    }
    return {
      public: containsInPublic,
      private: containsInPrivate
    };
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
}
