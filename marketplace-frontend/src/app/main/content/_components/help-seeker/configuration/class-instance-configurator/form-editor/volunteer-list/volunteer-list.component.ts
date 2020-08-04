import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ViewChild,
} from "@angular/core";
import { MatTableDataSource, MatPaginator } from "@angular/material";
import { isNullOrUndefined } from "util";
import { User, UserRole } from "app/main/content/_model/user";
import { SelectionModel } from "@angular/cdk/collections";
import { ImageService } from "app/main/content/_service/image.service";
import { CoreUserService } from "app/main/content/_service/core-user.serivce";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginGuard } from "app/main/content/_guard/login.guard";
import { LoginService } from "app/main/content/_service/login.service";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "app-instance-creation-volunteer-list",
  templateUrl: "./volunteer-list.component.html",
  styleUrls: ["./volunteer-list.component.scss"],
  providers: [],
})
export class InstanceCreationVolunteerListComponent implements OnInit {
  @Input() tenantAdmin: User;
  @Output() selectedVolunteers: EventEmitter<User[]> = new EventEmitter();

  tenant: Tenant;
  datasource = new MatTableDataSource<User>();
  displayedColumns = ["checkbox", "image", "name", "nickname", "username"];
  selection = new SelectionModel<User>(true, []);
  volunteers: User[];

  loaded: boolean;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  constructor(
    private coreUserService: CoreUserService,
    private imageService: ImageService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    this.volunteers = [];
    this.datasource = new MatTableDataSource();

    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];

    Promise.all([
      this.coreUserService
        .findAllByRoleAndTenantId(this.tenant.id, UserRole.VOLUNTEER)
        .toPromise()
        .then((volunteers: User[]) => {
          this.volunteers = volunteers;
          this.paginator.length = volunteers.length;
          this.datasource.paginator = this.paginator;
          this.datasource.data = volunteers;

          console.log(volunteers);
        }),
    ]).then(() => {
      this.loaded = true;
    });
  }

  getImage(userId: string) {
    let user = this.volunteers.find((v) => v.id === userId);
    if (isNullOrUndefined(user.image)) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return this.imageService.getImgSourceFromBytes(user.image);
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
      : this.datasource.data.forEach((row) => this.selection.select(row));
  }

  checkboxChanged(event: PointerEvent, row: User) {
    if (event) {
      this.selection.toggle(row);
    }
    this.selectedVolunteers.emit(this.selection.selected);
  }

  printAnything(anything: any) {
    console.log(anything);
  }

  navigateBack() {
    window.history.back();
  }
}
