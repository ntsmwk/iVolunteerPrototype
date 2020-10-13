import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { User, UserRole } from 'app/main/content/_model/user';

@Component({
  selector: "tenant-form",
  templateUrl: 'tenant-form.component.html',
  styleUrls: ['./tenant-form.component.scss']
})
export class FuseTenantFormComponent implements OnInit {
  tenant: Tenant;
  marketplace: Marketplace;
  globalInfo: GlobalInfo;
  loaded: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tenantService: TenantService,
    private marketplaceService: MarketplaceService,
    private loginService: LoginService,
  ) { }

  async ngOnInit() {

    this.loaded = false;
    let tenantId: string;
    let marketplaceId: string;

    this.route.params.subscribe((params) => {
      tenantId = params['tenantId'];
    });
    this.route.queryParams.subscribe((params) => {
      marketplaceId = params['marketplaceId'];
    });

    Promise.all([
      this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise(),
      this.tenantService.findById(tenantId).toPromise().then((tenant: Tenant) => this.tenant = tenant),
      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => this.marketplace = marketplace),
    ]).then(() => {
      this.loaded = true;
    });


  }

  handleToDashboardClick() {
    this.router.navigate(['/main/dashboard']);
  }

  handleTenantSaved(tenant: Tenant) {

  }
}
