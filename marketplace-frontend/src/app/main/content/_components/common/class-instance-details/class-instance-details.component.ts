
import { Component, OnInit, ViewChild, Inject } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import {
  ClassInstanceDTO,
  ClassInstance,
  ClassDefinition,
} from "app/main/content/_model/meta/class";
import { LoginService } from "app/main/content/_service/login.service";
import {
  Participant,
  ParticipantRole,
} from "app/main/content/_model/participant";
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource, MatSort, Sort, MAT_DIALOG_DATA } from "@angular/material";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { PropertyInstance } from 'app/main/content/_model/meta/property';

@Component({
  selector: "app-class-instance-details",
  templateUrl: "./class-instance-details.component.html",
  styleUrls: ["./class-instance-details.component.scss"],
})
export class ClassInstanceDetailsComponent implements OnInit {
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  id: string = null;
  classInstance: ClassInstance;
  participant: Participant;
  role: ParticipantRole;
  marketplace: Marketplace;
  tenant: Tenant;

  isDialog: boolean = false;

  tableDataSource = new MatTableDataSource<PropertyInstance<any>>();
  displayedColumns = ['name', 'values', 'type'];

  constructor(
    private route: ActivatedRoute,
    private classInstanceService: ClassInstanceService,
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private helpseekerService: CoreHelpSeekerService,
    private classDefinitionService: ClassDefinitionService,
    private tenantService: TenantService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.route.params.subscribe((params) => {
      this.id = params.id;
    });
  }

  async ngOnInit() {
    if (!this.id) {
      this.id = this.data.id;
      this.isDialog = true;
    }

    this.participant = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.role = <ParticipantRole>(
      await this.loginService.getLoggedInParticipantRole().toPromise()
    );

    if (this.role === "HELP_SEEKER") {
      this.marketplace = <Marketplace>(
        await this.helpseekerService
          .findRegisteredMarketplaces(this.participant.id)
          .toPromise()
      );
    } else if (this.role === "VOLUNTEER") {
      let marketplaces = [];
      marketplaces = <Marketplace[]>(
        await this.volunteerService
          .findRegisteredMarketplaces(this.participant.id)
          .toPromise()
      );
      this.marketplace = marketplaces[0];
    }
    this.classInstance = <ClassInstance>(
      await this.classInstanceService
        .getClassInstanceById(this.marketplace, this.id)
        .toPromise()
    );

    this.tenant = <Tenant>(
      await this.tenantService.findById(this.classInstance.tenantId).toPromise()
    );

    this.tableDataSource.data = this.classInstance.properties;

    this.tenantService.initHeader(this.tenant);
  }

  getName() {
    return this.classInstance.properties.find((p) => p.name === "name")
      .values[0];
  }

  sortData(sort: Sort) {
    this.tableDataSource.data = this.tableDataSource.data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return this.compare(a.name, b.name, isAsc);
        default: return 0;
      }
    });
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    if (typeof (a) === 'string' && typeof (b) === 'string') {
      return (a.toLowerCase() < b.toLowerCase() ? -1 : 1) * (isAsc ? 1 : -1);
    } else {
      return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }
  }

}
