import { Component, OnInit, OnDestroy } from "@angular/core";
import { User, UserRole } from "../../content/_model/user";
import { LoginService } from "../../content/_service/login.service";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { CoreUserImageService } from "app/main/content/_service/core-user-image.service";
import { UserImage } from "app/main/content/_model/image";
import { UserInfo } from "app/main/content/_model/userInfo";

@Component({
  selector: "fuse-user-menu",
  templateUrl: "./user-menu.component.html",
  styleUrls: ["./user-menu.component.scss"],
})
export class FuseUserMenuComponent implements OnInit, OnDestroy {
  userInfo: UserInfo;
  userImage: UserImage;

  role: UserRole;
  globalInfo: GlobalInfo;

  onRoleChanged: Subscription;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService,
    private userImageService: CoreUserImageService
  ) {
    this.onRoleChanged = this.roleChangeService.onRoleChanged.subscribe(() => {
      this.ngOnInit();
    });
  }

  async ngOnInit() {
    // const globalInfo = this.loginService.getGlobalInfo();
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo2().toPromise()
    );

    this.userInfo = this.globalInfo.userInfo;

    // Don't wait for image...
    this.userImageService
      .findByUserId(this.userInfo.id)
      .toPromise()
      .then((userImage: UserImage) => (this.userImage = userImage));

    await Promise.all([
      (this.role = <UserRole>(
        await this.loginService.getLoggedInUserRole().toPromise()
      )),
    ]);
  }

  ngOnDestroy() {
    this.onRoleChanged.unsubscribe();
  }

  logout() {
    this.loginService.logout();
  }

  getImage() {
    return this.userImageService.getUserProfileImage(this.userImage);
  }

  getUserNameString() {
    let ret = "";
    if (this.userInfo && this.userInfo.firstname && this.userInfo.lastname) {
      ret += this.userInfo.firstname + " " + this.userInfo.lastname;
    } else {
      ret += this.userInfo.username;
    }
    return ret;
  }

  navigateToTenantEditForm() {
    this.router.navigate(
      [`/main/edit-tenant/${this.globalInfo.currentTenants[0].id}`],
      {
        queryParams: {
          marketplaceId: this.globalInfo.currentMarketplaces[0].id,
        },
      }
    );
  }
}
