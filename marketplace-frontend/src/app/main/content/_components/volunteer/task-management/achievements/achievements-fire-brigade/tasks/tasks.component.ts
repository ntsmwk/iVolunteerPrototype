import { Component, OnInit, ViewChild } from '@angular/core';
import { fuseAnimations } from '../../../../../../../../../@fuse/animations';
import { LoginService } from '../../../../../../_service/login.service';
import { ClassInstanceService } from '../../../../../../_service/meta/core/class/class-instance.service';
import { Marketplace } from '../../../../../../_model/marketplace';
import { CoreMarketplaceService } from '../../../../../../_service/core-marketplace.service';
import { ClassInstanceDTO } from '../../../../../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { CoreVolunteerService } from '../../../../../../_service/core-volunteer.service';
import { Volunteer } from '../../../../../../_model/volunteer';
import { TenantService } from 'app/main/content/_service/core-tenant.service';

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
    private tenantService: TenantService
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

        this.tenantService.findByName(this.tenantName).toPromise().then((tenantId: string) => {
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
