import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { Relationship } from "app/main/content/_model/meta/relationship";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "app-configurator",
  templateUrl: "./configurator.component.html",
  styleUrls: ["./configurator.component.scss"],
})
export class ConfiguratorComponent implements OnInit {
  marketplace: Marketplace;
  configurableClasses: ClassDefinition[];
  relationships: Relationship[];
  helpseeker: User;
  isLoaded = false;

  constructor(
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
  ) { }

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;

    this.isLoaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
