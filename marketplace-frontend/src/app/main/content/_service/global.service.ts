import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { GlobalInfo } from "../_model/global-info";
import { Participant, ParticipantRole } from "../_model/participant";
import { TenantService } from "./core-tenant.service";
import { Tenant } from "../_model/tenant";
import { Volunteer } from "../_model/volunteer";
import { MarketplaceService } from "./core-marketplace.service";
import { LoginService } from "./login.service";

@Injectable({ providedIn: "root" })
export class ServiceNameService {
  globalInfo: GlobalInfo;

  constructor(
    private httpClient: HttpClient,
    private loginService: LoginService,
    private tenantService: TenantService,
    private marketplaceService: MarketplaceService
  ) {}

  async getGlobalInfo() {
    this.globalInfo.participant = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.globalInfo.participantRole = <ParticipantRole>(
      await this.loginService.getLoggedInParticipantRole().toPromise()
    );
  }
}
