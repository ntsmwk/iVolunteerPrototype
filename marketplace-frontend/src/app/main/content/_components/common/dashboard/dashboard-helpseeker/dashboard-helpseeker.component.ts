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

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((participant: Helpseeker) => {
        this.participant = participant;
        console.error("participant loaded");
        console.error("drin");
        this.tenantService
          .findById((<Helpseeker>this.participant).tenantId)
          .toPromise()
          .then(tenant => {
            this.tenant = tenant;
            console.error(tenant);
            console.error(this.tenant.image);
            let objectURL = "data:image/png;base64," + this.tenant.image;
            this.tenantImage = this.sanitizer.bypassSecurityTrustUrl(objectURL);
          });
      });
  }

  private isFF() {
    return this.participant.username == "FFA";
  }

  private isMV() {
    return this.participant.username === "MVS";
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }
}
