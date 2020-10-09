import { Component, OnInit, OnDestroy } from "@angular/core";
import { User, UserRole } from "../../content/_model/user";
import { LoginService } from "../../content/_service/login.service";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { CoreUserService } from "app/main/content/_service/core-user.service";

@Component({
  selector: "fuse-user-menu",
  templateUrl: "./user-menu.component.html",
  styleUrls: ["./user-menu.component.scss"]
})
export class FuseUserMenuComponent implements OnInit, OnDestroy {
  user: User;

  role: UserRole;
  globalInfo: GlobalInfo;

  onRoleChanged: Subscription;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService,
    private userService: CoreUserService
  ) {
    this.onRoleChanged = this.roleChangeService.onRoleChanged.subscribe(() => {
      this.ngOnInit();
    });
  }

  async ngOnInit() {
    (this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    )),
      (this.user = this.globalInfo.user);

    await Promise.all([
      (this.role = <UserRole>(
        await this.loginService.getLoggedInUserRole().toPromise()
      ))
    ]);
  }

  ngOnDestroy() {
    this.onRoleChanged.unsubscribe();
  }

  logout() {
    this.loginService.logout();
  }

  getImage() {
    return this.userService.getUserProfileImage(this.user);
  }

  getUserNameString() {
    let ret = "";
    if (this.user && this.user.firstname && this.user.lastname) {
      ret += this.user.firstname + " " + this.user.lastname;
    } else {
      ret += this.user.username;
    }
    return ret;
  }

  navigateToTenantEditForm() {
    this.router.navigate(
      [`/main/edit-tenant/${this.globalInfo.tenants[0].id}`],
      {
        queryParams: { marketplaceId: this.globalInfo.marketplace.id }
      }
    );
  }
}
