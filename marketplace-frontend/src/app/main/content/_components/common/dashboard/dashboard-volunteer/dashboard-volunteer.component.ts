import { Component, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTableDataSource } from "@angular/material/table";
import { ShareDialog } from "./share-dialog/share-dialog.component";
import { isNullOrUndefined } from "util";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO, ClassInstance } from "../../../../_model/meta/class";
import {
  MatSort,
  MatPaginator,
  Sort,
  MatIconRegistry
} from "@angular/material";
import { TenantService } from "../../../../_service/core-tenant.service";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Tenant } from "app/main/content/_model/tenant";
import HC_venn from "highcharts/modules/venn";
import * as Highcharts from "highcharts";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { DialogFactoryDirective } from "app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { User } from "app/main/content/_model/user";
import { LoginService } from "app/main/content/_service/login.service";
import { LocalRepositoryService } from "app/main/content/_service/local-repository.service";
import { CoreUserService } from "app/main/content/_service/core-user.service";
HC_venn(Highcharts);

@Component({
  selector: "dashboard-volunteer",
  templateUrl: "./dashboard-volunteer.component.html",
  styleUrls: ["./dashboard-volunteer.component.scss"],
  providers: [DialogFactoryDirective]
})
export class DashboardVolunteerComponent implements OnInit {
  volunteer: User;
  marketplace: Marketplace;
  localRepositoryService: LocalRepositoryService;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  isLoaded = false;

