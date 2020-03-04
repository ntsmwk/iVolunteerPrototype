import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '../../../../../../@fuse/animations';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/Class';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { CoreTenantService } from 'app/main/content/_service/core-tenant.service';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import * as Highcharts from 'highcharts';
import HC_sunburst from 'highcharts/modules/sunburst';
HC_sunburst(Highcharts);

@Component({
  selector: 'fuse-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss'],
  animations: fuseAnimations
})

export class TasksComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;

  classInstanceDTOs: ClassInstanceDTO[];
  selectedYaxis: string;
  selectedYear: string;
  selectedTaskType: string;
  timelineFilter: { from: Date, to: Date };

  tenantName: string = 'FF_Eidenberg';
  tenantId: string[] = [];

  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private volunteerService: CoreVolunteerService,
    private coreTenantService: CoreTenantService
  ) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {
      this.volunteer = volunteer;

      Promise.all([
        this.marketplaceService.findAll().toPromise(),
        this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      ]).then((values: any[]) => {

        // TODO: 
        this.marketplace = values[0][0];

        this.coreTenantService.findByName(this.tenantName).toPromise().then((tenantId: string) => {
          this.tenantId.push(tenantId);
          // this.tenantId.push(Object.assign({}, tenantId));

          this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK', this.volunteer.id, this.tenantId).toPromise().then((ret: ClassInstanceDTO[]) => {
            if (!isNullOrUndefined(ret)) {

              ret.forEach((ci, index, object) => {
                if (ci.duration === null) {
                  object.splice(index, 1);
                }
              });

              this.classInstanceDTOs = ret;
            }
          });
        });
      });
    });

  }



}
