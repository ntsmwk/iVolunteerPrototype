import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { User } from "app/main/content/_model/user";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { ActivatedRoute, Router } from "@angular/router";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { LoginService } from "app/main/content/_service/login.service";
import { isNullOrUndefined } from "util";

@Component({
  selector: "volunteer-inbox-confirmation-screen",
  templateUrl: "./confirmation-screen.component.html",
  styleUrls: ["./confirmation-screen.component.scss"],
})
export class VolunteerConfirmationScreenComponent implements OnInit {
  // dataSource = new MatTableDataSource<any>();
  // displayedColumns = ['data', 'issuer', 'label', 'details'];

  isLoaded: boolean;
  marketplace: Marketplace;
  user: User;
  classInstanceDTOs: ClassInstanceDTO[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: MarketplaceService,
    private loginService: LoginService
  ) {
    if (!isNullOrUndefined(this.router.getCurrentNavigation().extras.state)) {
      this.marketplace = this.router.getCurrentNavigation().extras.state.marketplace;
      this.user = this.router.getCurrentNavigation().extras.state.participant;
      this.classInstanceDTOs = this.router.getCurrentNavigation().extras.state.classInstances;
    }
  }

  ngOnInit() {
    console.log(this.marketplace);
    console.log(this.user);

    if (isNullOrUndefined(this.marketplace) || isNullOrUndefined(this.user)) {
      Promise.all([
        this.marketplaceService
          .findAll()
          .toPromise()
          .then((marketplaces: Marketplace[]) => {
            if (!isNullOrUndefined(marketplaces)) {
              this.marketplace = marketplaces[0];
            }
          }),
        this.loginService
          .getLoggedIn()
          .toPromise()
          .then((user: User) => {
            this.user = user;
          }),
      ]).then(() => {});
    }
  }

  onBackClickInbox() {
    this.router.navigate(["main/volunteer/asset-inbox"], {
      state: {
        instances: undefined,
        marketplace: this.marketplace,
        participant: this.user,
      },
    });
  }

  onBackClickDashboard() {
    this.router.navigate(["main/dashboard"]);
  }
}
