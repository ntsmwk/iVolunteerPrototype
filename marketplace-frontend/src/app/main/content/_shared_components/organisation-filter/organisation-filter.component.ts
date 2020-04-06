import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { CoreVolunteerService } from "../../_service/core-volunteer.service";
import { CoreHelpSeekerService } from "../../_service/core-helpseeker.service";
import { LoginService } from "../../_service/login.service";
import { CoreMarketplaceService } from "../../_service/core-marketplace.service";
import { TenantService } from "../../_service/core-tenant.service";
import { Volunteer } from "../../_model/volunteer";
import { Participant } from "../../_model/participant";
import { Marketplace } from "../../_model/marketplace";
import { Tenant } from "../../_model/tenant";
import { ImageService } from "../../_service/image.service";

@Component({
  selector: "organisation-filter",
  templateUrl: "organisation-filter.component.html"
})
export class OrganisationFilterComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  tenants: Tenant[];
  selectedTenants: Tenant[] = [];

  @Output()
  tenantSelectionChanged = new EventEmitter();

  constructor(
    private coreVolunteerService: CoreVolunteerService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private tenantService: TenantService,
    private imageService: ImageService
  ) {}

  async ngOnInit() {
    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.tenants = <Tenant[]>(
      await this.tenantService.findByVolunteerId(this.volunteer.id).toPromise()
    );
    this.selectedTenants = [...this.tenants];
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  tenantClicked(tenant: Tenant) {
    let t = this.selectedTenants.find(t => t.id === tenant.id);
    let index = this.selectedTenants.findIndex(t => t.id === tenant.id);

    if (index >= 0) {
      this.selectedTenants.splice(index, 1);
    } else {
      this.selectedTenants.push(tenant);
    }
    this.tenantSelectionChanged.emit(this.selectedTenants);
  }

  tenantSelected(tenant: Tenant) {
    return this.selectedTenants.findIndex(t => t.id === tenant.id) >= 0;
  }
}
