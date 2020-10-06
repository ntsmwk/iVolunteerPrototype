import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";
import { FuseConfigService } from "@fuse/services/config.service";
import { UserRole } from "app/main/content/_model/user";
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { Router } from "@angular/router";

const STANDARD_PRIMARY_COLOR = "#3e7ddb";
const STANDARD_SECONDARY_COLOR = "#fafafa";

@Component({
  selector: "create-tenant",
  templateUrl: "create-tenant.component.html",
  styleUrls: ["create-tenant.component.scss"],
})
export class CreateTenantComponent implements OnInit {
  globalInfo: GlobalInfo;
  tenant: Tenant;
  loaded: boolean;
  tenantSaved: boolean;
  layout = {
    navigation: "none",
    footer: "none",
    toolbar: "none",
  };

  constructor(
    private router: Router,
    private fuseConfig: FuseConfigService,
    private loginService: LoginService,
    private coreUserService: CoreUserService
  ) {}

  async ngOnInit() {
    this.loaded = false;
    this.tenantSaved = false;
    this.fuseConfig.setConfig({ layout: this.layout });

    this.globalInfo = <GlobalInfo>this.loginService.getGlobalInfo();

    if (this.globalInfo.userSubscriptions.length === 0) {
      this.tenant = new Tenant({
        name: this.globalInfo.userInfo.organizationName,
        primaryColor: STANDARD_PRIMARY_COLOR,
        secondaryColor: STANDARD_SECONDARY_COLOR,
      });
    }

    this.loaded = true;
  }

  async handleTenantSaved(tenant: Tenant) {
    await this.coreUserService
      .subscribeUserToTenant(
        this.globalInfo.userInfo.id,
        this.globalInfo.currentMarketplaces[0].id,
        tenant.id,
        UserRole.TENANT_ADMIN
      )
      .toPromise();
    await this.loginService.generateGlobalInfo(UserRole.TENANT_ADMIN, [
      tenant.id,
    ]);
    this.loginService.getGlobalInfo();
    this.tenantSaved = true;
  }

  handleLogoutClick() {
    this.loginService.logout();
    this.router.navigate([`/`]);
  }

  handleFinishedClick() {
    this.fuseConfig.setConfig(this.fuseConfig.defaultConfig);
    this.router.navigate(["/role"]);
  }
}
