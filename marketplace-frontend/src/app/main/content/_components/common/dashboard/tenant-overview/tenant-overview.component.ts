import { Component, OnInit } from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { ImageService } from "app/main/content/_service/image.service";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";
import { timeout, timeInterval } from "rxjs/operators";
import { GlobalService } from "app/main/content/_service/global.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { User } from "app/main/content/_model/user";

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
    private coreVolunteerService: CoreVolunteerService,
    private globalService: GlobalService
  ) {}

  async ngOnInit() {
    this.initialize();
  }

  async initialize() {
    let globalInfo = <GlobalInfo>(
      await this.globalService.getGlobalInfo().toPromise()
    );
    this.volunteer = globalInfo.user;
    this.tenants = <Tenant[]>await this.tenantService.findAll().toPromise();
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  isSubscribed(tenant: Tenant) {
    return (
      // TODO Philipp
      this.volunteer.subscribedTenants.findIndex(
        (t) => t.tenantId === tenant.id
      ) >= 0
    );
  }

  async unsubscribe(tenant: Tenant) {
    await this.coreVolunteerService
      .unsubscribeTenant(this.volunteer.id, tenant.marketplaceId, tenant.id)
      .toPromise();
    window.setTimeout(() => this.initialize(), 2000);
  }

  async subscribe(tenant: Tenant) {
    await this.coreVolunteerService
      .subscribeTenant(this.volunteer.id, tenant.marketplaceId, tenant.id)
      .toPromise();
    window.setTimeout(() => this.initialize(), 2000);
  }

  navigateBack() {
    window.history.back();
  }
}
