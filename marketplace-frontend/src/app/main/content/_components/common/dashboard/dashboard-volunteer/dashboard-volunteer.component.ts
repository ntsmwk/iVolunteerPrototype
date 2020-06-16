import { Component, OnInit, ViewChild, ChangeDetectorRef } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTableDataSource, MatTable } from "@angular/material/table";
import { ShareDialog } from "./share-dialog/share-dialog.component";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { isNullOrUndefined } from "util";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO, ClassInstance } from "../../../../_model/meta/class";
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
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { DialogFactoryDirective } from "app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.component";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { GlobalService } from "app/main/content/_service/global.service";
HC_venn(Highcharts);

@Component({
  selector: "dashboard-volunteer",
  templateUrl: "./dashboard-volunteer.component.html",
  styleUrls: ["./dashboard-volunteer.component.scss"],
  providers: [DialogFactoryDirective],
})
export class DashboardVolunteerComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatTable, { static: true }) table: MatTable<ClassInstanceDTO>;

  isLoaded: boolean;

  dataSource = new MatTableDataSource<ClassInstanceDTO>();
  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
    "action",
    "share",
  ];

  image;

  selectedTenants: Tenant[] = [];
  subscribedTenants: Tenant[] = [];
  allTenants: Tenant[] = [];

  marketplaceClassInstanceDTOs: ClassInstanceDTO[] = [];
  localClassInstances: ClassInstance[] = [];
  localClassInstanceDTOs: ClassInstanceDTO[] = [];
  filteredClassInstanceDTOs: ClassInstanceDTO[] = [];
  sharedClassInstanceDTOs: ClassInstanceDTO[] = [];
  mpAndLocalClassInstanceDTOs: ClassInstanceDTO[] = [];

  nrMpUnionLr: number = 0;

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

  vennData = [];
  chartOptions: Highcharts.Options = {
    title: {
      text: undefined,
    },
  };

  colors: Map<String, String> = new Map([
    ["marketplace", "#50B3EF"],
    ["localRepository", "#EF8C50"],
  ]);

  colorsOpac: Map<String, String> = new Map([
    ["marketplace", this.colors.get("marketplace") + "66"],
    ["localRepository", this.colors.get("localRepository") + "66"],
  ]);

  constructor(
    public dialog: MatDialog,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private localRepositoryService: LocalRepositoryService,
    private volunteerService: CoreVolunteerService,
    private marketplaceService: MarketplaceService,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private iconRegistry: MatIconRegistry,
    private changeDetectorRefs: ChangeDetectorRef,
    private dialogFactory: DialogFactoryDirective,
    private globalService: GlobalService
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
    iconRegistry.addSvgIcon(
      "storeSlash",
      sanitizer.bypassSecurityTrustResourceUrl("assets/icons/storeSlash.svg")
    );
  }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    let globalInfo = <GlobalInfo>(
      await this.globalService.getGlobalInfo().toPromise()
    );

    this.volunteer = <Volunteer>globalInfo.participant;
    this.marketplace = globalInfo.marketplace;
    this.subscribedTenants = globalInfo.tenants;

    this.setVolunteerImage();

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

      let mpAndSharedClassInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .getUserClassInstancesByArcheType(
            this.marketplace,
            "TASK",
            this.volunteer.id,
            this.volunteer.subscribedTenants
          )
          .toPromise()
      );

      mpAndSharedClassInstanceDTOs.forEach((ci) => {
        if (ci.tenantId != ci.issuerId) {
          this.sharedClassInstanceDTOs.push(ci);
        } else {
          this.marketplaceClassInstanceDTOs.push(ci);
        }
      });

      this.localClassInstances = <ClassInstance[]>(
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );

      // TODO Philipp
      // get unique marketplaceIds of CIs
      // perform once per marketplaceId
      this.localClassInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .mapClassInstancesToDTOs(this.marketplace, this.localClassInstances)
          .toPromise()
      );

      this.calcMetrics();

      // concat local and mp and remove dublicates (union)
      this.filteredClassInstanceDTOs = this.localClassInstanceDTOs.concat(
        this.marketplaceClassInstanceDTOs.filter(
          (mp) =>
            this.localClassInstanceDTOs.map((lo) => lo.id).indexOf(mp.id) < 0
        )
      );

      this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.sort(
        (a, b) => {
          if (a.dateFrom && b.dateFrom) {
            return b.dateFrom.valueOf() - a.dateFrom.valueOf();
          }
        }
      );

      this.paginator.length = this.filteredClassInstanceDTOs.length;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.dataSource.data = this.filteredClassInstanceDTOs;
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

  getTenantName(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    return tenant.name;
  }

  getIssuerName(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);

    if (!isNullOrUndefined(tenant)) {
      return tenant.name;
    } else {
      return "Unbekannt";
    }
  }

  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: "700px",
      height: "255px",
      data: { name: "share" },
    });

    dialogRef.afterClosed().subscribe((result: any) => {});
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    // concat local and mp and remove dublicates
    this.filteredClassInstanceDTOs = this.localClassInstanceDTOs.concat(
      this.marketplaceClassInstanceDTOs.filter(
        (mp) =>
          this.localClassInstanceDTOs.map((lo) => lo.id).indexOf(mp.id) < 0
      )
    );

    this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
      (ci) => {
        return this.selectedTenants.findIndex((t) => t.id === ci.tenantId) >= 0;
      }
    );

    this.filteredClassInstanceDTOs.sort((a, b) => {
      if (a.dateFrom && b.dateFrom) {
        return b.dateFrom.valueOf() - a.dateFrom.valueOf();
      }
    });

    this.paginator.length = this.filteredClassInstanceDTOs.length;
    this.dataSource.data = this.filteredClassInstanceDTOs;
  }

  //---- Local Repository functions -----//

  isInLocalRepository(classInstance: ClassInstanceDTO) {
    return (
      this.localClassInstanceDTOs.findIndex((t) => t.id === classInstance.id) >=
      0
    );
  }

  async syncOneToLocalRepository(ciDTO: ClassInstanceDTO) {
    let marketplace = <Marketplace>(
      await this.marketplaceService.findById(ciDTO.marketplaceId).toPromise()
    );
    let ci = <ClassInstance>(
      await this.classInstanceService
        .getClassInstanceById(marketplace, ciDTO.id)
        .toPromise()
    );

    await this.localRepositoryService
      .synchronizeSingleClassInstance(this.volunteer, ci)
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs.push(ciDTO);
      });

    this.calcMetrics();
  }

  async removeOneFromLocalRepository(ciDTO: ClassInstanceDTO) {
    if (
      this.marketplaceClassInstanceDTOs.findIndex((c) => c.id === ciDTO.id) ===
      -1
    ) {
      this.dialogFactory
        .confirmationDialog(
          "Wirklich entfernen?",
          "Der Eintrag befindet sich nur mehr in Ihrem lokalen Freiwilligenpass, löschen Sie den Eintrag würde er unwiderruflich verloren gehen."
        )
        .then(async (ret: boolean) => {
          if (ret) {
            await this.localRepositoryService
              .removeSingleClassInstance(this.volunteer, ciDTO.id)
              .toPromise()
              .then(() => {
                this.localClassInstanceDTOs.splice(
                  this.localClassInstanceDTOs.findIndex(
                    (c) => c.id === ciDTO.id
                  ),
                  1
                );
              });

            // remove row
            this.dataSource.data.splice(this.dataSource.data.indexOf(ciDTO), 1);
            this.dataSource._updateChangeSubscription();
          }
        });
    } else {
      await this.localRepositoryService
        .removeSingleClassInstance(this.volunteer, ciDTO.id)
        .toPromise()
        .then(() => {
          this.localClassInstanceDTOs.splice(
            this.localClassInstanceDTOs.findIndex((c) => c.id === ciDTO.id),
            1
          );
        });
    }

    this.calcMetrics();
  }

  async syncAllToLocalRepository() {
    let missingCiDTOs: ClassInstanceDTO[] = [];
    this.filteredClassInstanceDTOs.forEach((ci) => {
      if (
        !(this.localClassInstanceDTOs.findIndex((t) => t.id === ci.id) >= 0)
      ) {
        missingCiDTOs.push(ci);
      }
    });

    // TODO Philipp
    // sort missingClassInstances by marketplaceId
    // for each marketplaceId call classInstanceService to get CI from correct marketplace...
    let missingCis = <ClassInstance[]>await this.classInstanceService
      .getClassInstancesById(
        this.marketplace,
        missingCiDTOs.map((c) => c.id)
      )
      .toPromise();

    await this.localRepositoryService
      .synchronizeClassInstances(this.volunteer, missingCis)
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs = this.localClassInstanceDTOs.concat(
          missingCiDTOs.filter(
            (mp) =>
              this.localClassInstanceDTOs.map((lo) => lo.id).indexOf(mp.id) < 0
          )
        );
      });

    this.calcMetrics();
  }

  async removeAllFromLocalRepository() {
    let filteredClassInstances = <ClassInstance[]>(
      await this.classInstanceService
        .getClassInstancesById(
          this.marketplace,
          this.filteredClassInstanceDTOs.map((c) => c.id)
        )
        .toPromise()
    );

    filteredClassInstances = filteredClassInstances.filter((c) => {
      return c !== null && this.isSynced(c);
    });

    await this.localRepositoryService
      .removeClassInstances(
        this.volunteer,
        filteredClassInstances.map((ci) => ci.id)
      )
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs = this.localClassInstanceDTOs.filter(
          (a) => filteredClassInstances.map((b) => b.id).indexOf(a.id) < 0
        );
      });

    this.calcMetrics();
  }

  //---- Local Repository functions end -----//

  async removeOneFromMarketplace(ci: ClassInstanceDTO) {
    let marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    await this.classInstanceService
      .deleteClassInstance(marketplace, ci.id)
      .toPromise();
    this.marketplaceClassInstanceDTOs.forEach((item, index, array) => {
      if (item.id === ci.id) {
        array.splice(index, 1);
      }
    });

    this.sharedClassInstanceDTOs.forEach(async (item, index, array) => {
      if (ci.name === item.name && ci.timestamp === item.timestamp) {
        array.splice(index, 1);
        await this.classInstanceService
          .deleteClassInstance(marketplace, item.id)
          .toPromise();
      }
    });

    this.calcMetrics();
  }

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
            this.isInLocalRepository(a).toString(),
            this.isInLocalRepository(b).toString(),
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

    this.sharedClassInstanceDTOs.forEach((shared) => {
      if (ci.name === shared.name && ci.timestamp === shared.timestamp) {
        tenants = tenants.filter((t) => t.id != shared.tenantId);
      }
    });

    return tenants;
  }

  getSharedWith(ci: ClassInstanceDTO) {
    let tenants: Tenant[] = [];

    this.sharedClassInstanceDTOs.forEach((shared) => {
      if (ci.name === shared.name && ci.timestamp === shared.timestamp) {
        tenants.push(this.allTenants.find((t) => t.id === shared.tenantId));
      }
    });

    return tenants;
  }

  async shareClassInstanceToOthers(ci: ClassInstanceDTO, tenant: Tenant) {
    let marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    let sharedCi = <ClassInstanceDTO>(
      await this.classInstanceService
        .createSharedClassInstances(marketplace, tenant.id, ci.id)
        .toPromise()
    );
    this.sharedClassInstanceDTOs.push(sharedCi);

    // TODO: redraw table
    // Does not work ;)
    // this.changeDetectorRefs.detectChanges();
    // this.paginator._changePageSize(this.paginator.pageSize);
    // this.table.renderRows();
    // this.changeDetectorRefs.detectChanges();
  }

  async shareClassInstanceToIssuer(ci: ClassInstanceDTO, tenantId: string) {
    let marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    let share = <ClassInstance>(
      await this.localRepositoryService
        .getSingleClassInstance(this.volunteer, ci.id)
        .toPromise()
    );

    await this.classInstanceService
      .createNewTaskClassInstance(marketplace, share)
      .toPromise()
      .then(() => {
        this.marketplaceClassInstanceDTOs.push(ci);
      });

    this.generateVennData();
  }

  async revokeClassInstance(ci: ClassInstanceDTO, tenant: Tenant) {
    let marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    this.sharedClassInstanceDTOs.forEach(async (shared, index, array) => {
      if (
        ci.name === shared.name &&
        ci.timestamp === shared.timestamp &&
        shared.tenantId === tenant.id
      ) {
        array.splice(index, 1);
        await this.classInstanceService
          .deleteClassInstance(marketplace, shared.id)
          .toPromise();
      }
    });
  }

  //---- Share functionality end -----//

  calcMetrics() {
    // intersection of CIs on mp and local repo
    this.mpAndLocalClassInstanceDTOs = this.localClassInstanceDTOs.filter(
      (a) =>
        this.marketplaceClassInstanceDTOs.map((b) => b.id).indexOf(a.id) !== -1
    );
    this.nrMpUnionLr = this.mpAndLocalClassInstanceDTOs.length;
    this.generateVennData();
  }

  generateVennData() {
    let data = [];
    data.push(
      {
        sets: ["Freiwilligenpass"],
        value: this.localClassInstanceDTOs.length, //2,
        displayValue: this.localClassInstanceDTOs.length,
        color: this.colors.get("localRepository"),
        dataLabels: {
          y: -15,
        },
      },
      {
        sets: ["Marktplatz"],
        value: this.marketplaceClassInstanceDTOs.length, //2,
        displayValue: this.marketplaceClassInstanceDTOs.length,
        color: this.colors.get("marketplace"),
      },
      {
        sets: ["Freiwilligenpass", "Marktplatz"],
        value: this.nrMpUnionLr, //1,
        displayValue: this.nrMpUnionLr,
        name: "Synchronisiert",
        dataLabels: {
          y: 15,
        },
        color: {
          linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
          stops: [
            [0, this.colorsOpac.get("marketplace")], // start
            [1, this.colorsOpac.get("localRepository")], // end
          ],
        },
      }
    );

    this.vennData = [...data];
    this.chartOptions.series = [
      {
        name: "Anzahl Einträge",
        type: "venn",
        data: this.vennData,
        tooltip: {
          pointFormat: "{point.name}: {point.displayValue}",
        },
        cursor: "pointer",
        events: {
          click: (event) => {
            this.onVennClicked(event);
          },
        },
        dataLabels: {
          align: "center",
          allowOverlap: false,
        },
      },
    ];
    Highcharts.chart("container", this.chartOptions);
  }

  onVennClicked(event) {
    // console.error(event.point.name);
  }

  getTableRowColor(ci: ClassInstanceDTO) {
    if (this.isSynced(ci)) {
      return {
        "background-image":
          "repeating-linear-gradient(to right," +
          this.colorsOpac.get("marketplace") +
          " 0%, " +
          this.colorsOpac.get("localRepository") +
          " 33%, " +
          this.colorsOpac.get("localRepository") +
          " 33%, " +
          this.colorsOpac.get("marketplace") +
          " 66%, " +
          this.colorsOpac.get("marketplace") +
          " 66%, " +
          this.colorsOpac.get("localRepository") +
          " 100%)",
      };
    } else if (this.isLocalRepositoryOnly(ci)) {
      return {
        "background-color": this.colorsOpac.get("localRepository"),
      };
    } else if (this.isMarketplaceOnly(ci)) {
      return {
        "background-color": this.colorsOpac.get("marketplace"),
      };
    }
  }

  isMarketplaceOnly(ci: ClassInstanceDTO) {
    return (
      this.marketplaceClassInstanceDTOs.findIndex((c) => c.id === ci.id) >= 0 &&
      this.localClassInstanceDTOs.findIndex((c) => c.id === ci.id) === -1
    );
  }
  isLocalRepositoryOnly(ci: ClassInstanceDTO | ClassInstance) {
    return (
      this.localClassInstanceDTOs.findIndex((c) => c.id === ci.id) >= 0 &&
      this.marketplaceClassInstanceDTOs.findIndex((c) => c.id === ci.id) === -1
    );
  }
  isSynced(ci: ClassInstanceDTO | ClassInstance) {
    return (
      this.marketplaceClassInstanceDTOs.findIndex((c) => c.id === ci.id) >= 0 &&
      this.localClassInstanceDTOs.findIndex((c) => c.id === ci.id) >= 0
    );
  }
}

export interface DialogData {
  name: string;
}
