import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";

import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { PropertyDefinition } from "app/main/content/_model/meta/property";
import { isNullOrUndefined } from "util";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Router, Route, ActivatedRoute } from "@angular/router";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "app-property-build-form",
  templateUrl: "./property-build-form.component.html",
  styleUrls: ["./property-build-form.component.scss"],
})
export class PropertyBuildFormComponent implements OnInit {
  marketplaceId: string;
  marketplace: Marketplace;

  entryId: string;

  helpseeker: User;
  loaded: boolean;

  displayBuilder: boolean;
  builderType: string;

  // tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService,
    private marketplaceService: MarketplaceService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.displayBuilder = true;

    await Promise.all([
      this.route.queryParams.subscribe((params) => {
        if (
          isNullOrUndefined(params["type"] || params["type"] === "property")
        ) {
          this.builderType = "property";
        } else {
          this.builderType = params["type"];
        }
      }),
      this.route.params.subscribe((params) => {
        this.marketplaceId = params["marketplaceId"];
        this.entryId = params["entryId"];
      }),
    ]);

    this.helpseeker = <User>await this.loginService.getLoggedIn().toPromise();
    // console.log(this.helpseeker);

    this.marketplace = <
      Marketplace // await this.helpseekerService
    >//   .findRegisteredMarketplaces(this.helpseeker.id)
    //   .toPromise()
    await this.marketplaceService.findById(this.marketplaceId).toPromise();

    this.loaded = true;
  }

  handleResultEvent(result: PropertyDefinition<any>) {
    this.displayBuilder = false;
    window.history.back();
  }

  handleManagementEvent(event: string, dom: HTMLElement) {
    if (event === "disableScroll") {
      dom.style.overflow = "hidden";
    } else if (event === "enableScroll") {
      dom.style.overflow = "scroll";
    }
  }
}
