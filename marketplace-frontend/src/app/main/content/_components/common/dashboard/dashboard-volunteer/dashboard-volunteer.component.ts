import { Component, OnInit, ViewChild, ChangeDetectorRef, AfterViewInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTableDataSource, MatTable } from "@angular/material/table";
import { ShareDialog } from "./share-dialog/share-dialog.component";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { isNullOrUndefined } from "util";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO } from "../../../../_model/meta/class";
import { CoreUserImagePathService } from "../../../../_service/core-user-imagepath.service";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import {
  MatSort,
  MatPaginator,
  Sort,
  MatIconRegistry,
} from "@angular/material";
import { TenantService } from "../../../../_service/core-tenant.service";
import { Volunteer } from "../../../../_model/volunteer";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Tenant } from "app/main/content/_model/tenant";
import { LocalRepositoryService } from "app/main/content";
import { timer } from "rxjs";
import HC_venn from "highcharts/modules/venn";
import * as Highcharts from "highcharts";
HC_venn(Highcharts);

@Component({
  selector: "dashboard-volunteer",
  templateUrl: "./dashboard-volunteer.component.html",
  styleUrls: ["./dashboard-volunteer.component.scss"],
})
export class DashboardVolunteerComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatTable, { static: false }) table: MatTable<ClassInstanceDTO>;

  isLoaded: boolean;

  dataSource = new MatTableDataSource<ClassInstanceDTO>();
  selectedTenants: Tenant[] = [];

  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
    "action",
    "share",
  ];

  image;

  subscribedTenants: Tenant[] = [];
  allTenants: Tenant[] = [];

  marketplaceClassInstances: ClassInstanceDTO[] = [];
  mpAndSharedClassInstances: ClassInstanceDTO[] = [];
  localClassInstances: ClassInstanceDTO[] = [];
  filteredClassInstances: ClassInstanceDTO[] = [];
  sharedClassInstances: ClassInstanceDTO[] = [];
  mpAndLocalClassInstances: ClassInstanceDTO[] = [];

  nrMpOnly: number = 0;
  nrLrOnly: number = 0;
  nrMpUnionLr: number = 0;

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

  vennData = [];

  chartOptions: Highcharts.Options = {
    title: {
      text: undefined
    }
  };

  colors: Map<any, any> = new Map([['marketplace', '#e5e5e5'], ['localRepository', '#ffc9b2'], ['synced', '#afcbe6']]);

  constructor(
    public dialog: MatDialog,
    private coreHelpseekerService: CoreHelpSeekerService,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private userImagePathService: CoreUserImagePathService,
    private localRepositoryService: LocalRepositoryService,
    private volunteerService: CoreVolunteerService,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer,
    private router: Router,
    iconRegistry: MatIconRegistry
  ) {
    iconRegistry.addSvgIcon(
      "info",
      sanitizer.bypassSecurityTrustResourceUrl("assets/icons/info.svg")
    );
    iconRegistry.addSvgIcon(
      "share",
      sanitizer.bypassSecurityTrustResourceUrl("assets/icons/share.svg")
    );
    iconRegistry.addSvgIcon(
      "plus",
      sanitizer.bypassSecurityTrustResourceUrl("assets/icons/plus.svg")
    );
    iconRegistry.addSvgIcon(
      "minus",
      sanitizer.bypassSecurityTrustResourceUrl("assets/icons/minus.svg")
    );
  }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.setVolunteerImage();

    this.subscribedTenants = <Tenant[]>(
      await this.tenantService.findByVolunteerId(this.volunteer.id).toPromise()
    );

    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(
      this.volunteer
    );
    if (this.isLocalRepositoryConnected) {
      let marketplaces = <Marketplace[]>(
        await this.volunteerService
          .findRegisteredMarketplaces(this.volunteer.id)
          .toPromise()
      );
      this.marketplace = marketplaces[0];

      this.mpAndSharedClassInstances = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .getUserClassInstancesByArcheType(
            this.marketplace,
            "TASK",
            this.volunteer.id,
            this.volunteer.subscribedTenants
          )
          .toPromise()
      );

      this.mpAndSharedClassInstances.forEach((ci) => {
        if (ci.tenantId != ci.issuerId) {
          this.sharedClassInstances.push(ci);
        } else {
          this.marketplaceClassInstances.push(ci);
        }
      });

      this.localClassInstances = <ClassInstanceDTO[]>(
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );

      this.calcMetrics();

      // concat local and mp and remove dublicates (union)
      this.filteredClassInstances = this.localClassInstances.concat(
        this.marketplaceClassInstances.filter(
          (mp) => this.localClassInstances.map((lo) => lo.id).indexOf(mp.id) < 0
        )
      );

      this.filteredClassInstances = this.filteredClassInstances.sort((a, b) => {
        if (a.dateFrom && b.dateFrom) {
          return b.dateFrom.valueOf() - a.dateFrom.valueOf();
        }
      });

      this.paginator.length = this.filteredClassInstances.length;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.dataSource.data = this.filteredClassInstances;
    }
  }

  setVolunteerImage() {
    let objectURL = "data:image/png;base64," + this.volunteer.image;
    this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  navigateToTenantOverview() {
    this.router.navigate(["/main/dashboard/tenants"]);
  }

  getTenantImage(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    if (isNullOrUndefined(tenant)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return tenant.image;
    }
  }

  getIssuerName(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);

    if (!isNullOrUndefined(tenant)) {
      return tenant.name;
    } else {
      return "";
    }
  }

  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: "700px",
      height: "255px",
      data: { name: "share" },
    });

    dialogRef.afterClosed().subscribe((result: any) => { });
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    // concat local and mp and remove dublicates
    this.filteredClassInstances = this.localClassInstances.concat(
      this.marketplaceClassInstances.filter(
        (mp) => this.localClassInstances.map((lo) => lo.id).indexOf(mp.id) < 0
      )
    );

    this.filteredClassInstances = this.filteredClassInstances.filter((ci) => {
      return this.selectedTenants.findIndex((t) => t.id === ci.tenantId) >= 0;
    });

    this.filteredClassInstances.sort((a, b) => {
      if (a.dateFrom && b.dateFrom) {
        return b.dateFrom.valueOf() - a.dateFrom.valueOf();
      }
    });

    this.paginator.length = this.filteredClassInstances.length;
    this.dataSource.data = this.filteredClassInstances;
  }

  //---- Local Repository functions -----//

  inLocalRepository(classInstance: ClassInstanceDTO) {
    return (
      this.localClassInstances.findIndex((t) => t.id === classInstance.id) >= 0
    );
  }

  async syncOneToLocalRepository(classInstance: ClassInstanceDTO) {
    this.localClassInstances = <ClassInstanceDTO[]>(
      await this.localRepositoryService
        .synchronizeSingleClassInstance(this.volunteer, classInstance)
        .toPromise()
    );
    this.calcMetrics();
  }

  async removeOneFromLocalRepository(classInstance: ClassInstanceDTO) {
    this.localClassInstances = <ClassInstanceDTO[]>(
      await this.localRepositoryService
        .removeSingleClassInstance(this.volunteer, classInstance)
        .toPromise()
    );
    this.calcMetrics();
  }

  async syncAllToLocalRepository() {
    let missingClassInstances: ClassInstanceDTO[] = [];
    this.filteredClassInstances.forEach((ci) => {
      if (!(this.localClassInstances.findIndex((t) => t.id === ci.id) >= 0)) {
        missingClassInstances.push(ci);
      }
    });

    this.localClassInstances = <ClassInstanceDTO[]>(
      await this.localRepositoryService
        .synchronizeClassInstances(this.volunteer, missingClassInstances)
        .toPromise()
    );
    this.calcMetrics();
  }

  async removeAllFromLocalRepository() {
    this.localClassInstances = <ClassInstanceDTO[]>(
      await this.localRepositoryService
        .removeClassInstances(this.volunteer, this.filteredClassInstances)
        .toPromise()
    );

    this.calcMetrics();
  }

  //---- Local Repository functions end -----//

  sortData(sort: Sort) {
    this.dataSource.data = this.dataSource.data.sort((a, b) => {
      const isAsc = sort.direction === "asc";
      switch (sort.active) {
        case "issuer":
          return this.compare(
            this.getIssuerName(a.issuerId),
            this.getIssuerName(b.issuerId),
            isAsc
          );
        case "taskName":
          return this.compare(a.name, b.name, isAsc);
        case "taskType1":
          return this.compare(a.taskType1, b.taskType1, isAsc);
        case "date":
          return this.compare(a.dateFrom, b.dateFrom, isAsc);
        case "action":
          return this.compare(
            this.inLocalRepository(a).toString(),
            this.inLocalRepository(b).toString(),
            isAsc
          );
        default:
          return 0;
      }
    });
  }

  compare(
    a: number | string | Date,
    b: number | string | Date,
    isAsc: boolean
  ) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  navigateToClassInstanceDetails(row) {
    this.router.navigate(["main/details/" + row.id]);
  }

  //---- Share functionality -----//

  getShareableTenants(ci: ClassInstanceDTO) {
    let tenants: Tenant[];
    tenants = this.subscribedTenants;
    tenants = tenants.filter((t) => t.id != ci.tenantId);

    this.sharedClassInstances.forEach((shared) => {
      if (ci.name === shared.name && ci.timestamp === shared.timestamp) {
        tenants = tenants.filter((t) => t.id != shared.tenantId);
      }
    });

    return tenants;
  }

  getSharedWith(ci: ClassInstanceDTO) {
    let tenants: Tenant[] = [];

    this.sharedClassInstances.forEach((shared) => {
      if (ci.name === shared.name && ci.timestamp === shared.timestamp) {
        tenants.push(this.allTenants.find((t) => t.id === shared.tenantId));
      }
    });

    return tenants;
  }

  async shareClassInstance(ci: ClassInstanceDTO, tenant: Tenant) {
    // TODO: @Philipp: marketplace muss jener von ci und nicht vom volunteer sein, aktuell gibt es nur einen, deswegen ok

    let sharedCi = <ClassInstanceDTO>(
      await this.classInstanceService
        .createSharedClassInstances(this.marketplace, tenant.id, ci.id)
        .toPromise()
    );
    this.sharedClassInstances.push(sharedCi);

    // TODO: redraw table
  }

  async revokeClassInstance(ci: ClassInstanceDTO, tenant: Tenant) {
    // TODO: @Philipp: marketplace muss jener von ci und nicht vom volunteer sein, aktuell gibt es nur einen, deswegen ok

    let deleteCi;
    this.sharedClassInstances.forEach((shared, index, self) => {
      if (
        ci.name === shared.name &&
        ci.timestamp === shared.timestamp &&
        shared.tenantId === tenant.id
      ) {
        deleteCi = shared;
        self.splice(index, 1);
      }
    });
    await this.classInstanceService
      .deleteClassInstance(this.marketplace, deleteCi.id)
      .toPromise();
  }

  //---- Share functionality end -----//

  calcMetrics() {
    // intersection of CIs on mp and local repo
    this.mpAndLocalClassInstances = this.localClassInstances.filter(
      (ci) =>
        -1 !== this.marketplaceClassInstances.map((ci) => ci.id).indexOf(ci.id)
    );
    this.nrMpUnionLr = this.mpAndLocalClassInstances.length;

    // only in mp (mp minus interesction)
    this.nrMpOnly =
      this.marketplaceClassInstances.length -
      this.mpAndLocalClassInstances.length;

    // only in local repo (local repo minus intersection)
    this.nrLrOnly =
      this.localClassInstances.length - this.mpAndLocalClassInstances.length;

    this.generateVennData();
  }

  generateVennData() {
    let data = [];
    data.push(
      {
        sets: ['Freiwilligenpass'],
        value:  this.localClassInstances.length, //2,
        displayValue: this.localClassInstances.length,
        color: this.colors.get('localRepository')
      },
      {
        sets: ['Marktplatz'],
        value: this.marketplaceClassInstances.length, //2,
        displayValue: this.marketplaceClassInstances.length,
        color: this.colors.get('marketplace')
      },
      {
        sets: ['Freiwilligenpass', 'Marktplatz'],
        value: this.nrMpUnionLr, //1,
        displayValue: this.nrMpUnionLr,
        color: this.colors.get('synced'),
        name: 'Synchronisiert'
      });

    this.vennData = [...data];
    this.chartOptions.series = [
      {
        name: 'Anzahl Einträge',
        type: "venn",
        data: this.vennData,
        tooltip: {
          pointFormat: '{point.name}: {point.displayValue}',
        },
      },
    ];
    Highcharts.chart("container", this.chartOptions);
  }

  getStyle(ci: ClassInstanceDTO) {
    if (this.mpAndLocalClassInstances.findIndex(c => c.id === ci.id) >= 0) {
      return {
        "background-color": this.colors.get('synced')
      };
    } else if (this.localClassInstances.findIndex(c => c.id === ci.id) >= 0) {
      return {
        "background-color": this.colors.get('localRepository')
      };
    } else {
      return {
        "background-color": this.colors.get('marketplace')
      };
    }
  }
}

export interface DialogData {
  name: string;
}
