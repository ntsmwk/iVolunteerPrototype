import { OnInit, Component } from "@angular/core";
import { Subscription } from "rxjs";
import { fuseAnimations } from "@fuse/animations";
import { isNullOrUndefined } from "util";
import { Router } from "@angular/router";
import { Participant } from "../../../../_model/participant";
import { LoginService } from "../../../../_service/login.service";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: "dashboard-helpseeker",
  templateUrl: "./dashboard-helpseeker.component.html",
  styleUrls: ["dashboard-helpseeker.scss"],
  animations: fuseAnimations
})
export class DashboardHelpSeekerComponent implements OnInit {
  participant: Participant;
  tenant;
  tenantImage;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer
  ) {}

  async ngOnInit() {
    this.participant = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.tenant = <Tenant>(
      await this.tenantService
        .findById((<Helpseeker>this.participant).tenantId)
        .toPromise()
    );
    this.setTenantImage();
    this.setTenantHeaderColor();
  }

  private setTenantImage() {
    let objectURL = "data:image/png;base64," + this.tenant.image;
    this.tenantImage = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  private setTenantHeaderColor() {
    (<HTMLElement>(
      document.querySelector(".header")
    )).style.background = this.tenant.primaryColor;
  }

  private isFF() {
    return this.tenant && this.tenant.name == "FF_Eidenberg";
  }

  private isMV() {
    return this.tenant && this.tenant.name === "Musikverein_Schwertberg";
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }
}
