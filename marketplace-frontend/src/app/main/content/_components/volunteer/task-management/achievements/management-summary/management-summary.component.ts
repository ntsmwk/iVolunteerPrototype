import { Component, OnInit } from "@angular/core";
import {
  ClassInstanceDTO,
  ClassInstance,
} from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { LoginService } from "app/main/content/_service/login.service";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { StoredChartService } from "app/main/content/_service/stored-chart.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { StoredChart } from "app/main/content/_model/stored-chart";
import { isNullOrUndefined } from "util";
import { LocalRepositoryJsonServerService } from "app/main/content/_service/local-repository-jsonServer.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { User, LocalRepositoryLocation } from "app/main/content/_model/user";
import { LocalRepositoryDropboxService } from "app/main/content/_service/local-repository-dropbox.service";
import { LocalRepositoryService } from "app/main/content/_service/local-repository.service";

@Component({
  selector: "fuse-management-summary",
  templateUrl: "./management-summary.component.html",
  styleUrls: ["./management-summary.component.scss"],
})
export class ManagementSummaryComponent implements OnInit {
  // comparison chart
  comparisonXlabel = "Jahr";
  comparisonYlabel = "Anzahl Tätigkeiten";
  colorScheme = "cool";

  schemeType = "ordinal";
  showGridLines = true;
  animations = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = "Datum";
  yAxisLabel = "Dauer [Stunden]";
  noBarWhenZero = true;
  showLabels = true;
  autoScale = true;
  legend = false;
  legendPosition = "below";
  tooltipDisabled = false;

  volunteer: User;
  marketplace: any = [];
  classInstanceDTOs: ClassInstanceDTO[] = [];

  uniqueYears: any[] = [];
  uniqueTenants: Tenant[] = [];
  subscribedTenants: Tenant[] = [];

  durationTotal: any[] = [];
  numberTotal: any[] = [];

  durationYear: any[] = [];
  numberYear: any[] = [];

  comparisonData: any[] = [];
  comparisonYear: number;
  engagementYear: number;

  isLocalRepositoryConnected: boolean;
  isLoaded: boolean = false;

  percentageFilteredOut: number = 0;

  localRepositoryService: LocalRepositoryService;

  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private storedChartService: StoredChartService,
    private tenantService: TenantService,
    private lrDropboxService: LocalRepositoryDropboxService,
    private lrJsonServerService: LocalRepositoryJsonServerService
  ) { }

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.volunteer = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.subscribedTenants = globalInfo.tenants;

    this.comparisonYear = 2019;
    this.engagementYear = 2019;

    this.localRepositoryService = this.loginService.getLocalRepositoryService(
      this.volunteer
    );

    try {
      let localClassInstances = <ClassInstance[]>(
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );

      // TODO
      // get unique marketplaceIds of CIs
      // perform once per marketplaceId
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .mapClassInstancesToDTOs(this.marketplace, localClassInstances)
          .toPromise()
      );
      this.isLocalRepositoryConnected = true;
    } catch (e) {
      this.isLocalRepositoryConnected = false;

      if (!isNullOrUndefined(this.marketplace)) {
        this.classInstanceDTOs = <ClassInstanceDTO[]>(
          await this.classInstanceService
            .getUserClassInstancesByArcheType(
              this.marketplace,
              "TASK",
              this.volunteer.id,
              this.volunteer.subscribedTenants.map((s) => s.tenantId)

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

    this.uniqueYears = [
      ...new Set(
        this.classInstanceDTOs.map((item) =>
          new Date(item.dateFrom).getFullYear()
        )
      ),
    ];
    this.uniqueYears.sort();

    let uniqueTenantIds = [
      ...new Set(this.classInstanceDTOs.map((item) => item.tenantId)),
    ];
    let allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();
    this.uniqueTenants = allTenants.filter((t) => {
      return uniqueTenantIds.indexOf(t.id) !== -1;
    });

    this.generateComparisonChartData(this.comparisonYear);
    this.generateEngagementYearData(this.engagementYear);
    this.generateEngagementTotalData();

    this.isLoaded = true;
  }

  generateComparisonChartData(comparisonYear) {
    this.uniqueTenants.forEach((tenant) => {
      let yearData = this.classInstanceDTOs
        .filter((ci) => {
          return new Date(ci.dateFrom).getFullYear() === comparisonYear;
        })
        .filter((ci) => {
          return ci.tenantId === tenant.id;
        });

      this.comparisonData.push({ name: tenant.name, value: yearData.length });
    });

    let finalData: any[] = [];

    this.uniqueYears.sort();
    this.uniqueYears.forEach((curYear) => {
      let data: any[] = [];
      this.uniqueTenants.forEach((tenant) => {
        let currentData = this.classInstanceDTOs
          .filter((ci) => {
            return new Date(ci.dateFrom).getFullYear() === curYear;
          })
          .filter((ci) => {
            return ci.tenantId === tenant.id;
          });

        data.push({
          name: tenant.name,
          value:
            currentData.length -
            this.comparisonData.find((d) => d.name === tenant.name).value,
        });
      });
      finalData.push({ name: curYear.toString(), series: data });
    });

    this.comparisonData = [...finalData];
  }

  generateEngagementYearData(engagementYear) {
    this.engagementYear = engagementYear;
    let classInstancesYear = this.classInstanceDTOs.filter((ci) => {
      return new Date(ci.dateFrom).getFullYear() === this.engagementYear;
    });

    this.durationYear = [];
    this.numberYear = [];

    this.uniqueTenants.forEach((tenant) => {
      let classInstancesTenant = classInstancesYear.filter((ci) => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce(
        (acc, curr) => acc + Number(curr.duration),
        0
      );
      this.durationYear.push({ name: tenant.name, value: duration });
      this.numberYear.push({
        name: tenant.name,
        value: classInstancesTenant.length,
      });
    });

    this.durationYear = [...this.durationYear];
    this.numberYear = [...this.numberYear];
  }

  generateEngagementTotalData() {
    this.uniqueTenants.forEach((tenant) => {
      let classInstancesTenant = this.classInstanceDTOs.filter((ci) => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce(
        (acc, curr) => acc + Number(curr.duration),
        0
      );
      this.durationTotal.push({ name: tenant.name, value: duration });
      this.numberTotal.push({
        name: tenant.name,
        value: classInstancesTenant.length,
      });
    });
  }

  exportChart(source: string) {
    let storedChart: StoredChart;

    switch (source) {
      case "Vergleich":
        storedChart = new StoredChart(
          "Vergleich der Anzahl an Tätigkeiten",
          "ngx-charts-bar-vertical-2d",
          JSON.stringify(this.comparisonData),
          this.volunteer.id
        );
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }

  valueFormattingDuration(c) {
    return `${c} Stunden`;
  }

  valueFormattingNumber(c) {
    return `${c} Tätigkeiten`;
  }

  getMemberSince(tenantId) {
    let t = this.classInstanceDTOs.filter((ci) => {
      return ci.tenantId === tenantId;
    });
    let uniqueYears = [
      ...new Set(t.map((item) => new Date(item.dateFrom).getFullYear())),
    ];
    return Math.min.apply(Math, uniqueYears);
  }

  getEngagementSince() {
    let uniqueYears = [
      ...new Set(
        this.classInstanceDTOs.map((item) =>
          new Date(item.dateFrom).getFullYear()
        )
      ),
    ];
    return (
      Math.min.apply(Math, uniqueYears) +
      " - " +
      Math.max.apply(Math, uniqueYears)
    );
  }
}
