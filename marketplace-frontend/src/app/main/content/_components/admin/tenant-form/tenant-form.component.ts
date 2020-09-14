import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { Marketplace } from 'app/main/content/_model/marketplace';

@Component({
  selector: "tenant-form",
  templateUrl: "tenant-form.component.html",
  styleUrls: ["./tenant-form.component.scss"]
})
export class FuseTenantFormComponent implements OnInit {
  tenant: Tenant;
  marketplace: Marketplace;

  constructor(
    private route: ActivatedRoute,
    private tenantService: TenantService,
    private marketplaceService: MarketplaceService,
  ) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.tenantService.findById(params['tenantId']).toPromise().then((tenant: Tenant) => this.tenant = tenant);
    });
    this.route.queryParams.subscribe((params) => {
      this.marketplaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => this.marketplace = marketplace);
    });
  }
}
