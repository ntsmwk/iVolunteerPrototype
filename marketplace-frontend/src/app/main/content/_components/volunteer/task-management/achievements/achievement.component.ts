import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/class';
import { Tenant } from 'app/main/content/_model/tenant';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { LocalRepositoryService } from 'app/main/content';
import { timer } from 'rxjs';
import { MatTabChangeEvent } from '@angular/material';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'fuse-achievements',
  templateUrl: './achievement.component.html',
  styleUrls: ['./achievement.component.scss'],
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
              'TASK',
              this.volunteer.id,
              this.volunteer.subscribedTenants
            )
            .toPromise()
        );
      }
    }

    // TODO: philipp filter out classInstances missing the reqired fields
    // TODO: check if data is valid!

    // console.error('before', this.classInstanceDTOs.length);
    // this.classInstanceDTOs = this.classInstanceDTOs.filter(ci => {
    //   return (ci.name && ci.dateFrom && ci.taskType1 && ci.taskType2 &&
    //     ci.taskType3 && ci.duration)
    // });

    this.classInstanceDTOs.forEach((ci, index, object) => {
      if (ci.duration === null) {
        object.splice(index, 1);
      }
    });


    let nameCnt:number = 0, dateFromCnt:number = 0, taskType1Cnt:number = 0, taskType2Cnt:number = 0, 
    taskType3Cnt:number = 0, durationCnt:number = 0, locationCnt:number = 0, rankCnt:number = 0;

    console.error(this.classInstanceDTOs);
    this.classInstanceDTOs.forEach(ci => {
      if(!ci.name) {
        nameCnt++;
      }
      if(!ci.dateFrom) {
        dateFromCnt++;
      }
      if(!ci.taskType1) {
        taskType1Cnt++;
      }
      if(!ci.taskType2) {
        taskType2Cnt++;
      }
      if(!ci.taskType3) {
        taskType3Cnt++;
      }
      if(!ci.duration) {
        durationCnt++;
      }
      if(!ci.location) {
        locationCnt++;
      }
      if(!ci.rank) {
        rankCnt++;
      }
      
    });

    console.error('ohne name', nameCnt);
    console.error('ohne dateFrom', dateFromCnt);
    console.error('ohne taskType1', taskType1Cnt);
    console.error('ohne taskType2', taskType2Cnt);
    console.error('ohne taskType3', taskType3Cnt);
    console.error('ohne duration', durationCnt);
    console.error('ohne location', locationCnt);
    console.error('ohne rank', rankCnt);

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
    if (tabChangeEvent.tab.textLabel === 'Tätigkeiten') {
      this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
    }
  }


}
