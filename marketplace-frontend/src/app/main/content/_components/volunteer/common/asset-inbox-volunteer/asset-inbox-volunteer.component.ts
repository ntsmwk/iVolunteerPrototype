import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Marketplace } from "../../../../_model/marketplace";
import { Participant } from "../../../../_model/participant";
import { ClassInstanceDTO } from "../../../../_model/meta/Class";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import { isNullOrUndefined } from "util";
import { CoreMarketplaceService } from "../../../../_service/core-marketplace.service";
import { LoginService } from "../../../../_service/login.service";
import { TenantService } from "../../../../_service/core-tenant.service";
import { Volunteer } from "../../../../_model/volunteer";

@Component({
  selector: "asset-inbox-volunteer",
  templateUrl: "./asset-inbox-volunteer.component.html",
  styleUrls: ["./asset-inbox-volunteer.component.scss"]
})
export class AssetInboxVolunteerComponent implements OnInit {
  isLoaded: boolean;
  marketplace: Marketplace;
  volunteer: Volunteer;
  classInstanceDTOs: ClassInstanceDTO[];

  submitPressed: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private loginService: LoginService,
    private coreTenantService: TenantService
  ) {
    if (!isNullOrUndefined(this.router.getCurrentNavigation().extras.state)) {
      this.marketplace = this.router.getCurrentNavigation().extras.state.marketplace;
      this.volunteer = this.router.getCurrentNavigation().extras.state.volunteer;
    }
  }

  ngOnInit() {
    if (
      !isNullOrUndefined(this.marketplace) &&
      !isNullOrUndefined(this.volunteer)
    ) {
      this.loadInboxEntries();
    } else {
      Promise.all([
        this.marketplaceService
          .findAll()
          .toPromise()
          .then((marketplaces: Marketplace[]) => {
            if (!isNullOrUndefined(marketplaces)) {
              this.marketplace = marketplaces[0];
            }
          }),
        this.loginService
          .getLoggedIn()
          .toPromise()
          .then((volunteer: Volunteer) => {
            this.volunteer = volunteer;
          })
      ]).then(() => {
        this.loadInboxEntries();
      });
    }
  }

  loadInboxEntries() {
    this.classInstanceService
      .getClassInstancesInUserInbox(
        this.marketplace,
        this.volunteer.id,
        this.volunteer.subscribedTenants
      )
      .toPromise()
      .then((ret: ClassInstanceDTO[]) => {
        this.classInstanceDTOs = ret;
        this.isLoaded = true;
      });
  }

  onAssetInboxSubmit(classInstanceDTOs: ClassInstanceDTO[]) {
    this.classInstanceService
      .setClassInstanceInUserRepository(
        this.marketplace,
        classInstanceDTOs.map(c => c.id),
        true
      )
      .toPromise()
      .then(() => {
        this.router.navigate(["/main/dashboard"]);
      });
  }
}