  dataSource = new MatTableDataSource<ClassInstanceDTO>();
  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
    "action",
    "share"
  ];

  image;

  selectedTenants: Tenant[] = [];
  subscribedTenants: Tenant[] = [];
  allTenants: Tenant[] = [];

  marketplaceClassInstanceDTOs: ClassInstanceDTO[] = [];
  localClassInstanceDTOs: ClassInstanceDTO[] = [];
  filteredClassInstanceDTOs: ClassInstanceDTO[] = [];
  sharedClassInstanceDTOs: ClassInstanceDTO[] = [];
  mpAndLocalClassInstanceDTOs: ClassInstanceDTO[] = [];

  // <original CI.id, Tenants>
  sharedWithMap: Map<string, Tenant[]> = new Map();

  nrMpUnionLr = 0;

  isLocalRepositoryConnected = true;

  vennData = [];
  chartOptions: Highcharts.Options = {
    title: {
      text: undefined
    }
  };

  colors: Map<String, String> = new Map([
    ["marketplace", "#50B3EF"],
    ["localRepository", "#EF8C50"]
  ]);

  colorsOpac: Map<String, String> = new Map([
    ["marketplace", this.colors.get("marketplace") + "66"],
    ["localRepository", this.colors.get("localRepository") + "66"]
  ]);

  isAllSyncing: boolean = false;
  isAllDesyncing: boolean = false;

  constructor(
    public dialog: MatDialog,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: MarketplaceService,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private iconRegistry: MatIconRegistry,
    private dialogFactory: DialogFactoryDirective,
    private loginService: LoginService,
    private userService: CoreUserService
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
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.volunteer = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.subscribedTenants = globalInfo.tenants;
    this.image = this.userService.getUserProfileImage(this.volunteer);
    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();

    this.localRepositoryService = this.loginService.getLocalRepositoryService(
      this.volunteer
    );

    const mpAndSharedClassInstanceDTOs = <ClassInstanceDTO[]>(
      await this.classInstanceService
        .getUserClassInstancesByArcheType(
          this.marketplace,
          "TASK",
          this.volunteer.id,
          this.volunteer.subscribedTenants.map(s => s.tenantId)
        )
        .toPromise()
    );

    mpAndSharedClassInstanceDTOs.forEach(ci => {
      if (ci.tenantId != ci.issuerId) {
        this.sharedClassInstanceDTOs.push(ci);
      } else {
        this.marketplaceClassInstanceDTOs.push(ci);
      }
    });

    try {
      const localClassInstances = <ClassInstance[]>(
        await this.localRepositoryService
          .findClassInstancesByVolunteer(this.volunteer)
          .toPromise()
      );

      // TODO
      // get unique marketplaceIds of CIs
      // perform once per marketplaceId
      this.localClassInstanceDTOs = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .mapClassInstancesToDTOs(this.marketplace, localClassInstances)
          .toPromise()
      );

      this.generateVennData();

      // concat local and mp and remove dublicates (union)
      this.filteredClassInstanceDTOs = this.localClassInstanceDTOs.concat(
        this.marketplaceClassInstanceDTOs.filter(
          mp => this.localClassInstanceDTOs.map(lo => lo.id).indexOf(mp.id) < 0
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

      this.generateSharedTenantsMap();
      this.isLocalRepositoryConnected = true;
    } catch (e) {
      this.isLocalRepositoryConnected = false;
    }
    this.isLoaded = true;
    // console.error(this.dataSource.data);
    // console.error(this.volunteer);
  }

  getTenantImageById(tenantId: string) {
    const tenant = this.allTenants.find(t => t.id === tenantId);

    return !isNullOrUndefined(tenant) ? tenant.imagePath : null;
  }

  navigateToClassInstanceDetails(row) {
    this.router.navigate(["main/details/" + row.id]);
  }

  getTenantName(tenantId: string) {
    const tenant = this.allTenants.find(t => t.id === tenantId);
    return tenant.name;
  }

  getIssuerName(tenantId: string) {
    const tenant = this.allTenants.find(t => t.id === tenantId);

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
      data: { name: "share" }
    });

    dialogRef.afterClosed().subscribe((result: any) => {});
  }

  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    // concat local and mp and remove dublicates
    this.filteredClassInstanceDTOs = this.localClassInstanceDTOs.concat(
      this.marketplaceClassInstanceDTOs.filter(
        mp => this.localClassInstanceDTOs.map(lo => lo.id).indexOf(mp.id) < 0
      )
    );

    this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
      ci => {
        return this.selectedTenants.findIndex(t => t.id === ci.tenantId) >= 0;
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

  generateSharedTenantsMap() {
    this.marketplaceClassInstanceDTOs.forEach(ci => {
      const sharedCis = this.sharedClassInstanceDTOs.filter(s => {
        return ci.timestamp === s.timestamp;
      });

      let sharedTenants: Tenant[] = [];
      if (this.sharedWithMap.get(ci.id)) {
        sharedTenants = this.sharedWithMap.get(ci.id);
      }
      sharedCis.forEach(s => {
        sharedTenants.push(this.allTenants.find(t => t.id === s.tenantId));
      });
      this.sharedWithMap.set(ci.id, sharedTenants);
    });
  }

  // ---- Local Repository functions -----//

  isInLocalRepository(classInstance: ClassInstanceDTO) {
    return (
      this.localClassInstanceDTOs.findIndex(t => t.id === classInstance.id) >= 0
    );
  }

  async syncOneToLocalRepository(ciDTO: ClassInstanceDTO) {
    const marketplace = <Marketplace>(
      await this.marketplaceService.findById(ciDTO.marketplaceId).toPromise()
    );
    let ci = <ClassInstance>(
      await this.classInstanceService
        .getClassInstanceById(marketplace, ciDTO.id)
        .toPromise()
    );

    // remove allowedValues, since too large for localRepository... 
    ci.properties.forEach(p => p.allowedValues = []);

    this.localRepositoryService
      .synchronizeSingleClassInstance(this.volunteer, ci)
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs.push(ciDTO);
        this.generateVennData();
      });
  }

  removeOneFromLocalRepository(ciDTO: ClassInstanceDTO) {
    if (
      this.marketplaceClassInstanceDTOs.findIndex(c => c.id === ciDTO.id) === -1
    ) {
      this.dialogFactory
        .confirmationDialog(
          "Wirklich entfernen?",
          "Der Eintrag befindet sich nur mehr in Ihrem lokalen Freiwilligenpass, löschen Sie den Eintrag würde er unwiderruflich verloren gehen."
        )
        .then((ret: boolean) => {
          if (ret) {
            this.localRepositoryService
              .removeSingleClassInstance(this.volunteer, ciDTO.id)
              .toPromise()
              .then(() => {
                this.localClassInstanceDTOs.splice(
                  this.localClassInstanceDTOs.findIndex(c => c.id === ciDTO.id),
                  1
                );

                // remove row
                this.dataSource.data.splice(
                  this.dataSource.data.indexOf(ciDTO),
                  1
                );
                this.dataSource._updateChangeSubscription();

                this.generateVennData();
              });
          } else {
          }
        });
    } else {
      this.localRepositoryService
        .removeSingleClassInstance(this.volunteer, ciDTO.id)
        .toPromise()
        .then(() => {
          this.localClassInstanceDTOs.splice(
            this.localClassInstanceDTOs.findIndex(c => c.id === ciDTO.id),
            1
          );

          this.generateVennData();
        });
    }
  }

  async syncAllToLocalRepository() {
    this.isAllSyncing = true;
    const missingCiDTOs: ClassInstanceDTO[] = [];
    this.filteredClassInstanceDTOs.forEach(ci => {
      if (!(this.localClassInstanceDTOs.findIndex(t => t.id === ci.id) >= 0)) {
        missingCiDTOs.push(ci);
      }
    });

    // TODO
    // sort missingClassInstances by marketplaceId
    // for each marketplaceId call classInstanceService to get CI from correct marketplace...
    const missingCis = <ClassInstance[]>await this.classInstanceService
      .getClassInstancesById(
        this.marketplace,
        missingCiDTOs.map(c => c.id)
      )
      .toPromise();

      // remove allowedValues, since too large for localRepository... 
      missingCis.forEach(ci => {
        ci.properties.forEach(p => p.allowedValues = [])
      });

    this.localRepositoryService
      .synchronizeClassInstances(this.volunteer, missingCis)
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs = this.localClassInstanceDTOs.concat(
          missingCiDTOs.filter(
            mp =>
              this.localClassInstanceDTOs.map(lo => lo.id).indexOf(mp.id) < 0
          )
        );

        this.generateVennData();
        this.isAllSyncing = false;
      });
  }

  async removeAllFromLocalRepository() {
    this.isAllDesyncing = true;
    let filteredClassInstances = <ClassInstance[]>(
      await this.classInstanceService
        .getClassInstancesById(
          this.marketplace,
          this.filteredClassInstanceDTOs.map(c => c.id)
        )
        .toPromise()
    );

    filteredClassInstances = filteredClassInstances.filter(c => {
      return c !== null && this.isSynced(c);
    });

    this.localRepositoryService
      .removeClassInstances(
        this.volunteer,
        filteredClassInstances.map(ci => ci.id)
      )
      .toPromise()
      .then(() => {
        this.localClassInstanceDTOs = this.localClassInstanceDTOs.filter(
          a => filteredClassInstances.map(b => b.id).indexOf(a.id) < 0
        );

        this.generateVennData();
        this.isAllDesyncing = false;
      });
  }

  // ---- Local Repository functions end -----//

  // ---- Share to marketplace functions -----//

  getShareableTenants(ci: ClassInstanceDTO) {
    // subscribedTenants - sharedWithTenats - ci.issuerId
    let tenants: Tenant[] = [];
    let sharedWith: Tenant[] = [];
    if (this.sharedWithMap.get(ci.id)) {
      sharedWith = this.sharedWithMap.get(ci.id);
    }

    const ownTenant = this.allTenants.find(t => t.id === ci.issuerId);
    tenants = this.subscribedTenants.filter(s => {
      return sharedWith.map(t => t.id).indexOf(s.id) < 0;
    });
    tenants = tenants.filter(t => t.id !== ownTenant.id);

    return tenants;
  }

  getSharedWithTenants(ci: ClassInstanceDTO) {
    return this.sharedWithMap.get(ci.id);
  }

  async shareClassInstanceToOthers(ci: ClassInstanceDTO, tenant: Tenant) {
    const marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    const sharedCi = <ClassInstanceDTO>(
      await this.classInstanceService
        .createSharedClassInstances(marketplace, tenant.id, ci.id)
        .toPromise()
    );
    this.sharedClassInstanceDTOs.push(sharedCi);

    let sharedTenants = [];
    if (this.sharedWithMap.get(ci.id)) {
      sharedTenants = this.sharedWithMap.get(ci.id);
    }
    sharedTenants.push(tenant);
    this.sharedWithMap.set(ci.id, sharedTenants);
  }

  async shareClassInstanceToIssuer(ci: ClassInstanceDTO) {
    const marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    const share = <ClassInstance>(
      await this.localRepositoryService
        .getSingleClassInstance(this.volunteer, ci.id)
        .toPromise()
    );
    const list = [share];

    this.classInstanceService
      .createNewClassInstances(marketplace, list)
      .toPromise()
      .then(() => {
        this.marketplaceClassInstanceDTOs.push(ci);
        this.generateVennData();
      });
  }

  async revokeSharedClassInstance(ci: ClassInstanceDTO, tenant: Tenant) {
    const marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    this.sharedClassInstanceDTOs.forEach(async (shared, index, array) => {
      if (ci.timestamp === shared.timestamp && shared.tenantId === tenant.id) {
        await this.classInstanceService
          .deleteClassInstance(marketplace, shared.id)
          .toPromise();

        let sharedTenants = this.sharedWithMap.get(ci.id);
        sharedTenants = sharedTenants.filter(t => t.id !== tenant.id);
        this.sharedWithMap.set(ci.id, sharedTenants);

        array.splice(index, 1);
      }
    });
  }

  async removeOneFromMarketplace(ci: ClassInstanceDTO) {
    const marketplace = <Marketplace>(
      await this.marketplaceService.findById(ci.marketplaceId).toPromise()
    );

    await this.classInstanceService
      .deleteClassInstance(marketplace, ci.id)
      .toPromise();

    this.marketplaceClassInstanceDTOs = this.marketplaceClassInstanceDTOs.filter(
      mp => {
        return mp.id != ci.id;
      }
    );

    const toDeleteSharedCis = this.sharedClassInstanceDTOs.filter(s => {
      return s.timestamp === ci.timestamp;
    });

    toDeleteSharedCis.forEach(shared => {
      let sharedTenants = this.sharedWithMap.get(ci.id);
      sharedTenants = sharedTenants.filter(t => t.id != shared.tenantId);
      this.sharedWithMap.set(ci.id, sharedTenants);
    });

    toDeleteSharedCis.forEach(async ci => {
      await this.classInstanceService
        .deleteClassInstance(marketplace, ci.id)
        .toPromise();
    });

    this.sharedClassInstanceDTOs = this.sharedClassInstanceDTOs.filter(s => {
      return s.timestamp !== ci.timestamp;
    });

    this.generateVennData();
  }

  // ---- Share to marketplace functions end -----//

  // ---- table functions -----//

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
          " 100%)"
      };
    } else if (this.isLocalRepositoryOnly(ci)) {
      return {
        "background-color": this.colorsOpac.get("localRepository")
      };
    } else if (this.isMarketplaceOnly(ci)) {
      return {
        "background-color": this.colorsOpac.get("marketplace")
      };
    }
  }

  isMarketplaceOnly(ci: ClassInstanceDTO | ClassInstance) {
    return (
      this.marketplaceClassInstanceDTOs.findIndex(c => c.id === ci.id) >= 0 &&
      this.localClassInstanceDTOs.findIndex(c => c.id === ci.id) === -1
    );
  }
  isLocalRepositoryOnly(ci: ClassInstanceDTO | ClassInstance) {
    return (
      this.localClassInstanceDTOs.findIndex(c => c.id === ci.id) >= 0 &&
      this.marketplaceClassInstanceDTOs.findIndex(c => c.id === ci.id) === -1
    );
  }
  isSynced(ci: ClassInstanceDTO | ClassInstance) {
    return (
      this.marketplaceClassInstanceDTOs.findIndex(c => c.id === ci.id) >= 0 &&
      this.localClassInstanceDTOs.findIndex(c => c.id === ci.id) >= 0
    );
  }

  // ---- table functions end -----//

  generateVennData() {
    // intersection of CIs on mp and local repo
    this.mpAndLocalClassInstanceDTOs = this.localClassInstanceDTOs.filter(
      a => this.marketplaceClassInstanceDTOs.map(b => b.id).indexOf(a.id) !== -1
    );
    this.nrMpUnionLr = this.mpAndLocalClassInstanceDTOs.length;

    const data = [];
    data.push(
      {
        sets: ["Freiwilligenpass"],
        value: this.localClassInstanceDTOs.length, // 2,
        displayValue: this.localClassInstanceDTOs.length,
        color: this.colors.get("localRepository"),
        dataLabels: {
          y: -15
        }
      },
      {
        sets: ["Marktplatz"],
        value: this.marketplaceClassInstanceDTOs.length, // 2,
        displayValue: this.marketplaceClassInstanceDTOs.length,
        color: this.colors.get("marketplace")
      },
      {
        sets: ["Freiwilligenpass", "Marktplatz"],
        value: this.nrMpUnionLr, // 1,
        displayValue: this.nrMpUnionLr,
        name: "Synchronisiert",
        dataLabels: {
          y: 15
        },
        color: {
          linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
          stops: [
            [0, this.colorsOpac.get("marketplace")], // start
            [1, this.colorsOpac.get("localRepository")] // end
          ]
        }
      }
    );

    this.vennData = [...data];
    this.chartOptions.series = [
      {
        name: "Anzahl Einträge",
        type: "venn",
        data: this.vennData,
        tooltip: {
          pointFormat: "{point.name}: {point.displayValue}"
        },
        // cursor: "pointer",
        events: {
          click: event => {
            this.onVennClicked(event);
          }
        },
        dataLabels: {
          align: "center",
          allowOverlap: false
        }
      }
    ];
    Highcharts.chart("container", this.chartOptions);
  }
  onVennClicked(event) {}
}

export interface DialogData {
  name: string;
}
