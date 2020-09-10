import { Component, OnInit } from "@angular/core";
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { Tenant } from 'app/main/content/_model/tenant';


@Component({
  selector: "create-tenant",
  templateUrl: "create-tenant.component.html",
  styleUrls: ["create-tenant.component.scss"],
})
export class CreateTenantComponent implements OnInit {

  globalInfo: GlobalInfo;
  tenant: Tenant;
  loaded: boolean;

  constructor(
    private loginService: LoginService,
  ) { }



  ngOnInit() {
    this.loaded = false;
    this.loginService.getGlobalInfo().toPromise().then((globalInfo: GlobalInfo) => {
      this.globalInfo = globalInfo;
      this.tenant = new Tenant({ name: this.globalInfo.user.organizationName, primaryColor: '', secondaryColor: '' });
      this.loaded = true;
    });
  }

}
