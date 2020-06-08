import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { Participant } from "app/main/content/_model/participant";
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
  participant: Participant;
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
      this.participant = this.router.getCurrentNavigation().extras.state.participant;
      this.classInstanceDTOs = this.router.getCurrentNavigation().extras.state.classInstances;
    }
  }

  ngOnInit() {
    console.log(this.marketplace);
    console.log(this.participant);

    if (
      isNullOrUndefined(this.marketplace) ||
      isNullOrUndefined(this.participant)
    ) {
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
          .then((participant: Participant) => {
            this.participant = participant;
          }),
      ]).then(() => {});
    }
  }

  onBackClickInbox() {
    this.router.navigate(["main/volunteer/asset-inbox"], {
      state: {
        instances: undefined,
        marketplace: this.marketplace,
        participant: this.participant,
      },
    });
  }

  onBackClickDashboard() {
    this.router.navigate(["main/dashboard"]);
  }
}
