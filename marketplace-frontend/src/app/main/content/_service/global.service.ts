import { Injectable } from "@angular/core";
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

@Injectable({ providedIn: "root" })
export class ServiceNameService {
  globalInfo: GlobalInfo;

  constructor(
    private httpClient: HttpClient,
    private loginService: LoginService,
    private tenantService: TenantService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private coreVolunteerService: CoreVolunteerService,
    private marketplaceService: MarketplaceService
  ) {}

  async getGlobalInfo() {
    this.globalInfo.participant = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.globalInfo.participantRole = <ParticipantRole>(
      await this.loginService.getLoggedInParticipantRole().toPromise()
    );

    if (this.globalInfo.participantRole === "HELP_SEEKER") {
      this.globalInfo.marketplace = <Marketplace>(
        await this.coreHelpseekerService
          .findRegisteredMarketplaces(this.globalInfo.participant.id)
          .toPromise()
      );
    } else if (this.globalInfo.participantRole === "VOLUNTEER") {
      let marketplaces = [];
      marketplaces = <Marketplace[]>(
        await this.coreVolunteerService
          .findRegisteredMarketplaces(this.globalInfo.participant.id)
          .toPromise()
      );
      this.globalInfo..marketplace = marketplaces[0];
    }
  }
}
