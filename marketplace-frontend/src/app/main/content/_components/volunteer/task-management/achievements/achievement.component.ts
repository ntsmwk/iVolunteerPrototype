import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../@fuse/animations";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { Volunteer } from "app/main/content/_model/volunteer";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO } from "app/main/content/_model/meta/Class";
import { Tenant } from "app/main/content/_model/tenant";
import { NgxSpinnerService } from "ngx-spinner";
import { isNullOrUndefined } from "util";
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: "fuse-achievements",
  templateUrl: "./achievement.component.html",
  styleUrls: ["./achievement.component.scss"],
  animations: fuseAnimations,
})
export class AchievementsComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[];
  filteredClassInstanceDTOs: ClassInstanceDTO[];

  selectedTenants: Tenant[];

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private spinner: NgxSpinnerService
  ) {}

  ngAfterViewInit() {}

  async ngOnInit() {
    //this.spinner.show();

    this.classInstanceDTOs = [];
    this.filteredClassInstanceDTOs = [];
    this.selectedTenants = [];

    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    let marketplaces = <Marketplace[]>(
      await this.volunteerService
        .findRegisteredMarketplaces(this.volunteer.id)
        .toPromise()
    );

    // TODO for each registert mp
    this.marketplace = marketplaces[0];

    if (!isNullOrUndefined(this.marketplace)) {
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .getUserClassInstancesByArcheType(
            this.marketplace,
            "TASK",
            this.volunteer.id,
            this.volunteer.subscribedTenants
          )
          .toPromise()
      );

      this.classInstanceDTOs.forEach((ci, index, object) => {
        if (ci.duration === null) {
          object.splice(index, 1);
        }
      });

      this.tenantSelectionChanged(this.selectedTenants);
    }
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((ci) => {
      return this.selectedTenants.findIndex((t) => t.id === ci.tenantId) >= 0;
    });
  }

  showSpinner() {
    this.spinner.show();
  }

  hideSpinner() {
    this.spinner.hide();
  }

  public tabChanged(tabChangeEvent: MatTabChangeEvent) {
    if(tabChangeEvent.tab.textLabel === 'Tätigkeiten') {
      this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
    }
}
}
