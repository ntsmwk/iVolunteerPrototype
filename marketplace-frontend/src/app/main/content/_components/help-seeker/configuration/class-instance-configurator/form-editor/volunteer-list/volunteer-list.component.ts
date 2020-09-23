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
import { CoreUserService } from "app/main/content/_service/core-user.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginGuard } from "app/main/content/_guard/login.guard";
import { LoginService } from "app/main/content/_service/login.service";
import { Tenant } from "app/main/content/_model/tenant";
import { UserService } from 'app/main/content/_service/user.service';
import { CoreUserImageService } from 'app/main/content/_service/core-user-image.service';
import { UserImage } from 'app/main/content/_model/image';

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
  volunteerImages: UserImage[];

  loaded: boolean;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  constructor(
    private coreUserService: CoreUserService,
    private userImageService: CoreUserImageService,
    private loginService: LoginService,
    private userService: UserService,
  ) { }

  async ngOnInit() {
    this.volunteers = [];
    this.volunteerImages = [];
    this.datasource = new MatTableDataSource();

    const globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();

    this.tenant = globalInfo.tenants[0];

    Promise.all([
      this.coreUserService.findAllByRoleAndTenantId(this.tenant.id, UserRole.VOLUNTEER).toPromise().then((volunteers: User[]) => {
        this.volunteers = volunteers;
        this.paginator.length = volunteers.length;
        this.datasource.paginator = this.paginator;
        this.datasource.data = volunteers;
      }),
      this.userImageService.findAllByRoleAndTenantId(this.tenant.id, UserRole.VOLUNTEER).toPromise().then((volunteerImages: UserImage[]) => {
        this.volunteerImages = volunteerImages;
      })
    ]).then(() => {
      this.loaded = true;
    });
  }

  getImage(userId: string) {
    const userImage = this.volunteerImages.find((v) => v.userId === userId);
    return this.userImageService.getUserProfileImage(userImage);
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
