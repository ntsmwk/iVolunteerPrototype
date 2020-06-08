import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { isNullOrUndefined } from 'util';
import { UserImagePath } from 'app/main/content/_model/participant';
import { SelectionModel } from '@angular/cdk/collections';


@Component({
  selector: 'app-instance-creation-volunteer-list',
  templateUrl: './volunteer-list.component.html',
  styleUrls: ['./volunteer-list.component.scss'],
  providers: []
})
export class InstanceCreationVolunteerListComponent implements OnInit {

  @Input() helpseeker: Helpseeker;
  @Output() selectedVolunteers: EventEmitter<Volunteer[]> = new EventEmitter();

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;


  datasource = new MatTableDataSource<Volunteer>();
  displayedColumns = ['checkbox', 'image', 'name', 'nickname', 'username'];
  selection = new SelectionModel<Volunteer>(true, []);
  volunteers: Volunteer[];
  userImagePaths: UserImagePath[];


  loaded: boolean;

  constructor(
    private coreVolunteerService: CoreVolunteerService,
  ) { }

  ngOnInit() {
    this.volunteers = [];
    this.datasource = new MatTableDataSource();

    Promise.all([
      this.coreVolunteerService.findAllByTenantId(this.helpseeker.tenantId).toPromise().then((volunteers: Volunteer[]) => {
        this.volunteers = volunteers;

        this.paginator.length = volunteers.length;
        this.datasource.paginator = this.paginator;
        this.datasource.data = volunteers;

        console.log(volunteers);
      }),

      // this.coreImagePathService.getAllImagePaths().toPromise().then((imagePaths: UserImagePath[]) => {
      //   this.userImagePaths = imagePaths;
      //   console.log(this.userImagePaths);
      // })
    ]).then(() => {
      this.loaded = true;
    });
  }

  getImagePathById(id: string) {

    if (isNullOrUndefined(this.userImagePaths)) {
      return '/assets/images/avatars/profile.jpg';
    }

    const ret = this.userImagePaths.find((userImagePath) => {
      return userImagePath.userId === id;
    });

    if (isNullOrUndefined(ret)) {
      return '/assets/images/avatars/profile.jpg';
    } else {
      return ret.imagePath;
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
    this.isAllSelected() ?
      this.selection.clear() :
      this.datasource.data.forEach(row => this.selection.select(row));
  }

  checkboxChanged(event: PointerEvent, row: Volunteer) {
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
