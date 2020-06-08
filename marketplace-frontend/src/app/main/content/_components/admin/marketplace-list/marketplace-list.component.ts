import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { fuseAnimations } from "@fuse/animations";
import { Router } from "@angular/router";
import { MarketplaceService } from "../../../_service/core-marketplace.service";
import { Marketplace } from "../../../_model/marketplace";

@Component({
  templateUrl: "./marketplace-list.component.html",
  styleUrls: ["./marketplace-list.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations,
})
export class FuseMarketplaceListComponent implements OnInit {
  dataSource = new MatTableDataSource<Marketplace>();
  displayedColumns = ["name", "shortName", "url", "actions"];

  constructor(
    private router: Router,
    private marketplaceService: MarketplaceService
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
