import { Injectable, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { GlobalInfo } from "../_model/global-info";
import { Participant, ParticipantRole } from "../_model/participant";
import { TenantService } from "./core-tenant.service";
import { Tenant } from "../_model/tenant";
import { Volunteer } from "../_model/volunteer";
import { MarketplaceService } from "./core-marketplace.service";
import { LoginService } from "./login.service";
import { Marketplace } from "../_model/marketplace";
import { HelpseekerService } from "./helpseeker.service";
import { VolunteerService } from "./volunteer.service";
import { CoreVolunteerService } from "./core-volunteer.service";
import { CoreHelpSeekerService } from "./core-helpseeker.service";
import { timeInterval } from "rxjs/operators";

@Injectable({ providedIn: "root" })
export class GlobalService {
  globalInfo: GlobalInfo;

  constructor(
    private httpClient: HttpClient,
    private loginService: LoginService,
    private tenantService: TenantService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private coreVolunteerService: CoreVolunteerService,
    private marketplaceService: MarketplaceService
  ) {}

  async getGlobalInfo(): Promise<GlobalInfo> {
    while (this.globalInfo == null) {
      setTimeout(async () => {
        console.error("globalinfo == null");
        await this.initializeGlobalInfo();

        console.error("await");
        console.error(this.globalInfo);
      }, 200);
    }
    return this.globalInfo;
  }

  private async initializeGlobalInfo() {
    console.error("initialize");
    this.globalInfo = new GlobalInfo();
    this.httpClient
      .get(`/core/global`)
      .toPromise()
      .then((gi: GlobalInfo) => (this.globalInfo = gi));
  }

  clearGlobalInfo() {
    this.globalInfo = undefined;
  }
}
