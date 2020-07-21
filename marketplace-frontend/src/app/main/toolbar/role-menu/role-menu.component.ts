import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { User, UserRole } from "app/main/content/_model/user";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";

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
  allTenants: Tenant[] = [];

  onRoleChanged: Subscription;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService,
    private tenantService: TenantService
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

  async ngOnInit() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();
    this.currentRole = <UserRole>(
      await this.loginService.getLoggedInUserRole().toPromise()
    );
    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();

    if (this.user) {
      this.allRoles = [
        ...new Set(this.user.subscribedTenants.map((s) => s.role)),
      ];
    }

    this.possibleRoles = this.allRoles.filter((r) => {
      return r != this.currentRole;
    });
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

  ngOnDestroy() {
    this.onRoleChanged.unsubscribe();
  }

  getCurrentRoleName() {
    return this.roleChangeService.getRoleNameString(this.currentRole);
  }

  getRoleNameString(role: UserRole) {
    return this.roleChangeService.getRoleNameString(role);
  }
}
