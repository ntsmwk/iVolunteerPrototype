import { Component, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatTableDataSource } from "@angular/material/table";
import { ShareDialog } from "./share-dialog/share-dialog.component";
import { CoreVolunteerService } from "../../../../_service/core-volunteer.service";
import { LoginService } from "../../../../_service/login.service";
import { Participant } from "../../../../_model/participant";
import { CoreMarketplaceService } from "../../../../_service/core-marketplace.service";
import { isNullOrUndefined } from "util";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassInstanceService } from "../../../../_service/meta/core/class/class-instance.service";
import {
  ClassInstance,
  ClassArchetype,
  ClassInstanceDTO,
} from "../../../../_model/meta/Class";
import { CoreUserImagePathService } from "../../../../_service/core-user-imagepath.service";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import { MatSort, MatPaginator } from "@angular/material";
import { TenantService } from "../../../../_service/core-tenant.service";
import { Volunteer } from "../../../../_model/volunteer";
import { DomSanitizer } from "@angular/platform-browser";
import { NavigationEnd, Router } from "@angular/router";
import { ImageService } from "app/main/content/_service/image.service";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "dashboard-volunteer",
  templateUrl: "./dashboard-volunteer.component.html",
  styleUrls: ["./dashboard-volunteer.component.scss"],
})
export class DashboardVolunteerComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  isLoaded: boolean;

  dataSourceComp = new MatTableDataSource<ClassInstanceDTO>();
  dataSourceFeedback = new MatTableDataSource<ClassInstanceDTO>();
  dataSourceRepository = new MatTableDataSource<ClassInstanceDTO>();

  private displayedColumnsRepository: string[] = [
    "issuer",
    "taskName",
    "taskType1",
    "date",
  ];

  issuerIds: string[] = [];
  issuers: Participant[] = [];
  userImagePaths: any[] = [];
  image;

  tenants: Tenant[] = [];

  private classInstances: ClassInstanceDTO[] = [];

  constructor(
    public dialog: MatDialog,
    private coreVolunteerService: CoreVolunteerService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private classInstanceService: ClassInstanceService,
    private userImagePathService: CoreUserImagePathService,
    private tenantService: TenantService,
    private sanitizer: DomSanitizer,
    private imageService: ImageService,
    private router: Router
  ) {}

  async ngOnInit() {
    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.setVolunteerImage();

    this.marketplace = (<Marketplace[]>(
      await this.coreVolunteerService
        .findRegisteredMarketplaces(this.volunteer.id)
        .toPromise()
    )).filter((m) => (m.name = "Marketplace 1"))[0];
    this.tenants = <Tenant[]>(
      await this.tenantService.findByVolunteerId(this.volunteer.id).toPromise()
    );
    this.loadDashboardContent();
  }

  private setVolunteerImage() {
    let objectURL = "data:image/png;base64," + this.volunteer.image;
    this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  async loadDashboardContent() {
    if (this.marketplace != null && this.tenants.length > 0) {
      this.classInstances = <ClassInstanceDTO[]>(
        await this.classInstanceService
          .getClassInstancesInUserRepository(
            this.marketplace,
            this.volunteer.id,
            this.volunteer.subscribedTenants
          )
          .toPromise()
      );
      this.classInstances = this.classInstances.sort(
        (a, b) => b.blockchainDate.valueOf() - a.blockchainDate.valueOf()
      );

      this.dataSourceRepository.data = this.classInstances;
      this.paginator.length = this.classInstances.length;
      this.dataSourceRepository.paginator = this.paginator;
      this.issuerIds.push(...this.classInstances.map((t) => t.issuerId));

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
      this.isLoaded = true;
    }
  }

  getDateString(dateNumber: number) {
    const date = new Date(dateNumber);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
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

  getIssuerById(id: string) {
    return this.issuers.find((issuer) => {
      return issuer.id === id;
    });
  }

  getIssuerName(issuerId: string) {
    const person = this.issuers.find((i) => i.id === issuerId);

    let result = "";

    if (isNullOrUndefined(person)) {
      return result;
    }

    result = person.firstname + " " + person.lastname;
    return result;
  }

  getIssuerPosition(issuerId: string) {
    const person = this.issuers.find((i) => i.id === issuerId);
    if (isNullOrUndefined(person) || isNullOrUndefined(person.position)) {
      return "";
    } else {
      return "(" + person.position + ")";
    }
  }

  findNameProperty(entry: ClassInstance) {
    if (isNullOrUndefined(entry.properties)) {
      return "";
    }

    let name = entry.properties.find((p) => p.id === "name");

    if (isNullOrUndefined(name)) {
      name = entry.properties.find((p) => p.name === "taskName");
    }

    if (
      isNullOrUndefined(name) ||
      isNullOrUndefined(name.values) ||
      isNullOrUndefined(name.values[0])
    ) {
      return "";
    } else {
      return ": " + name.values[0];
    }
  }

  getArchetypeIcon(entry: ClassInstance) {
    if (isNullOrUndefined(entry.imagePath)) {
      if (entry.classArchetype === ClassArchetype.COMPETENCE) {
        return "/assets/competence.jpg";
      } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
        return "/assets/icons/achievements_black.png";
      } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
        return "/assets/TODO";
      } else if (entry.classArchetype === ClassArchetype.TASK) {
        return "/assets/cog.png";
      } else {
        return "/assets/NONE";
      }
    } else {
      return entry.imagePath;
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

  toggleShareInRep() {}
}

export interface DialogData {
  name: string;
}
