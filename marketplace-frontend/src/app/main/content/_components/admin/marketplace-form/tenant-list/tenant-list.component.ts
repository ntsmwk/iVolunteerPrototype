import { Component, OnInit, Input } from "@angular/core";
import { Router } from "@angular/router";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "tenant-list",
  templateUrl: "tenant-list.component.html",
  animations: fuseAnimations
})
export class FuseTenantListComponent implements OnInit {
  @Input() marketplaceId: string;
  dataSource = new MatTableDataSource<Marketplace>();
  displayedColumns = ["name", "primaryColor", "secondaryColor", "actions"];

  constructor(private router: Router, private tenantService: TenantService) {}

  async ngOnInit() {
    this.dataSource.data = <Marketplace[]>(
      await this.tenantService.findByMarketplace(this.marketplaceId).toPromise()
    );
  }

  addTenant() {
    this.router.navigate([`/main/edit-tenant`], {
      queryParams: { marketplaceId: this.marketplaceId }
    });
  }

  navigateToTenantForm(tenantId: string) {
    this.router.navigate([`/main/edit-tenant/${tenantId}`], {
      queryParams: { marketplaceId: this.marketplaceId }
    });
  }
}
