import { Component, OnInit, ViewChild } from '@angular/core';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstanceDTO, ClassArchetype } from 'app/main/content/_model/meta/Class';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { LocalRepositoryService } from 'app/main/content';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Participant } from 'app/main/content/_model/participant';
import { isNullOrUndefined } from "util";
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { CoreUserImagePathService } from 'app/main/content/_service/core-user-imagepath.service';
import { Tenant } from 'app/main/content/_model/tenant';

@Component({
  selector: 'app-local-repository',
  templateUrl: './local-repository.component.html',
  styleUrls: ['./local-repository.component.scss']
})
export class LocalRepositoryComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;
  classInstanceDTOs: ClassInstanceDTO[] = [];
  filteredClassInstanceDTOs: ClassInstanceDTO[] = [];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  dataSourceRepository = new MatTableDataSource<ClassInstanceDTO>();
  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
    "syncButton"
  ];
  issuerIds: string[] = [];
  issuers: Participant[] = [];
  userImagePaths: any[];
  image;

  tasksLocalRepository: ClassInstanceDTO[] = [];
  selectedTenants: Tenant[] = [];

  constructor(
    private loginService: LoginService,
    private volunteerService: CoreVolunteerService,
    private classInstanceService: ClassInstanceService,
    private localRepositoryService: LocalRepositoryService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private userImagePathService: CoreUserImagePathService,
    private coreHelpseekerService: CoreHelpSeekerService,

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

    this.classInstanceDTOs = this.classInstanceDTOs.sort(
      (a, b) => b.dateFrom.valueOf() - a.dateFrom.valueOf()
    );

    this.filteredClassInstanceDTOs = this.classInstanceDTOs;

    this.dataSourceRepository.data = this.filteredClassInstanceDTOs;
    this.paginator.length = this.filteredClassInstanceDTOs.length;
    this.dataSourceRepository.paginator = this.paginator;

    this.issuerIds.push(...this.filteredClassInstanceDTOs.map(t => t.issuerId));

    this.issuerIds = this.issuerIds.filter((elem, index, self) => {
      return index === self.indexOf(elem);
    });
    this.userImagePaths = <any[]>(
      await this.userImagePathService
        .getImagePathsById(this.issuerIds)
        .toPromise()
    );
    this.issuers = <any[]>(
      await this.coreHelpseekerService.findByIds(this.issuerIds).toPromise()
    );

    this.tasksLocalRepository = <ClassInstanceDTO[]>(
      await this.localRepositoryService.findByVolunteerAndArcheType(this.volunteer, ClassArchetype.TASK).toPromise());
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

  async syncToLocalRepository(classInstance: ClassInstanceDTO) {
    await this.localRepositoryService.synchronizeClassInstance(this.volunteer, classInstance).toPromise();
    this.tasksLocalRepository.push(classInstance);
  }

  async removeFromLocalRepository(classInstance: ClassInstanceDTO) {
    await this.localRepositoryService.removeClassInstance(this.volunteer, classInstance).toPromise();

    this.tasksLocalRepository.forEach((ci, index, object) => {
      if (ci.id === classInstance.id) {
        object.splice(index, 1);
      }
    });
  }

  inLocalRepository(classInstance: ClassInstanceDTO) {
    return this.tasksLocalRepository.findIndex(t => t.id === classInstance.id) >= 0;
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(ci => {
      return this.selectedTenants.findIndex(t => t.id === ci.tenantId) >= 0;
    });

    this.dataSourceRepository.data = this.filteredClassInstanceDTOs;
    this.paginator.length = this.filteredClassInstanceDTOs.length;
    this.dataSourceRepository.paginator = this.paginator;
  }




}
