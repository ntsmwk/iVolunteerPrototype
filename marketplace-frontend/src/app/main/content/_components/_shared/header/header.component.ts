import { Component, OnInit, Input } from "@angular/core";
import { Tenant } from "../../../_model/tenant";
import { TenantService } from "../../../_service/core-tenant.service";

@Component({
  selector: "customizable-header",
  templateUrl: "header.component.html"
})
export class HeaderComponent implements OnInit {
  @Input() headerText: string;
  @Input() tenant: Tenant;
  @Input() displayNavigateBack: boolean;

  constructor(private tenantService: TenantService) {}

  async ngOnInit() {
    this.tenantService.initHeader(this.tenant);
  }

  navigateBack() {
    window.history.back();
  }
}
