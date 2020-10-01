import { Component, OnInit } from '@angular/core';
import { User, UserRole } from 'app/main/content/_model/user';
import { fuseAnimations } from '@fuse/animations';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { LoginService } from 'app/main/content/_service/login.service';
import { UserImage } from 'app/main/content/_model/image';
import { CoreUserImageService } from 'app/main/content/_service/core-user-image.service';
import { DialogFactoryDirective } from '../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { UserProfileImageUploadDialogData } from '../../_shared/dialogs/user-profile-image-upload-dialog/user-profile-image-upload-dialog.component';

@Component({
  selector: "profile",
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.scss'],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  globalInfo: GlobalInfo;
  user: User;
  userImage: UserImage;
  currentRoles: UserRole[] = [];

  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private userImageService: CoreUserImageService,
    private dialogFactory: DialogFactoryDirective,
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    this.user = this.globalInfo.user;

    // Don't wait for image...
    this.userImageService.findByUserId(this.user.id).toPromise().then((userImage: UserImage) => this.userImage = userImage);

    this.currentRoles = this.user.subscribedTenants.map((s) => s.role);
    this.loaded = true;
  }


  getProfileImage() {
    return this.userImageService.getUserProfileImage(this.userImage);
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }

  handleProfileImageClick() {
    this.dialogFactory.openProfileImageUploadDialog().then((ret: UserProfileImageUploadDialogData) => {
      console.log(ret);
    });
  }
}
