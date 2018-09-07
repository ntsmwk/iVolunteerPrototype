import {Component, OnInit} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {Participant} from '../_model/participant';
import {isNullOrUndefined} from 'util';
import {CoreVolunteerService} from '../_service/core-volunteer.service';
import {Volunteer} from '../_model/volunteer';

@Component({
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  animations: fuseAnimations
})
export class FuseProfileComponent implements OnInit {
  private mySelf: boolean;

  public participant: Participant;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private volunteerService: CoreVolunteerService) {

  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      const participantId = params['participantId'];
      if (isNullOrUndefined(participantId)) {
        this.mySelf = true;
        this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant);
      } else {
        this.mySelf = false;
        this.volunteerService.findById(participantId).toPromise().then((volunteer: Volunteer) => this.participant = volunteer);
      }
    });
  }

  isMySelf() {
    return this.mySelf;
  }
}
