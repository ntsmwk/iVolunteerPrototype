import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import {
  User,
  UserRole,
  roleTenantMapping,
} from "app/main/content/_model/user";
import { ImageService } from "app/main/content/_service/image.service";
import { fuseAnimations } from "@fuse/animations";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Router } from "@angular/router";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { isNullOrUndefined } from "util";

@Component({
  selector: "app-role-switch",
  templateUrl: "./role-switch.component.html",
  styleUrls: ["./role-switch.component.scss"],
  animations: fuseAnimations,
})
export class RoleSwitchComponent implements OnInit {
  user: User;
  allTenants: Tenant[] = [];
  roleTenantMappings: roleTenantMapping[] = [];

  isLoaded: boolean = false;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private imageService: ImageService,
    private tenantService: TenantService,
    private roleChangeService: RoleChangeService
  ) {}

  async ngOnInit() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();

    this.roleTenantMappings = this.roleChangeService.getRoleTenantMappings(
      this.user
    );

    if (this.roleTenantMappings.length === 1) {
      this.onRoleSelected(this.roleTenantMappings[0]);
    }

    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();
    this.isLoaded = true;
  }

  onRoleSelected(mapping: roleTenantMapping) {
    this.loginService
      .generateGlobalInfo(mapping.role, mapping.tenantIds)
      .then(() => {
        this.router.navigate(["/main/dashboard"]).then(() => {
          this.roleChangeService.changeRole(mapping.role);
        });
      });
  }

  getTenant(tenantId: string) {
    return this.allTenants.filter((t) => t.id === tenantId);
  }

  getTenantImage(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    if (isNullOrUndefined(tenant)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return this.imageService.getImgSourceFromBytes(tenant.image);
    }
  }

  getTenantNameString(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    return tenant.name;
  }

  getRoleNameString(role: UserRole) {
    return this.roleChangeService.getRoleNameString(role);
  }
}