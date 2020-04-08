import { Component, OnInit } from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { ImageService } from "app/main/content/_service/image.service";
import { LoginService } from "app/main/content/_service/login.service";
import { Volunteer } from "app/main/content/_model/volunteer";

@Component({
  selector: "tenant-overview",
  templateUrl: "tenant-overview.component.html",
  styleUrls: ["tenant-overview.component.scss"],
})
export class TenantOverviewComponent implements OnInit {
  tenants: Tenant[] = [];
  volunteer: Volunteer;

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService,
    private imageService: ImageService
  ) {}

  async ngOnInit() {
    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.tenants = <Tenant[]>await this.tenantService.findAll().toPromise();
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  isSubscribed(tenant: Tenant) {
    return (
      this.volunteer.subscribedTenants.findIndex((t) => t === tenant.id) >= 0
    );
  }
}
