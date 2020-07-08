import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { ActivatedRoute, Router } from "@angular/router";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { LoginService } from "app/main/content/_service/login.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "asset-inbox-volunteer",
  templateUrl: "./asset-inbox-volunteer.component.html",
  styleUrls: ["./asset-inbox-volunteer.component.scss"],
})
export class AssetInboxVolunteerComponent implements OnInit {
  isLoaded: boolean;
  marketplace: Marketplace;
  volunteer: User;
  classInstanceDTOs: ClassInstanceDTO[];

  submitPressed: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: MarketplaceService,
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
          .then((volunteer: User) => {
            this.volunteer = volunteer;
          }),
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
        this.volunteer.subscribedTenants.map((t) => t.tenant.id)
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
        classInstanceDTOs.map((c) => c.id),
        true
      )
      .toPromise()
      .then(() => {
        this.router.navigate(["/main/dashboard"]);
      });
  }
}
