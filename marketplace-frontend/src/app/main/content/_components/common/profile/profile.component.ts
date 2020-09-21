import { Component, OnInit } from '@angular/core';
import { User, UserRole } from 'app/main/content/_model/user';
import { fuseAnimations } from '@fuse/animations';
import { FormGroup } from '@angular/forms';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { UserService } from 'app/main/content/_service/user.service';

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
  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();
  loaded: boolean;

  constructor(
    private userService: UserService,
  ) { }

  async ngOnInit() {
    this.loaded = false;


    this.loaded = true;
  }


  getProfileImage() {
    return this.userService.getUserProfileImage(this.user);
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }
}
