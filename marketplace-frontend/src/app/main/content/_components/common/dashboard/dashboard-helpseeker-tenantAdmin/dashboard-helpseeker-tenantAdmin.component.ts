import { OnInit, Component } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { LoginService } from "../../../../_service/login.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { UserInfo } from "app/main/content/_model/userInfo";

@Component({
  selector: "dashboard-helpseeker-tenantAdmin",
  templateUrl: "./dashboard-helpseeker-tenantAdmin.component.html",
  styleUrls: ["dashboard-helpseeker-tenantAdmin.component.scss"],
  animations: fuseAnimations,
})
export class DashboardHelpSeekerTenantAdminComponent implements OnInit {
  userInfo: UserInfo;
  tenant: Tenant;
  image: any;
  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.loaded = false;
    const globalInfo = this.loginService.getGlobalInfo();
    this.userInfo = globalInfo.userInfo;
    this.tenant = globalInfo.currentTenants[0];
    this.image = this.getTitleImage();
    this.loaded = true;
  }

  getTitleImage() {
    return this.tenantService.getTenantLandingPageImage(this.tenant);
  }
}
