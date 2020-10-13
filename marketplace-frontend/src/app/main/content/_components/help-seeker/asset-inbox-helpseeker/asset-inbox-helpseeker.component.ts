import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Marketplace } from "../../../_model/marketplace";
import { User, UserRole } from "../../../_model/user";
import { ClassInstanceDTO, ClassArchetype } from "../../../_model/meta/class";
import { ClassInstanceService } from "../../../_service/meta/core/class/class-instance.service";
import { isNullOrUndefined } from "util";
import { LoginService } from "../../../_service/login.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";
import { SelectionModel } from "@angular/cdk/collections";
import { UserService } from "app/main/content/_service/user.service";
import { MatTableDataSource } from "@angular/material";

@Component({
  selector: "asset-inbox-helpseeker",
  templateUrl: "./asset-inbox-helpseeker.component.html",
  styleUrls: ["./asset-inbox-helpseeker.component.scss"]
})
export class AssetInboxHelpseekerComponent implements OnInit {
  public marketplaces = new Array<Marketplace>();
  marketplace: Marketplace;
  user: User;
  tenant: Tenant;
  classInstanceDTOs: ClassInstanceDTO[];
  isLoaded: boolean;
  userImagePaths: any[];
  selection = new SelectionModel<ClassInstanceDTO>(true, []);
  datasource = new MatTableDataSource<ClassInstanceDTO>();

  issuers: User[] = [];
  volunteers: User[] = [];

  displayedColumns = ["checkboxes", "label", "archetype", "user", "date"];

  constructor(
    private loginService: LoginService,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private userService: UserService
  ) {}

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.user = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.tenant = globalInfo.tenants[0];

    this.loadInboxEntries();
  }

  loadInboxEntries() {
    this.classInstanceService
      .getClassInstancesInIssuerInbox(this.marketplace, this.tenant.id)
      .toPromise()
      .then((ret: ClassInstanceDTO[]) => {
        this.classInstanceDTOs = ret;
        this.isLoaded = true;
        console.error(this.classInstanceDTOs);
        this.sortClassInstances();
        this.loadUsers();
      });
  }

  sortClassInstances() {
    this.classInstanceDTOs.sort(
      (a, b) => a.blockchainDate.valueOf() - b.blockchainDate.valueOf()
    );
  }

  loadUsers() {
    if (!isNullOrUndefined(this.classInstanceDTOs)) {
      Promise.all([
        this.userService
          .findAllByRole(this.marketplace, UserRole.HELP_SEEKER)
          .toPromise()
          .then((issuers: User[]) => {
            this.issuers = issuers;
          }),

        this.userService
          .findAllByRole(this.marketplace, UserRole.VOLUNTEER)
          .toPromise()
          .then((volunteers: User[]) => {
            this.volunteers = volunteers;
          })
      ]);
    } else {
      this.classInstanceDTOs = [];
    }

    this.datasource.data = this.classInstanceDTOs;
  }

  onAssetInboxSubmit() {
    this.classInstanceService
      .issueClassInstance(
        this.marketplace,
        this.classInstanceDTOs.map(c => c.id)
      )
      .toPromise()
      .then(() => {
        this.router.navigate(["main/helpseeker/asset-inbox/confirm"], {
          state: {
            instances: this.classInstanceDTOs,
            marketplace: this.marketplace,
            participant: this.user
          }
        });
      });
  }

  getDateString(dateNumber: number) {
    const date = new Date(dateNumber);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  }

  getNameForEntry(personId: string, type: string) {
    let person: User;
    if (type === "issuer") {
      person = this.issuers.find(i => i.id === personId);
    } else {
      person = this.volunteers.find(i => i.id === personId);
    }
    if (isNullOrUndefined(person)) {
      return "";
    }

    return person.firstname + " " + person.lastname;
  }

  getIssuerPositionForEntry(personId: string) {
    const user = this.issuers.find(p => p.id === personId);

    if (
      isNullOrUndefined(user) ||
      isNullOrUndefined(user.organizationPosition)
    ) {
      return "";
    } else {
      return "(" + user.organizationPosition + ")";
    }
  }

  findNameProperty(entry: ClassInstanceDTO) {
    if (isNullOrUndefined(entry.name)) {
      return "";
    } else {
      return entry.name;
    }
  }

  getImagePathById(id: string) {
    if (isNullOrUndefined(this.userImagePaths)) {
      return "/assets/images/avatars/profile.jpg";
    }

    const ret = this.userImagePaths.find(userImagePath => {
      return userImagePath.userId === id;
    });

    if (isNullOrUndefined(ret)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return ret.imagePath;
    }
  }

  getArchetypeIcon(entry: ClassInstanceDTO) {
    if (isNullOrUndefined(entry.imagePath)) {
      if (entry.classArchetype === ClassArchetype.COMPETENCE) {
        return "/assets/competence.jpg";
      } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
        return "/assets/icons/award.svg";
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

  getArchetypeName(entry: ClassInstanceDTO) {
    if (entry.classArchetype === ClassArchetype.COMPETENCE) {
      return "Kompetenz";
    } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
      return "Verdienst";
    } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
      return "Funktion";
    } else if (entry.classArchetype === ClassArchetype.TASK) {
      return "Tätigkeit";
    } else {
      return "";
    }
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.datasource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.datasource.data.forEach(row => this.selection.select(row));
  }

  navigateBack() {
    window.history.back();
  }
}
