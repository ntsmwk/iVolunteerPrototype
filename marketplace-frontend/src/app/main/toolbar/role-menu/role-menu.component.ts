import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import {
  User,
  UserRole,
  RoleTenantMapping
} from "app/main/content/_model/user";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { UserService } from "app/main/content/_service/user.service";
import { CoreUserService } from "app/main/content/_service/core-user.service";

@Component({
  selector: "app-role-menu",
  templateUrl: "./role-menu.component.html",
  styleUrls: ["./role-menu.component.scss"]
})
export class RoleMenuComponent implements OnInit, OnDestroy {
  user: User;
  role: UserRole;
  allTenants: Tenant[] = [];

  currentMapping: RoleTenantMapping;
  roleTenantMappings: RoleTenantMapping[] = [];
  possibleRoleTenantMappings: RoleTenantMapping[] = [];

  onRoleChanged: Subscription;
  onUpdate: Subscription;
  isLoaded = false;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService,
    private tenantService: TenantService,
    private userService: CoreUserService
  ) {
    this.onRoleChanged = this.roleChangeService.onRoleChanged.subscribe(() => {
      this.ngOnInit();
    });

    this.onUpdate = this.roleChangeService.onUpdate.subscribe(() => {
      this.ngOnInit();
    });
  }

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.user = globalInfo.user;
    this.role = globalInfo.userRole;

    await Promise.all([
      (this.allTenants = <Tenant[]>(
        await this.tenantService.findAll().toPromise()
      ))
    ]);

    this.currentMapping = new RoleTenantMapping();
    this.currentMapping.role = this.role;
    this.currentMapping.tenantIds = globalInfo.tenants.map(t => t.id);

    this.roleTenantMappings = this.roleChangeService.getRoleTenantMappings(
      this.user
    );

    this.possibleRoleTenantMappings = this.roleTenantMappings.filter(m => {
      return !this.isSameMapping(m, this.currentMapping);
    });

    this.isLoaded = true;
  }

  onRoleSelected(mapping: RoleTenantMapping) {
    this.role = mapping.role;
    this.currentMapping = mapping;
    this.possibleRoleTenantMappings = this.roleTenantMappings.filter(m => {
      return !this.isSameMapping(m, this.currentMapping);
    });

    this.loginService
      .generateGlobalInfo(mapping.role, mapping.tenantIds)
      .then(() => {
        this.roleChangeService.changeRole(mapping.role);

        this.router.navigate(["/login"]).then(() => {
          this.router.navigate(["/main/dashboard"]);
        });
      });
  }

  ngOnDestroy() {
    this.onRoleChanged.unsubscribe();
    this.onUpdate.unsubscribe();
  }

  getCurrentRoleNameString() {
    return this.roleChangeService.getRoleNameString(this.currentMapping.role);
  }

  getRoleNameString(role: UserRole) {
    return this.roleChangeService.getRoleNameString(role);
  }

  getUserImage() {
    return this.userService.getUserProfileImage(this.user);
  }

  getCurrentTenantImage() {
    let tenant = this.allTenants.find(
      t => t.id === this.currentMapping.tenantIds[0]
    );
    return tenant.imagePath;
  }

  getImage(tenantId: string) {
    let tenant = this.allTenants.find(t => t.id === tenantId);
    if (tenant) {
      return tenant.imagePath;
    }
    return "";
  }

  isSameMapping(a: RoleTenantMapping, b: RoleTenantMapping) {
    if (a.role !== b.role) {
      return false;
    } else if (a.tenantIds.length !== b.tenantIds.length) {
      return false;
    } else if (a.tenantIds.every(id => b.tenantIds.indexOf(id) != -1)) {
      return true;
    } else {
      return false;
    }
  }
}
