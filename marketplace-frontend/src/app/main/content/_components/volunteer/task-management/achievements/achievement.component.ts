import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../@fuse/animations";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { Volunteer } from "app/main/content/_model/volunteer";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO } from "app/main/content/_model/meta/Class";
import { Tenant } from "app/main/content/_model/tenant";
import { isNullOrUndefined } from "util";
import { MatTabChangeEvent } from "@angular/material";
import { LocalRepositoryService } from "app/main/content";
import { timer } from "rxjs";

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
  subscribedTenants: string[] = [];

  isLocalRepositoryConnected: boolean;

  timeout: boolean = false;

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private localRepositoryService: LocalRepositoryService
  ) { }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.subscribedTenants = this.volunteer.subscribedTenants;

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(
      this.volunteer
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
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );
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
      }
    }

    // TODO: philipp filter out classInstances missing the reqired fields
    // current assumption: if existent the format is valid
    // console.error('before', this.classInstanceDTOs.length);
    this.classInstanceDTOs.filter(ci => {
      return (ci.name && ci.dateFrom && ci.taskType1 && ci.taskType2 &&
        ci.taskType3 && ci.duration && ci.location && ci.rank)
    });
    // console.error('after', this.classInstanceDTOs.length);

    this.tenantSelectionChanged(this.selectedTenants);
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((ci) => {
      return this.selectedTenants.findIndex((t) => t.id === ci.tenantId) >= 0;
    });
  }

  public tabChanged(tabChangeEvent: MatTabChangeEvent) {
    if (tabChangeEvent.tab.textLabel === "Tätigkeiten") {
      this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
    }
  }


}
