import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ViewChild,
} from "@angular/core";
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";
import { MatTableDataSource, MatPaginator } from "@angular/material";
import { isNullOrUndefined } from "util";
import { User, UserRole } from "app/main/content/_model/user";
import { SelectionModel } from "@angular/cdk/collections";
import { ImageService } from "app/main/content/_service/image.service";

@Component({
  selector: "app-instance-creation-volunteer-list",
  templateUrl: "./volunteer-list.component.html",
  styleUrls: ["./volunteer-list.component.scss"],
  providers: [],
})
export class InstanceCreationVolunteerListComponent implements OnInit {
  @Input() helpseeker: User;
  @Output() selectedVolunteers: EventEmitter<User[]> = new EventEmitter();

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  datasource = new MatTableDataSource<User>();
  displayedColumns = ["checkbox", "image", "name", "nickname", "username"];
  selection = new SelectionModel<User>(true, []);
  volunteers: User[];

  loaded: boolean;

  constructor(
    private coreVolunteerService: CoreVolunteerService,
    private imageService: ImageService
  ) {}

  ngOnInit() {
    this.volunteers = [];
    this.datasource = new MatTableDataSource();

    Promise.all([
      this.coreVolunteerService
        .findAllByTenantId(
          this.helpseeker.subscribedTenants.find(
            (t) => t.role === UserRole.HELP_SEEKER
          ).tenant.id
        )
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

    if (isNullOrUndefined(user)) {
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
