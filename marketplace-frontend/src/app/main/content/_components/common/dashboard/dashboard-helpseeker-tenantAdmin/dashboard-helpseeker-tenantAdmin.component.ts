import { OnInit, Component } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { User } from "../../../../_model/user";
import { LoginService } from "../../../../_service/login.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "dashboard-helpseeker-tenantAdmin",
  templateUrl: "./dashboard-helpseeker-tenantAdmin.component.html",
  styleUrls: ["dashboard-helpseeker-tenantAdmin.component.scss"],
  animations: fuseAnimations
})
export class DashboardHelpSeekerTenantAdminComponent implements OnInit {
  user: User;
  tenant: Tenant;
  image: any;
  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.loaded = false;
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.user = globalInfo.user;
    this.tenant = globalInfo.tenants[0];
    this.image = this.tenant.landingpageImagePath;
    this.loaded = true;
  }
}
