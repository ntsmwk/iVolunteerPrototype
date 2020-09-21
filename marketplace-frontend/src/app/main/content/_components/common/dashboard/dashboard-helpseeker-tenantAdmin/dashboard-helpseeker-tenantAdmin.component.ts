import { OnInit, Component } from "@angular/core";
import { Subscription } from "rxjs";
import { fuseAnimations } from "@fuse/animations";
import { isNullOrUndefined } from "util";
import { Router } from "@angular/router";
import { User } from "../../../../_model/user";
import { LoginService } from "../../../../_service/login.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { DomSanitizer } from "@angular/platform-browser";
import { ImageService } from "app/main/content/_service/image.service";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "dashboard-helpseeker-tenantAdmin",
  templateUrl: "./dashboard-helpseeker-tenantAdmin.component.html",
  styleUrls: ["dashboard-helpseeker-tenantAdmin.component.scss"],
  animations: fuseAnimations,
})
export class DashboardHelpSeekerTenantAdminComponent implements OnInit {
  user: User;
  tenant: Tenant;
  image: any;
  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer,
    private imageService: ImageService
  ) { }

  async ngOnInit() {
    this.loaded = false;
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.user = globalInfo.user;
    this.tenant = globalInfo.tenants[0];
    this.image = this.getTitleImage();
    console.log(this.image);
    this.loaded = true;
  }

  getTitleImage() {
    return this.tenantService.getTenantLandingPageImage(this.tenant);
  }
}
