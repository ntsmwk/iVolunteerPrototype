import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { LoginService } from "../../../_service/login.service";
import { TenantService } from "../../../_service/core-tenant.service";
import { User } from "../../../_model/user";
import { Marketplace } from "../../../_model/marketplace";
import { Tenant } from "../../../_model/tenant";

@Component({
  selector: "organisation-filter",
  templateUrl: "organisation-filter.component.html"
})
export class OrganisationFilterComponent implements OnInit {
  volunteer: User;
  marketplace: Marketplace;
  tenants: Tenant[];
  selectedTenants: Tenant[] = [];

  @Output()
  tenantSelectionChanged = new EventEmitter();

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.volunteer = <User>await this.loginService.getLoggedIn().toPromise();
    this.tenants = <Tenant[]>(
      await this.tenantService.findByUserId(this.volunteer.id).toPromise()
    );
    this.selectedTenants = [...this.tenants];
    this.tenantSelectionChanged.emit(this.selectedTenants);
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
