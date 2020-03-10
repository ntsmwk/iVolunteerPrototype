import { Component, OnInit, Input } from "@angular/core";
import { Router } from "@angular/router";
import { CoreMarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material";
import { TenantService } from "app/main/content/_service/core-tenant.service";

@Component({
  selector: "tenant-list",
  templateUrl: "tenant-list.component.html"
})
export class FuseTenantListComponent implements OnInit {
  @Input() marketplaceId: string;
  dataSource = new MatTableDataSource<Marketplace>();
  displayedColumns = ["name", "shortName", "url", "actions"];

  constructor(private router: Router, private tenantService: TenantService) {}

  async ngOnInit() {
    this.dataSource.data = <Marketplace[]>(
      await this.tenantService.findByMarketplace(this.marketplaceId).toPromise()
    );
  }

  addTenant() {
    this.router.navigate(["/main/tenant-form"]);
  }
}
