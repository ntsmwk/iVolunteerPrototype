import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ViewChild
} from "@angular/core";
import { MatTableDataSource, MatPaginator } from "@angular/material";
import { isNullOrUndefined } from "util";
import { User, UserRole } from "app/main/content/_model/user";
import { SelectionModel } from "@angular/cdk/collections";
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginGuard } from "app/main/content/_guard/login.guard";
import { LoginService } from "app/main/content/_service/login.service";
import { Tenant } from "app/main/content/_model/tenant";
import { UserService } from "app/main/content/_service/user.service";

@Component({
  selector: "app-instance-creation-volunteer-list",
  templateUrl: "./volunteer-list.component.html",
  styleUrls: ["./volunteer-list.component.scss"],
  providers: []
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
    private loginService: LoginService,
    private userService: CoreUserService
  ) {}

  async ngOnInit() {
    this.volunteers = [];
    this.datasource = new MatTableDataSource();

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.tenant = globalInfo.tenants[0];

    this.coreUserService
      .findAllByRoleAndTenantId(this.tenant.id, UserRole.VOLUNTEER)
      .toPromise()
      .then((volunteers: User[]) => {
        this.volunteers = volunteers;
        this.paginator.length = volunteers.length;
        this.datasource.paginator = this.paginator;
        this.datasource.data = volunteers;
        this.loaded = true;
      });
  }

  getProfile(user: User) {
    return this.userService.getUserProfileImage(user);
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
