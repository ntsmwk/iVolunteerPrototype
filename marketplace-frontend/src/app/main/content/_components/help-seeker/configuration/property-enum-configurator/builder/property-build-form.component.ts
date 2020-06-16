import { Component, OnInit } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';
import { Tenant } from 'app/main/content/_model/tenant';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Router, Route, ActivatedRoute } from '@angular/router';

@Component({
  selector: "app-property-build-form",
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss'],
})
export class PropertyBuildFormComponent implements OnInit {
  marketplace: Marketplace;
  helpseeker: Helpseeker;
  loaded: boolean;

  displayBuilder: boolean;
  displayResultSuccess: boolean;
  builderType: string;

  tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService,
    private tenantService: TenantService
  ) { }

  async ngOnInit() {
    this.displayBuilder = true;
    this.displayResultSuccess = false;

    console.log(this.route);
    // const typeParam = this.route.queryParams.subscribe()
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (isNullOrUndefined(params['type'] || params['type'] === 'property')) {
        this.builderType = 'property';
      } else {
        this.builderType = params['type'];
      }
    });

    this.helpseeker = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.tenant = <Tenant>(
      await this.tenantService.findById(this.helpseeker.tenantId).toPromise()
    );

    this.marketplace = <Marketplace>(
      await this.helpseekerService
        .findRegisteredMarketplaces(this.helpseeker.id)
        .toPromise()
    );
    this.loaded = true;
  }

  handleResultEvent(result: PropertyDefinition<any>) {
    this.displayBuilder = false;

    if (!isNullOrUndefined(result)) {
      this.displayResultSuccess = true;
    } else {
      this.displayResultSuccess = false;
    }
  }

  handleAddAnotherClick() {
    this.displayResultSuccess = false;
    this.displayBuilder = true;
  }
}
