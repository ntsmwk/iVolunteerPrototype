import { Component, OnInit } from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { ImageService } from "app/main/content/_service/image.service";
import { LoginService } from "app/main/content/_service/login.service";
import { timeout, timeInterval } from "rxjs/operators";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { User, UserRole } from "app/main/content/_model/user";
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';

@Component({
  selector: "tenant-overview",
  templateUrl: "tenant-overview.component.html",
  styleUrls: ["tenant-overview.component.scss"],
})
export class TenantOverviewComponent implements OnInit {
  tenants: Tenant[] = [];
  volunteer: User;

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService,
    private imageService: ImageService,
    private coreUserService: CoreUserService,
  ) { }

  async ngOnInit() {
    this.initialize();
  }

  async initialize() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.volunteer = globalInfo.user;
    this.tenants = <Tenant[]>await this.tenantService.findAll().toPromise();
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  isSubscribed(tenant: Tenant) {
    return (
      this.volunteer.subscribedTenants.findIndex(
        (t) => t.tenantId === tenant.id
      ) >= 0
    );
  }

  async unsubscribe(tenant: Tenant) {
    await this.coreUserService
      .unsubscribeUserFromTenant(this.volunteer.id, tenant.marketplaceId, tenant.id, UserRole.VOLUNTEER)
      .toPromise();
    window.setTimeout(() => this.initialize(), 2000);
  }

  async subscribe(tenant: Tenant) {
    await this.coreUserService
      .subscribeUserToTenant(this.volunteer.id, tenant.marketplaceId, tenant.id, UserRole.VOLUNTEER)
      .toPromise();
    window.setTimeout(() => this.initialize(), 2000);
  }

  navigateBack() {
    window.history.back();
  }
}
