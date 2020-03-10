import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CoreMarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { Marketplace } from "app/main/content/_model/marketplace";

@Component({
  selector: "tenant-list",
  templateUrl: "name.component.html"
})
export class FuseTenantListComponent implements OnInit {
  dataSource = new MatTableDataSource<Marketplace>();
  displayedColumns = ["name", "shortName", "url", "actions"];

  constructor(
    private router: Router,
    private marketplaceService: CoreMarketplaceService
  ) {}

  ngOnInit() {
    this.marketplaceService
      .findAll()
      .toPromise()
      .then(
        (marketplaces: Marketplace[]) => (this.dataSource.data = marketplaces)
      );
  }

  addMarketplace() {
    this.router.navigate(["/main/marketplace-form"]);
  }
}
