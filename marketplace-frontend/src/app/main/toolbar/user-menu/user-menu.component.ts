import { Component, OnInit } from "@angular/core";

import { User, UserRole } from "../../content/_model/user";
import { LoginService } from "../../content/_service/login.service";
import { isNullOrUndefined } from "util";

import { ImageService } from "app/main/content/_service/image.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Router } from "@angular/router";

@Component({
  selector: "fuse-user-menu",
  templateUrl: "./user-menu.component.html",
  styleUrls: ["./user-menu.component.scss"],
})
export class FuseUserMenuComponent implements OnInit {
  user: User;
  role: UserRole;

  constructor(
    private router: Router,
    private imageService: ImageService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();

    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.role = globalInfo.userRole;
  }

  logout() {
    localStorage.clear();
    this.router.navigate(["/login"]).then(() => {
      window.location.reload(true);
    });
  }

  getImage() {
    if (isNullOrUndefined(this.user.image)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return this.imageService.getImgSourceFromBytes(this.user.image);
    }
  }

  getUserNameString() {
    let ret = "";
    if (this.user && this.user.firstname && this.user.lastname) {
      ret += this.user.firstname + " " + this.user.lastname;
    } else {
      ret += this.user.username;
    }

    if (!isNullOrUndefined(this.user.position)) {
      ret += " (" + this.user.position + ")";
    }
    return ret;
  }

  getRoleString() {
    if (this.role == UserRole.NONE || this.role == undefined) {
      return "";
    } else {
      return " (" + this.role + ")";
    }
  }
}
