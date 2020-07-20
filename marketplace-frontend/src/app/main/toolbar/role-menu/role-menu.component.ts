import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: "app-role-menu",
  templateUrl: "./role-menu.component.html",
  styleUrls: ["./role-menu.component.scss"],
})
export class RoleMenuComponent implements OnInit, OnDestroy {
  user: User;
  currentRole: UserRole;
  allRoles: UserRole[] = [];
  possibleRoles: UserRole[] = [];

  onRoleChanged: Subscription;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService
  ) {
    this.onRoleChanged = this.roleChangeService.onRoleChanged.subscribe(
      (newRole) => {
        this.currentRole = newRole;
        this.possibleRoles = this.allRoles.filter((r) => {
          return r != this.currentRole;
        });
      }
    );
  }
  ngOnDestroy() {
    this.onRoleChanged.unsubscribe();
  }

  async ngOnInit() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();
    this.currentRole = <UserRole>(
      await this.loginService.getLoggedInUserRole().toPromise()
    );

    if (this.user) {
      this.allRoles = [
        ...new Set(this.user.subscribedTenants.map((s) => s.role)),
      ];
    }

    this.possibleRoles = this.allRoles.filter((r) => {
      return r != this.currentRole;
    });
  }

  getCurrentRoleName() {
    return this.roleChangeService.getRoleNameString(this.currentRole);
  }

  getRoleNameString(role: UserRole) {
    return this.roleChangeService.getRoleNameString(role);
  }

  getRoleImage(role: UserRole) {
    return "/assets/images/avatars/profile.jpg";
  }

  onRoleSelected(role: UserRole) {
    this.loginService.generateGlobalInfo(role).then(() => {
      this.roleChangeService.changeRole(role);

      this.possibleRoles = this.allRoles.filter((r) => {
        return r != role;
      });

      this.router.navigate(["/login"]).then(() => {
        this.router.navigate(["/main/dashboard"]);
      });
    });
  }
}
