import { Component, OnInit } from '@angular/core';

import { Participant, UserImagePath } from '../../content/_model/participant';
import { LoginService } from '../../content/_service/login.service';
import { CoreUserImagePathService } from 'app/main/content/_service/core-user-imagepath.service';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';

@Component({
  selector: 'fuse-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.scss']
})

export class FuseUserMenuComponent implements OnInit {

  participant: Participant;
  participantImagepath: UserImagePath;

  constructor(private loginService: LoginService,
    private userImagePathService: CoreUserImagePathService,
    private router: Router) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise()
      .then((participant: Participant) => this.participant = participant)
      .catch(e => console.warn(e)).then(() => {
        this.fetchUserImagePaths();
      });
  }

  logout() {
    localStorage.clear();
    window.location.reload(true);
  }

  fetchUserImagePaths() {
    const users: Participant[] = [];
    users.push(this.participant);
    this.userImagePathService.getImagePathsById(users.map(u => u.id)).toPromise().then((ret: UserImagePath[]) => {
      console.log(ret);
      if (!isNullOrUndefined(ret) && ret.length <= 1) {
        this.participantImagepath = ret[0];
      }
    });
  }

  getImagePath() {

    if (this.router.url === '/main/rules/all' || this.router.url === '/main/rule') {
      return '/assets/images/avatars/OERK_Sonderlogo_rgb_cropped.jpg';
    } else {
      if (isNullOrUndefined(this.participantImagepath)) {
        return '/assets/images/avatars/profile.jpg';
      } else {
        return this.participantImagepath.imagePath;
      }
    }
  }

  getUserNameString() {
    console.log(this.router.url);
    if (this.router.url === '/main/rules/all' || this.router.url === '/main/rule') {
      return "Sandra Wolkerstorfer (Rotes Kreuz)";
    } else {
      let ret = this.participant.firstname + ' ' + this.participant.lastname;

      if (!isNullOrUndefined(this.participant.position)) {
        ret = ret + ' (' + this.participant.position + ')';
      }
      return ret;
    }
  }

}
