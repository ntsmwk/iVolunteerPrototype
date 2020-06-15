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

  getGlobalInfo() {
    // TODO make only once
    return this.httpClient.get(`/core/global`);
    // setTimeout(() => {
    //   while (this.globalInfo == null) {
    //     console.error("globalinfo == null");
    //     this.initializeGlobalInfo();

    //     console.error("await");
    //     console.error(this.globalInfo);
    //   }
    // }, 2000);
    // return new Promise(() => this.globalInfo);
  }

  private initializeGlobalInfo() {
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
