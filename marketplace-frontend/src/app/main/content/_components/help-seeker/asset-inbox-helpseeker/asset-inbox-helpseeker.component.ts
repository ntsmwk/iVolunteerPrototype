import { Component, OnInit } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { ActivatedRoute, Router } from "@angular/router";
import { Marketplace } from "../../../_model/marketplace";
import { Participant } from "../../../_model/participant";
import { ClassInstanceDTO } from "../../../_model/meta/Class";
import { ClassInstanceService } from "../../../_service/meta/core/class/class-instance.service";
import { isNullOrUndefined } from "util";
import { CoreMarketplaceService } from "../../../_service/core-marketplace.service";
import { LoginService } from "../../../_service/login.service";
import { Helpseeker } from "../../../_model/helpseeker";
import { CoreHelpSeekerService } from "../../../_service/core-helpseeker.service";
import { ArrayService } from "../../../_service/array.service";

@Component({
  selector: "asset-inbox-helpseeker",
  templateUrl: "./asset-inbox-helpseeker.component.html",
  styleUrls: ["./asset-inbox-helpseeker.component.scss"]
})
export class AssetInboxHelpseekerComponent implements OnInit {
  public marketplaces = new Array<Marketplace>();
  marketplace: Marketplace;
  helpseeker: Helpseeker;
  classInstanceDTO: ClassInstanceDTO[];
  isLoaded: boolean;

  constructor(
    private arrayService: ArrayService,
    private loginService: LoginService,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
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
        .then((helpseeker: Helpseeker) => {
          this.helpseeker = helpseeker;
        })
    ]).then(() => {
      this.loadInboxEntries();
    });
  }

  loadInboxEntries() {
    this.classInstanceService
      .getClassInstancesInIssuerInbox(
        this.marketplace,
        this.helpseeker.id,
        this.helpseeker.tenantId
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
        this.classInstanceDTO.map(c => c.id),
        false
      )
      .toPromise()
      .then(() => {
        console.log("confirm");
        this.router.navigate(["main/helpseeker/asset-inbox/confirm"], {
          state: {
            instances: this.classInstanceDTO,
            marketplace: this.marketplace,
            participant: this.helpseeker
          }
        });
      });
  }
}
