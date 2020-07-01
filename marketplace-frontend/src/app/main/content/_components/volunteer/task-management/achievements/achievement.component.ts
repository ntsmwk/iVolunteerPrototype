import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  ClassInstanceDTO,
  ClassInstance,
} from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { timer } from "rxjs";
import { MatTabChangeEvent } from "@angular/material";
import { isNullOrUndefined } from "util";
import { LocalRepositoryService } from "app/main/content/_service/local-repository.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { GlobalService } from "app/main/content/_service/global.service";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "fuse-achievements",
  templateUrl: "./achievement.component.html",
  styleUrls: ["./achievement.component.scss"],
  animations: fuseAnimations,
})
export class AchievementsComponent implements OnInit {
  volunteer: User;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[] = [];
  filteredClassInstanceDTOs: ClassInstanceDTO[] = [];

  selectedTenants: Tenant[] = [];
  subscribedTenants: Tenant[] = [];

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

  percentageFilteredOut: number = 0;

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private localRepositoryService: LocalRepositoryService,
    private globalService: GlobalService
  ) {}

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    let globalInfo = <GlobalInfo>(
      await this.globalService.getGlobalInfo().toPromise()
    );

    this.volunteer = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.subscribedTenants = globalInfo.tenants;

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(
      this.volunteer
    );

    if (this.isLocalRepositoryConnected) {
      let localClassInstances = <ClassInstance[]>(
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );

      // TODO Philipp
      // get unique marketplaceIds of CIs
      // perform once per marketplaceId
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .mapClassInstancesToDTOs(this.marketplace, localClassInstances)
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
              this.subscribedTenants.map((t) => t.id)
            )
            .toPromise()
        );
      }
    }

    // filter out classInstances missing the reqired fields
    let before = this.classInstanceDTOs.length;
    this.classInstanceDTOs = this.classInstanceDTOs.filter((ci) => {
      return (
        ci.name != null &&
        ci.tenantId != null &&
        ci.dateFrom &&
        ci.taskType1 &&
        ci.duration &&
        !isNaN(Number(ci.duration))
      );
    });
    let after = this.classInstanceDTOs.length;
    this.percentageFilteredOut = (1 - after / before) * 100;

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
