import { Component, OnInit } from "@angular/core";
import { User, UserRole } from "app/main/content/_model/user";
import { fuseAnimations } from "@fuse/animations";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginService } from "app/main/content/_service/login.service";
import { DialogFactoryDirective } from "../../_shared/dialogs/_dialog-factory/dialog-factory.component";
import { UserProfileImageUploadDialogData } from "../../_shared/dialogs/user-profile-image-upload-dialog/user-profile-image-upload-dialog.component";
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations,
  providers: [DialogFactoryDirective]
})
export class ProfileComponent implements OnInit {
  globalInfo: GlobalInfo;
  user: User;
  currentRoles: UserRole[] = [];

  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private userService: CoreUserService,
    private dialogFactory: DialogFactoryDirective,
    private http: HttpClient
  ) {}

  async ngOnInit() {
    this.loaded = false;
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.user = this.globalInfo.user;

    this.currentRoles = this.user.subscribedTenants.map(s => s.role);
    this.loaded = true;
  }

  getProfileImage() {
    return this.userService.getUserProfileImage(this.user);
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }

  handleProfileImageClick() {
    this.dialogFactory
      .openProfileImageUploadDialog(this.user)
      .then((ret: UserProfileImageUploadDialogData) => {
        // console.log(ret);
      });
  }
}
