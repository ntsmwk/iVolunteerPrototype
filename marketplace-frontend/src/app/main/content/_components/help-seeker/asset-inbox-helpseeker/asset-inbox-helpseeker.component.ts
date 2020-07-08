import { Component, OnInit } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { ActivatedRoute, Router } from "@angular/router";
import { Marketplace } from "../../../_model/marketplace";
import { User, UserRole } from "../../../_model/user";
import { ClassInstanceDTO } from "../../../_model/meta/class";
import { ClassInstanceService } from "../../../_service/meta/core/class/class-instance.service";
import { isNullOrUndefined } from "util";
import { MarketplaceService } from "../../../_service/core-marketplace.service";
import { LoginService } from "../../../_service/login.service";
import { CoreHelpSeekerService } from "../../../_service/core-helpseeker.service";
import { ArrayService } from "../../../_service/array.service";

@Component({
  selector: "asset-inbox-helpseeker",
  templateUrl: "./asset-inbox-helpseeker.component.html",
  styleUrls: ["./asset-inbox-helpseeker.component.scss"],
})
export class AssetInboxHelpseekerComponent implements OnInit {
  public marketplaces = new Array<Marketplace>();
  marketplace: Marketplace;
  helpseeker: User;
  classInstanceDTO: ClassInstanceDTO[];
  isLoaded: boolean;

  constructor(
    private arrayService: ArrayService,
    private loginService: LoginService,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: MarketplaceService,
    private helpSeekerService: CoreHelpSeekerService
  ) {}

  ngOnInit() {
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
        .then((helpseeker: User) => {
          this.helpseeker = helpseeker;
        }),
    ]).then(() => {
      this.loadInboxEntries();
    });
  }

  loadInboxEntries() {
    this.classInstanceService
      .getClassInstancesInIssuerInbox(
        this.marketplace,
        this.helpseeker.id,
        this.helpseeker.subscribedTenants.find(
          (t) => t.role === UserRole.HELP_SEEKER
        ).tenant.id
      )
      .toPromise()
      .then((ret: ClassInstanceDTO[]) => {
        this.classInstanceDTO = ret;
        this.isLoaded = true;
      });
  }

  close() {}

  onAssetInboxSubmit() {
    this.classInstanceService
      .setClassInstanceInIssuerInbox(
        this.marketplace,
        this.classInstanceDTO.map((c) => c.id),
        false
      )
      .toPromise()
      .then(() => {
        console.log("confirm");
        this.router.navigate(["main/helpseeker/asset-inbox/confirm"], {
          state: {
            instances: this.classInstanceDTO,
            marketplace: this.marketplace,
            participant: this.helpseeker,
          },
        });
      });
  }
}
