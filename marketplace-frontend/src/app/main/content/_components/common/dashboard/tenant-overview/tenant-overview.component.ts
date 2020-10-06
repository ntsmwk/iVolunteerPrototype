import { Component, OnInit } from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { ImageService } from "app/main/content/_service/image.service";
import { LoginService } from "app/main/content/_service/login.service";
import {
  GlobalInfo,
  UserSubscriptionDTO,
} from "app/main/content/_model/global-info";
import { User, UserRole } from "app/main/content/_model/user";
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { UserInfo } from "app/main/content/_model/userInfo";

@Component({
  selector: "tenant-overview",
  templateUrl: "tenant-overview.component.html",
  styleUrls: ["tenant-overview.component.scss"],
})
export class TenantOverviewComponent implements OnInit {
  userInfo: UserInfo;
  currentTenants: Tenant[] = [];
  currentRole: UserRole;
  allTenants: Tenant[] = [];
  userSubscriptions: UserSubscriptionDTO[];

  isLoaded = false;

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService,
    private coreUserService: CoreUserService,
    private roleChangeService: RoleChangeService
  ) {}

  async ngOnInit() {
    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();
    this.updateCurrentInfo();

    this.isLoaded = true;
  }

  getTenantImage(tenant: Tenant) {
    return this.tenantService.getTenantProfileImage(tenant);
  }

  isSubscribed(tenant: Tenant, role: UserRole) {
    return (
      this.userSubscriptions
        .filter((s) => s.tenant.id === tenant.id)
        .findIndex((t) => t.role === role) >= 0
    );
  }

  // TODO: Philipp: evtl problem: vorher wurde this.user mit rückgabewert aktualisiert, jetzt nicht mehr, problem?
  async unsubscribe(tenant: Tenant, role: UserRole) {
    // this.userInfo = <User>(
    await this.coreUserService
      .unsubscribeUserFromTenant(
        this.userInfo.id,
        tenant.marketplaceId,
        tenant.id,
        role
      )
      .toPromise();
    // );

    if (this.userSubscriptions.length === 0) {
      this.loginService.generateGlobalInfo(UserRole.NONE, []).then(() => {
        this.roleChangeService.changeRole(UserRole.NONE);
        this.updateCurrentInfo();
      });
    } else {
      if (
        // current role unssubscribed?
        this.currentRole === role &&
        this.currentTenants.length === 1 &&
        this.currentTenants.map((t) => t.id).indexOf(tenant.id) >= 0
      ) {
        this.loginService
          .generateGlobalInfo(this.userSubscriptions[0].role, [
            this.userSubscriptions[0].tenant.id,
          ])
          .then(() => {
            this.roleChangeService.changeRole(this.userSubscriptions[0].role);
            this.updateCurrentInfo();
          });
      } else {
        this.loginService
          .generateGlobalInfo(
            this.currentRole,
            this.currentTenants.map((t) => t.id)
          )
          .then(() => {
            this.roleChangeService.update();
            this.updateCurrentInfo();
          });
      }
    }
  }

  async subscribe(tenant: Tenant, role: UserRole) {
    this.userInfo = <User>(
      await this.coreUserService
        .subscribeUserToTenant(
          this.userInfo.id,
          tenant.marketplaceId,
          tenant.id,
          role
        )
        .toPromise()
    );

    if (this.currentRole === UserRole.NONE) {
      this.loginService.generateGlobalInfo(role, [tenant.id]).then(() => {
        this.roleChangeService.changeRole(role);
        this.updateCurrentInfo();
      });
    } else {
      this.loginService
        .generateGlobalInfo(
          this.currentRole,
          this.currentTenants.map((t) => t.id)
        )
        .then(() => {
          this.roleChangeService.update();
          this.updateCurrentInfo();
        });
    }
  }

  updateCurrentInfo() {
    const globalInfo = this.loginService.getGlobalInfo();

    this.userInfo = globalInfo.userInfo;
    this.currentTenants = globalInfo.currentTenants;
    this.currentRole = globalInfo.currentRole;
    this.userSubscriptions = globalInfo.userSubscriptions;
  }

  navigateBack() {
    window.history.back();
  }
}
