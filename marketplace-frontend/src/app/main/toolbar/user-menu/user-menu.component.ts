import { Component, OnInit } from "@angular/core";

import { Participant, UserImagePath } from "../../content/_model/participant";
import { LoginService } from "../../content/_service/login.service";
import { CoreUserImagePathService } from "app/main/content/_service/core-user-imagepath.service";
import { isNullOrUndefined } from "util";
import { Router } from "@angular/router";

@Component({
  selector: "fuse-user-menu",
  templateUrl: "./user-menu.component.html",
  styleUrls: ["./user-menu.component.scss"]
})
export class FuseUserMenuComponent implements OnInit {
  participant: Participant;
  participantImagepath: UserImagePath;

  constructor(
    private loginService: LoginService,
    private userImagePathService: CoreUserImagePathService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((participant: Participant) => (this.participant = participant))
      .catch(e => console.warn(e))
      .then(() => {
        if (this.participant != null) {
          this.fetchUserImagePaths();
        }
      });
  }

  logout() {
    localStorage.clear();
    window.location.reload(true);
  }

  fetchUserImagePaths() {
    const users: Participant[] = [];
    if (this.participant) {
      users.push(this.participant);
      this.userImagePathService
        .getImagePathsById(users.map(u => u.id))
        .toPromise()
        .then((ret: UserImagePath[]) => {
          if (!isNullOrUndefined(ret) && ret.length <= 1) {
            this.participantImagepath = ret[0];
          }
        });
    }
  }

  getImagePath() {
    if (isNullOrUndefined(this.participantImagepath)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return this.participantImagepath.imagePath;
    }
  }

  getUserNameString() {
    let ret = "";
    if (
      this.participant &&
      this.participant.firstname &&
      this.participant.lastname
    ) {
      ret += this.participant.firstname + " " + this.participant.lastname;
    } else {
      ret += this.participant.username;
    }

    if (!isNullOrUndefined(this.participant.position)) {
      ret += " (" + this.participant.position + ")";
    }
    return ret;
  }
}
