import { Component, OnInit } from '@angular/core';
import { User, UserRole } from 'app/main/content/_model/user';
import { fuseAnimations } from '@fuse/animations';
import { FormGroup } from '@angular/forms';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { UserService } from 'app/main/content/_service/user.service';
import { LoginService } from 'app/main/content/_service/login.service';

@Component({
  selector: "profile",
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.scss'],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  globalInfo: GlobalInfo;
  user: User;
  currentRoles: UserRole[] = [];

  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private userService: UserService,
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    this.user = this.globalInfo.user;
    this.currentRoles = this.user.subscribedTenants.map((s) => s.role);
    this.loaded = true;
  }


  getProfileImage() {
    return this.userService.getUserProfileImage(this.user);
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }
}
