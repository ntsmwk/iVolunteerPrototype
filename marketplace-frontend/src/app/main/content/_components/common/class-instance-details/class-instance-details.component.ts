import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { ClassInstanceDTO, ClassInstance, ClassDefinition } from 'app/main/content/_model/meta/class';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant, ParticipantRole } from 'app/main/content/_model/participant';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatTableDataSource, Sort, MatSort } from '@angular/material';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { PropertyInstance } from 'app/main/content/_model/meta/property';
import * as moment from 'moment';


@Component({
  selector: 'app-class-instance-details',
  templateUrl: './class-instance-details.component.html',
  styleUrls: ['./class-instance-details.component.scss']
})
export class ClassInstanceDetailsComponent implements OnInit {

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  id: string;
  tenantId: string;
  classInstance: ClassInstance;
  participant: Participant;
  role: ParticipantRole;
  marketplace: Marketplace;

  tableDataSource = new MatTableDataSource<PropertyInstance<any>>();
  displayedColumns = ['name', 'type', 'values'];


  constructor(
    private route: ActivatedRoute,
    private classInstanceService: ClassInstanceService,
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private helpseekerService: CoreHelpSeekerService,
    private classDefinitionService: ClassDefinitionService,


  ) {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.tenantId = params.tenantId;
    });
  }

  async ngOnInit() {
    //this.classInstance = <ClassInstanceDTO>history.state.data.classInstance;

    this.participant = <Participant>await this.loginService.getLoggedIn().toPromise();

    this.role = <ParticipantRole>await this.loginService.getLoggedInParticipantRole().toPromise();

    let marketplaces = [];
    if (this.role === 'HELP_SEEKER') {
      marketplaces = <Marketplace[]>(
        await this.helpseekerService.findRegisteredMarketplaces(this.participant.id).toPromise()
      );
    } else if (this.role === 'VOLUNTEER') {
      marketplaces = <Marketplace[]>(
        await this.volunteerService.findRegisteredMarketplaces(this.participant.id).toPromise()
      );
    }
    this.marketplace = marketplaces[0];

    this.classInstance = <ClassInstance>(
      await this.classInstanceService.getClassInstanceById(this.marketplace, this.id, this.tenantId).toPromise()
    );

    this.tableDataSource.data = this.classInstance.properties;
  }

  getName() {
    return this.classInstance.properties.find(p => p.name === 'name').values[0];
  }

  navigateBack() {
    window.history.back();
  }

  sortData(sort: Sort) {
    this.tableDataSource.data = this.tableDataSource.data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'type': return this.compare(a.type, b.type, isAsc);
        default: return 0;
      }
    });
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    if(typeof(a) === 'string' && typeof(b) === 'string') {
      return (a.toLowerCase() < b.toLowerCase() ? -1 : 1) * (isAsc ? 1 : -1);
    } else {
      return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }
  }

}

