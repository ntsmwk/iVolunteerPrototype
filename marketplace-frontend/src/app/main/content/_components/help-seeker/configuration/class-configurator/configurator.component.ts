import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { Relationship } from "app/main/content/_model/meta/relationship";
import { LoginService } from "app/main/content/_service/login.service";
import { User } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "app-configurator",
  templateUrl: "./configurator.component.html",
  styleUrls: ["./configurator.component.scss"]
})
export class ConfiguratorComponent implements OnInit {
  marketplace: Marketplace;
  configurableClasses: ClassDefinition[];
  relationships: Relationship[];
  tenantAdmin: User;
  isLoaded = false;

  constructor(private loginService: LoginService) {}

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.tenantAdmin = globalInfo.user;

    this.isLoaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
