import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Marketplace } from "../../../_model/marketplace";
import { User, UserRole } from "../../../_model/user";
import { ClassInstanceDTO } from "../../../_model/meta/class";
import { ClassInstanceService } from "../../../_service/meta/core/class/class-instance.service";
import { isNullOrUndefined } from "util";
import { MarketplaceService } from "../../../_service/core-marketplace.service";
import { LoginService } from "../../../_service/login.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "asset-inbox-helpseeker",
  templateUrl: "./asset-inbox-helpseeker.component.html",
  styleUrls: ["./asset-inbox-helpseeker.component.scss"],
})
export class AssetInboxHelpseekerComponent implements OnInit {
  public marketplaces = new Array<Marketplace>();
  marketplace: Marketplace;
  user: User;
  tenant: Tenant;
  classInstanceDTO: ClassInstanceDTO[];
  isLoaded: boolean;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: MarketplaceService
  ) {}

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.user = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.tenant = globalInfo.tenants[0];

    this.loadInboxEntries();
  }

  loadInboxEntries() {
    this.classInstanceService
      .getClassInstancesInIssuerInbox(
        this.marketplace,
        this.user.id,
        this.tenant.id
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
            participant: this.user,
          },
        });
      });
  }

  private isFF() {
    return this.tenant && this.tenant.name == "FF Eidenberg";
  }

  private isMV() {
    return this.tenant && this.tenant.name === "MV Schwertberg";
  }

  private isOther() {
    return !this.isFF() && !this.isMV();
  }
}
