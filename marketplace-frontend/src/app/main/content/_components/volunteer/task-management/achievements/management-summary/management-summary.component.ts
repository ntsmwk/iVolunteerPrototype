import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../../../../_service/login.service';
import { ClassInstanceService } from '../../../../../_service/meta/core/class/class-instance.service';
import { CoreVolunteerService } from '../../../../../_service/core-volunteer.service';
import { StoredChartService } from '../../../../../_service/stored-chart.service';
import { Volunteer } from '../../../../../_model/volunteer';
import { ClassInstanceDTO, ClassArchetype } from '../../../../../_model/meta/Class';
import { StoredChart } from '../../../../../_model/stored-chart';
import { TenantService } from '../../../../../_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from "util";
import { LocalRepositoryService } from 'app/main/content';
import { timer } from 'rxjs';


@Component({
  selector: 'fuse-management-summary',
  templateUrl: './management-summary.component.html',
  styleUrls: ['./management-summary.component.scss']
})
export class ManagementSummaryComponent implements OnInit {

  // comparison chart
  comparisonXlabel = 'Jahr';
  comparisonYlabel = 'Anzahl Tätigkeiten';
  colorScheme = 'cool';

  schemeType = 'ordinal';
  showGridLines = true;
  animations = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Datum';
  yAxisLabel = 'Dauer [h]';
  noBarWhenZero = true;
  showLabels = true;
  autoScale = true;
  legend = false;
  legendPosition = 'below';
  tooltipDisabled = false;

  volunteer: Volunteer;
  marketplace: any = [];
  classInstanceDTOs: ClassInstanceDTO[] = [];

  uniqueYears: any[];
  tenantMap: Map<String, Tenant>;

  durationTotal: any[] = [];
  numberTotal: any[] = [];
  durationLastYear: any[] = [];
  numberLastYear: any[] = [];

  comparisonData: any[] = [];
  comparisonYear: number;
  lastYear: number;

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private volunteerService: CoreVolunteerService,
    private storedChartService: StoredChartService,
    private tenantService: TenantService,
    private localRepositoryService: LocalRepositoryService
  ) { }

  async ngOnInit() {
      let t = timer(3000);
      t.subscribe(() => {
          this.timeout = true;
      });

    this.comparisonYear = 2019;

    
    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(this.volunteer);

    this.tenantMap = new Map<String, Tenant>();
    for (let tenantId of this.volunteer.subscribedTenants) {
      let tenant = <Tenant>await this.tenantService.findById(tenantId).toPromise();
      this.tenantMap.set(tenantId, tenant);
    }

    let marketplaces = <Marketplace[]>(
      await this.volunteerService
        .findRegisteredMarketplaces(this.volunteer.id)
        .toPromise()
    );
    // TODO for each registert mp
    this.marketplace = marketplaces[0];

    if (this.isLocalRepositoryConnected) {
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.localRepositoryService.findByVolunteerAndArcheType(this.volunteer).toPromise());

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

        this.classInstanceDTOs.forEach((ci, index, object) => {
          if (ci.duration === null) {
            object.splice(index, 1);
          }
        });
      }
    }

    this.uniqueYears = [...new Set(this.classInstanceDTOs.map(item => new Date(item.dateFrom).getFullYear()))];
  
    this.generateComparisonChartData(this.comparisonYear);
    this.generateEngagementData();

  }

  generateComparisonChartData(comparisonYear) {
    this.tenantMap.forEach(tenant => {

      let yearData = this.classInstanceDTOs.filter(ci => {
        return (new Date(ci.dateFrom).getFullYear() === comparisonYear);
      }).filter(ci => {
        return (ci.tenantId === tenant.id);
      })

      this.comparisonData.push({ name: tenant.name, value: yearData.length });
    });


    let finalData: any[] = [];

    this.uniqueYears.forEach(curYear => {
      let data: any[] = [];
      this.tenantMap.forEach(tenant => {
        let currentData = this.classInstanceDTOs.filter(ci => {
          return new Date(ci.dateFrom).getFullYear() === curYear;
        }).filter(ci => {
          return ci.tenantId === tenant.id;
        })

        data.push({ name: tenant.name, value: currentData.length - this.comparisonData.find(d => d.name === tenant.name).value });
      });
      finalData.push({ name: curYear, series: data });
    });

    this.comparisonData = [...finalData];
  }

  generateEngagementData() {
    this.tenantMap.forEach(tenant => {
      let classInstancesTenant = this.classInstanceDTOs.filter(ci => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce((acc, curr) => acc + Number(curr.duration), 0)
      this.durationTotal.push({ name: tenant.name, value: duration });
      this.numberTotal.push({ name: tenant.name, value: classInstancesTenant.length });
    });


    this.lastYear = new Date().getFullYear() - 1;
    let classInstancesLastYear = this.classInstanceDTOs.filter(ci => {
      return new Date(ci.dateFrom).getFullYear() === this.lastYear;
    });

    this.tenantMap.forEach(tenant => {
      let classInstancesTenant = classInstancesLastYear.filter(ci => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce((acc, curr) => acc + Number(curr.duration), 0)
      this.durationLastYear.push({ name: tenant.name, value: duration });
      this.numberLastYear.push({ name: tenant.name, value: classInstancesTenant.length });
    });
  }

  exportChart(source: string) {
    let storedChart: StoredChart;

    switch (source) {
      case 'Vergleich':
        storedChart = new StoredChart('Vergleich der Anzahl an Tätigkeiten', 'ngx-charts-bar-vertical-2d', JSON.stringify(this.comparisonData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }

  valueFormattingDuration(c) {
    return `${(c)} Stunden`;
  }

  valueFormattingNumber(c) {
    return `${(c)} Tätigkeiten`;
  }

  getMemberSince(tenantId) {
    let classInstancesTenant = this.classInstanceDTOs.filter(ci => {
      return ci.tenantId === tenantId
    });

    let uniqueYears = [...new Set(classInstancesTenant.map(item => new Date(item.dateFrom).getFullYear().toString()))];
    uniqueYears.sort;

    return uniqueYears[0];
  }
}