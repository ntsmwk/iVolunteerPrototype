import { Component, OnDestroy, OnInit } from "@angular/core";

import { Marketplace } from "../../content/_model/marketplace";
import { User, UserRole } from "../../content/_model/user";
import { LoginService } from "../../content/_service/login.service";

import { isNullOrUndefined } from "util";
import { SelectionModel } from "@angular/cdk/collections";
import { MessageService } from "../../content/_service/message.service";
import { ArrayService } from "../../content/_service/array.service";
import { Subscription } from "rxjs";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { CoreUserService } from "app/main/content/_service/core-user.service";

@Component({
  selector: "fuse-marketplace-selection",
  templateUrl: "./marketplace-selection.component.html",
  styleUrls: ["./marketplace-selection.component.scss"],
})
export class FuseMarketplaceSelectionComponent implements OnInit, OnDestroy {
  private user: User;
  private role: UserRole;
  public marketplaces: Marketplace[];

  private selection = new SelectionModel<Marketplace>(true, []);
  private marketplaceRegistrationSubscription: Subscription;

  constructor(
    private arrayService: ArrayService,
    private messageService: MessageService,
    private loginService: LoginService,
    private coreUserService: CoreUserService
  ) { }

  ngOnInit() {
    this.loadMarketplaces();
    this.marketplaceRegistrationSubscription = this.messageService.subscribe(
      "marketplaceRegistration",
      this.loadMarketplaces.bind(this)
    );
  }

  ngOnDestroy() {
    this.marketplaceRegistrationSubscription.unsubscribe();
  }

  async loadMarketplaces() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.role = globalInfo.userRole;
    this.user = globalInfo.user;

    if (this.isRoleVolunteer(this.role)) {
      this.marketplaces = <Marketplace[]>(
        await this.coreUserService
          .findRegisteredMarketplaces(this.user.id)
          .toPromise()
      );

      this.marketplaces.forEach((marketplace: Marketplace) => {
        const storedMarketplaces = <Marketplace[]>(
          JSON.parse(localStorage.getItem("marketplaces"))
        );
        if (this.arrayService.contains(storedMarketplaces, marketplace)) {
          this.selection.select(marketplace);
        }
      });
    }
  }

  isSelected(marketplace: Marketplace) {
    return this.selection.isSelected(marketplace);
  }

  isLoggedInAsVolunteer() {
    return this.isRoleVolunteer(this.role);
  }

  private isRoleVolunteer(role: UserRole) {
    return !isNullOrUndefined(role) && role === UserRole.VOLUNTEER;
  }

  onMarketplaceSelect(marketplace: Marketplace) {
    this.selection.toggle(marketplace);
    this.messageService.broadcast("marketplaceSelectionChanged", {});
    localStorage.setItem(
      "marketplaces",
      JSON.stringify(this.selection.selected)
    );
  }
}
