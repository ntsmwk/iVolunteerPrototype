import { Component, OnInit, ViewChild } from '@angular/core';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/Class';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { VolunteerRepositoryService } from 'app/main/content';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Participant } from 'app/main/content/_model/participant';
import { isNullOrUndefined } from "util";
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';

@Component({
  selector: 'app-local-repository',
  templateUrl: './local-repository.component.html',
  styleUrls: ['./local-repository.component.scss']
})
export class LocalRepositoryComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  tableDataSource = new MatTableDataSource<ClassInstanceDTO>();
  displayedColumns: string[] = ['issuer', 'taskName', 'taskType1', 'date'];

  issuerIds: string[] = [];
  issuers: Participant[] = [];
  userImagePaths: any[];
  image;

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private volunteerRepositoryService: VolunteerRepositoryService,
    private coreHelpSeekerService: CoreHelpSeekerService
  ) { }

  async ngOnInit() {

    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    let marketplaces = <Marketplace[]>(
      await this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
    );

    this.marketplace = marketplaces[0];

    this.classInstanceDTOs = <ClassInstanceDTO[]>(
      await this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK', this.volunteer.id, this.volunteer.subscribedTenants).toPromise()
    );

    this.classInstanceDTOs.forEach((ci, index, object) => {
      if (ci.duration === null) {
        object.splice(index, 1);
      }
    });

    this.issuers = <any[]>(
      await this.coreHelpSeekerService.findByIds(this.issuerIds).toPromise()
    );

    this.tableDataSource.data = this.classInstanceDTOs;

  }


  getImagePathById(id: string) {
    const ret = this.userImagePaths.find(userImagePath => {
      return userImagePath.userId === id;
    });

    if (isNullOrUndefined(ret)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return ret.imagePath;
    }
  }

  getIssuerName(issuerId: string) {
    const person = this.issuers.find(i => i.id === issuerId);

    let result = "";

    if (isNullOrUndefined(person)) {
      return result;
    }

    result = person.firstname + " " + person.lastname;
    return result;
  }

  getIssuerPosition(issuerId: string) {
    const person = this.issuers.find(i => i.id === issuerId);
    if (isNullOrUndefined(person) || isNullOrUndefined(person.position)) {
      return "";
    } else {
      return "(" + person.position + ")";
    }
  }

}
