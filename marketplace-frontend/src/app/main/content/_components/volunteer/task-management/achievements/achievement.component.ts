import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../@fuse/animations";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { Volunteer } from "app/main/content/_model/volunteer";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO, ClassArchetype } from "app/main/content/_model/meta/Class";
import { Tenant } from "app/main/content/_model/tenant";
import { NgxSpinnerService } from "ngx-spinner";
import { isNullOrUndefined } from "util";
import { MatTabChangeEvent } from '@angular/material';
import { LocalRepositoryService } from 'app/main/content';
import { timer } from 'rxjs';

@Component({
  selector: "fuse-achievements",
  templateUrl: "./achievement.component.html",
  styleUrls: ["./achievement.component.scss"],
  animations: fuseAnimations,
})
export class AchievementsComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[] = [];
  filteredClassInstanceDTOs: ClassInstanceDTO[] = [];

  selectedTenants: Tenant[] = [];
  isLocalRepositoryConnected: boolean;

  timeout: boolean = false;

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private spinner: NgxSpinnerService,
    private localRepositoryService: LocalRepositoryService,
  ) { }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
        this.timeout = true;
    });

    //this.spinner.show();
    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected();

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

    if (this.isLocalRepositoryConnected) {
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.localRepositoryService.findByVolunteerAndArcheType(this.volunteer).toPromise());

    } else {
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
      }
    }

    this.tenantSelectionChanged(this.selectedTenants);
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((ci) => {
      return this.selectedTenants.findIndex((t) => t.id === ci.tenantId) >= 0;
    });
  }

  public tabChanged(tabChangeEvent: MatTabChangeEvent) {
    if (tabChangeEvent.tab.textLabel === 'Tätigkeiten') {
      this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
    }
  }



  showSpinner() {
    this.spinner.show();
  }

  hideSpinner() {
    this.spinner.hide();
  }
}
