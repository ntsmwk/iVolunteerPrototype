import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Marketplace } from "app/main/content/_model/marketplace";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { LoginService } from "app/main/content/_service/login.service";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { PropertyDefinition } from "app/main/content/_model/meta/property";
import { isNullOrUndefined } from "util";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";

@Component({
  selector: "app-property-builder",
  templateUrl: "./property-builder.component.html",
  styleUrls: ["./property-builder.component.scss"],
})
export class PropertyBuildFormComponent implements OnInit {
  marketplace: Marketplace;
  helpseeker: Helpseeker;
  loaded: boolean;

  displayBuilder: boolean;
  displayResultSuccess: boolean;
  tenant: Tenant;

  constructor(
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.displayBuilder = true;
    this.displayResultSuccess = false;

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
