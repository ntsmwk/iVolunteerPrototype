import { Component, OnDestroy, OnInit } from "@angular/core";

import { Marketplace } from "../../content/_model/marketplace";
import { User, UserRole } from "../../content/_model/user";
import { CoreVolunteerService } from "../../content/_service/core-volunteer.service";
import { LoginService } from "../../content/_service/login.service";

import { isNullOrUndefined } from "util";
import { SelectionModel } from "@angular/cdk/collections";
import { MessageService } from "../../content/_service/message.service";
import { ArrayService } from "../../content/_service/array.service";
import { Subscription } from "rxjs";
import { GlobalInfo } from "app/main/content/_model/global-info";

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
    private volunterService: CoreVolunteerService
  ) {}

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

  loadMarketplaces() {
    Promise.all([
      this.loginService.getLoggedIn().toPromise(),
      this.loginService.getLoggedInUserRole().toPromise(),
    ])
      .then((values: any[]) => {
        this.role = <UserRole>values[1];
        if (this.isRoleVolunteer(this.role)) {
          this.volunterService
            .findRegisteredMarketplaces((<User>values[0]).id)
            .toPromise()
            .then((marketplaces: Marketplace[]) => {
              this.marketplaces = marketplaces;
              this.marketplaces.forEach((marketplace: Marketplace) => {
                const storedMarketplaces = <Marketplace[]>(
                  JSON.parse(localStorage.getItem("marketplaces"))
                );
                if (
                  this.arrayService.contains(storedMarketplaces, marketplace)
                ) {
                  this.selection.select(marketplace);
                }
              });
            });
        }
      })
      .catch((e) => {
        console.warn(e);
      });
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
