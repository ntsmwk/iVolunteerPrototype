import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import {
  User,
  UserRole,
  RoleTenantMapping,
  AccountType,
} from "app/main/content/_model/user";
import { ImageService } from "app/main/content/_service/image.service";
import { fuseAnimations } from "@fuse/animations";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Router } from "@angular/router";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { isNullOrUndefined } from "util";
import { UserInfo } from "app/main/content/_model/userInfo";
import { UserService } from "app/main/content/_service/user.service";
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "app-role-switch",
  templateUrl: "./role-switch.component.html",
  styleUrls: ["./role-switch.component.scss"],
  animations: fuseAnimations,
})
export class RoleSwitchComponent implements OnInit {
  userInfo: UserInfo;
  user: User;
  allTenants: Tenant[] = [];
  roleTenantMappings: RoleTenantMapping[] = [];

  isLoaded = false;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private userService: CoreUserService,
    private imageService: ImageService,
    private tenantService: TenantService,
    private roleChangeService: RoleChangeService
  ) {}

  async ngOnInit() {
    this.userInfo = <UserInfo>await this.loginService.getLoggedIn().toPromise();

    // let globalInfo = this.loginService.getGlobalInfo();
    // const globalInfo = <GlobalInfo>(
    //   await this.loginService.getGlobalInfo2().toPromise()
    // );
    // this.userInfo = globalInfo.userInfo;
    // let userSubscriptions = globalInfo.userSubscriptions;

    // this.user = <User>(
    //   await this.userService.findById(this.userInfo.id).toPromise()
    // );

    this.roleTenantMappings = this.roleChangeService.getRoleTenantMappings(
      userSubscriptions
    );

    if (
      this.roleTenantMappings.length === 0 &&
      this.userInfo.accountType === AccountType.PERSON
    ) {
      this.loginService.updateGlobalInfoRole(UserRole.NONE, []);
      this.router.navigate(["/main/dashboard/tenants"]);
    } else if (
      this.roleTenantMappings.length === 0 &&
      this.userInfo.accountType === AccountType.ORGANIZATION
    ) {
      this.loginService.updateGlobalInfoRole(UserRole.TENANT_ADMIN, []);
      this.router.navigate(["/main/create-tenant"]);
    } else if (this.roleTenantMappings.length === 1) {
      this.onRoleSelected(this.roleTenantMappings[0]);
    }

    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();
    this.isLoaded = true;
  }

  onRoleSelected(mapping: RoleTenantMapping) {
    //@AK fehler hier?

    this.loginService.updateGlobalInfoRole(mapping.role, mapping.tenantIds);
    this.router.navigate(["/main/dashboard"]).then(() => {
      this.roleChangeService.changeRole(mapping.role);
    });
  }

  getTenant(tenantId: string) {
    return this.allTenants.filter((t) => t.id === tenantId);
  }

  getTenantImage(tenantId: string) {
    const tenant = this.allTenants.find((t) => t.id === tenantId);
    return this.tenantService.getTenantProfileImage(tenant);
  }

  getTenantNameString(tenantId: string) {
    const tenant = this.allTenants.find((t) => t.id === tenantId);
    return tenant.name;
  }

  getRoleNameString(role: UserRole) {
    return this.roleChangeService.getRoleNameString(role);
  }
}
