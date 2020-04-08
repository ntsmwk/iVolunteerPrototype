import { Component, OnInit } from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { ImageService } from "app/main/content/_service/image.service";

@Component({
  selector: "tenant-overview",
  templateUrl: "tenant-overview.component.html",
  styleUrls: ["tenant-overview.component.scss"],
})
export class TenantOverviewComponent implements OnInit {
  tenants: Tenant[] = [];

  constructor(
    private tenantService: TenantService,
    private imageService: ImageService
  ) {}

  async ngOnInit() {
    this.tenants = <Tenant[]>await this.tenantService.findAll().toPromise();
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }
}
