import { Component, OnInit } from "@angular/core";
import { CoreVolunteerService } from "../../_service/core-volunteer.service";
import { CoreHelpSeekerService } from "../../_service/core-helpseeker.service";
import { LoginService } from "../../_service/login.service";
import { CoreMarketplaceService } from "../../_service/core-marketplace.service";
import { TenantService } from "../../_service/core-tenant.service";
import { Volunteer } from "../../_model/volunteer";
import { Participant } from "../../_model/participant";
import { Marketplace } from "../../_model/marketplace";

@Component({
  selector: "organisation-filter",
  templateUrl: "organisation-filter.component.html"
})
export class OrganisationFilterComponent implements OnInit {
  participant: Participant;
  marketplace: Marketplace;

  constructor(
    private coreVolunteerService: CoreVolunteerService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.participant = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
    // this.setVolunteerImage();

    this.marketplace = (<Marketplace[]>(
      await this.coreVolunteerService
        .findRegisteredMarketplaces(this.participant.id)
        .toPromise()
    )).filter(m => (m.name = "Marketplace 1"))[0];
  }
}
