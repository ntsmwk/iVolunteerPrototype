import {Component, OnInit} from '@angular/core';
import {VolunteerProfileService} from '../volunteer-profile.service';
import {LoginService} from '../../login/login.service';
import {Participant} from '../../participant/participant';
import {Volunteer} from '../volunteer';
import {VolunteerProfile} from '../volunteer-profile';

@Component({
  templateUrl: './volunteer-profile.component.html',
  styleUrls: ['./volunteer-profile.component.css']
})
export class VolunteerProfileComponent implements OnInit {
  volunteer: Volunteer;
  volunteerProfile: VolunteerProfile;

  constructor(private loginService: LoginService,
              private volunteerProfileService: VolunteerProfileService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = <Volunteer> participant;
      this.loadVolunteerProfile(this.volunteer);
    });
  }

  private loadVolunteerProfile(volunteer: Volunteer) {
    this.volunteerProfileService.findByVolunteer(volunteer)
      .toPromise()
      .then((volunteerProfile: VolunteerProfile) => this.volunteerProfile = volunteerProfile);
  }
}
