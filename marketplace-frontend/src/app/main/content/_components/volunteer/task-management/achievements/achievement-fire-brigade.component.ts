import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../@fuse/animations";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { Volunteer } from 'app/main/content/_model/volunteer';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/Class';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';

@Component({
  selector: "fuse-achievements-fire-brigade",
  templateUrl: "./achievement-fire-brigade.component.html",
  styleUrls: ["./achievement-fire-brigade.component.scss"],
  animations: fuseAnimations
})
export class AchievementsFireBrigadeComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[];
  filteredClassInstanceDTOs: ClassInstanceDTO[];

  subscribedTenants: string[];

  tenantMap: Map<String, Tenant>;
  selectedTenants: String[];

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private tenantService: TenantService
  ) { }


  ngAfterViewInit() {
  }

  async ngOnInit () {
    this.filteredClassInstanceDTOs = [];
    
    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.tenantMap = new Map<String, Tenant>();
    this.selectedTenants = [];
    for (let tenantId of this.volunteer.subscribedTenants) {
      let tenant = <Tenant>await this.tenantService.findById(tenantId).toPromise();
      this.tenantMap.set(tenantId, tenant);

      this.selectedTenants.push(tenantId);
    }

    let marketplaces = <Marketplace[]>(
      await this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
    );

    // TODO for each registert mp
    this.marketplace = marketplaces[0];

    this.classInstanceDTOs = <ClassInstanceDTO[]>(
      await this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK', this.volunteer.id, this.volunteer.subscribedTenants).toPromise()
    );

    this.classInstanceDTOs.forEach((ci, index, object) => {
      if (ci.duration === null) {
        object.splice(index, 1);
      }
    });

    this.filteredClassInstanceDTOs = this.classInstanceDTOs;

  }


  onOrganisationFilterChange(event) {
    if (event.checked) {
      this.selectedTenants.push(event.source.value);
    } else {
      this.selectedTenants.splice(this.selectedTenants.indexOf(event.source.value), 1);
    }

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(ci => {
      return this.selectedTenants.indexOf(ci.tenantId) > -1;
    });

  }

}
