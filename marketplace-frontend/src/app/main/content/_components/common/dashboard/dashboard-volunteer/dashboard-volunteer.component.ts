import { Component, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTableDataSource } from "@angular/material/table";
import { ShareDialog } from "./share-dialog/share-dialog.component";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { CoreMarketplaceService } from "../../../../_service/core-marketplace.service";
import { isNullOrUndefined } from "util";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import { ClassInstanceDTO, } from "../../../../_model/meta/Class";
import { CoreUserImagePathService } from "../../../../_service/core-user-imagepath.service";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import { MatSort, MatPaginator, Sort } from "@angular/material";
import { TenantService } from "../../../../_service/core-tenant.service";
import { Volunteer } from "../../../../_model/volunteer";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { ImageService } from "app/main/content/_service/image.service";
import { Tenant } from "app/main/content/_model/tenant";
import { LocalRepositoryService } from 'app/main/content';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { timer } from 'rxjs';

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

  isLoaded: boolean;

  tableDataSource = new MatTableDataSource<ClassInstanceDTO>();
  selectedTenants: Tenant[] = [];

  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
    "syncButton"
  ];

  issuerIds: string[] = [];
  issuers: Helpseeker[] = [];
  userImagePaths: any[] = [];
  image;

  tenants: Tenant[] = [];

  marketplaceClassInstances: ClassInstanceDTO[] = [];
  localClassInstances: ClassInstanceDTO[] = [];
  filteredClassInstances: ClassInstanceDTO[] = [];

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

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
    private imageService: ImageService,
    private router: Router
  ) { }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.setVolunteerImage();

    this.tenants = <Tenant[]>(
      await this.tenantService.findByVolunteerId(this.volunteer.id).toPromise()
    );

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(this.volunteer);
    if (this.isLocalRepositoryConnected) {
      let marketplaces = <Marketplace[]>(
        await this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      );
      this.marketplace = marketplaces[0];

      this.marketplaceClassInstances = <ClassInstanceDTO[]>(
        await this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK', this.volunteer.id, this.volunteer.subscribedTenants).toPromise()
      );

      this.marketplaceClassInstances.forEach((ci, index, object) => {
        if (ci.duration === null) {
          object.splice(index, 1);
        }
      });

      this.issuerIds.push(...this.marketplaceClassInstances.map(t => t.issuerId));
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

      this.localClassInstances = <ClassInstanceDTO[]>(
        await this.localRepositoryService.findClassInstancesByVolunteer(this.volunteer).toPromise());

      // concat local and mp and remove dublicates
      this.filteredClassInstances = this.localClassInstances.concat(
        this.marketplaceClassInstances.filter(mp => this.localClassInstances.map(lo => lo.id).indexOf(mp.id) < 0));

      this.filteredClassInstances = this.filteredClassInstances.sort(
        (a, b) => b.dateFrom.valueOf() - a.dateFrom.valueOf()
      );

      this.tableDataSource.data = this.filteredClassInstances;
      this.paginator.length = this.filteredClassInstances.length;
      this.tableDataSource.paginator = this.paginator;
    }
  }

  setVolunteerImage() {
    let objectURL = "data:image/png;base64," + this.volunteer.image;
    this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  navigateToTenantOverview() {
    this.router.navigate(["/main/dashboard/tenants"]);
  }

  getImagePathById(id: string) {
    const ret = this.userImagePaths.find((userImagePath) => {
      return userImagePath.userId === id;
    });

    if (isNullOrUndefined(ret)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return ret.imagePath;
    }
  }

  getIssuerName(issuerId: string) {
    let person: Helpseeker = this.issuers.find((i) => i.id === issuerId);

    if (!isNullOrUndefined(person)) {
      let tenant = this.tenants.find(t => t.id === person.tenantId);
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

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  triggerStoreDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: "700px",
      height: "255px",
      data: { name: "store" },
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      this.toggleShareInRep();
    });
  }

  toggleShareInRep() { }


  tenantSelectionChanged(selectedTenants: Tenant[]) {
    this.selectedTenants = selectedTenants;

    // concat local and mp and remove dublicates
    this.filteredClassInstances = this.localClassInstances.concat(
      this.marketplaceClassInstances.filter(mp => this.localClassInstances.map(lo => lo.id).indexOf(mp.id) < 0));

    this.filteredClassInstances = this.filteredClassInstances.filter(ci => {
      return this.selectedTenants.findIndex(t => t.id === ci.tenantId) >= 0;
    });

    this.filteredClassInstances = this.filteredClassInstances.sort(
      (a, b) => b.dateFrom.valueOf() - a.dateFrom.valueOf()
    );

    this.tableDataSource.data = this.filteredClassInstances;
    this.paginator.length = this.filteredClassInstances.length;
    this.tableDataSource.paginator = this.paginator;
  }

  //---- Local Repository functions -----//

  inLocalRepository(classInstance: ClassInstanceDTO) {
    return this.localClassInstances.findIndex(t => t.id === classInstance.id) >= 0;
  }

  async syncOneToLocalRepository(classInstance: ClassInstanceDTO) {
    // let ci = <ClassInstance>await
    //   this.classInstanceService.getClassInstanceById(this.marketplace, classInstanceDTO.id, classInstanceDTO.tenantId).toPromise();
    // await this.localRepositoryService.synchronizeClassInstance(this.volunteer, ci).toPromise();
    // this.localClassInstances.push(ci);

    this.localClassInstances = <ClassInstanceDTO[]>await
      this.localRepositoryService.synchronizeSingleClassInstance(this.volunteer, classInstance).toPromise();
  }

  async removeOneFromLocalRepository(classInstance: ClassInstanceDTO) {
    this.localClassInstances = <ClassInstanceDTO[]>await
      this.localRepositoryService.removeSingleClassInstance(this.volunteer, classInstance).toPromise();


  }

  async syncAllToLocalRepository() {
    let missingClassInstances: ClassInstanceDTO[] = [];
    // TODO
    // sync only filtered selection
      //this.filteredClassInstances.forEach(ci => {

    // sync all
    this.marketplaceClassInstances.forEach(ci => {
      if (!(this.localClassInstances.findIndex(t => t.id === ci.id) >= 0)) {
        // let ci = <ClassInstance>await
        //   this.classInstanceService.getClassInstanceById(this.marketplace, ci.id, ci.tenantId).toPromise();

        missingClassInstances.push(ci);
      }
    });

    console.error(missingClassInstances);

    this.localClassInstances = <ClassInstanceDTO[]>await
      this.localRepositoryService.synchronizeClassInstances(this.volunteer, missingClassInstances).toPromise();
  }


  async removeAllFromLocalRepository() {
    let toRemoveClassInstances: ClassInstanceDTO[] = [];

    this.filteredClassInstances.forEach(ci => {
      if (this.localClassInstances.findIndex(t => t.id === ci.id) >= 0) {
        toRemoveClassInstances.push(ci);
      }
    });

    this.localClassInstances = <ClassInstanceDTO[]>await
      this.localRepositoryService.removeClassInstances(this.volunteer, toRemoveClassInstances).toPromise();
  }


  sortData(sort: Sort) {
    this.tableDataSource.data = this.tableDataSource.data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'issuer': return this.compare(this.getIssuerName(a.issuerId), this.getIssuerName(b.issuerId), isAsc);
        case 'taskName': return this.compare(a.name, b.name, isAsc);
        case 'taskType1': return this.compare(a.taskType1, b.taskType1, isAsc);
        case 'date': return this.compare(a.dateFrom, b.dateFrom, isAsc);
        default: return 0;
      }
    });
  }

  compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }


}

export interface DialogData {
  name: string;
}

