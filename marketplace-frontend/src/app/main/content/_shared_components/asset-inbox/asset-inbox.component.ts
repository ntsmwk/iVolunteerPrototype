import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { ClassInstanceDTO, ClassArchetype } from '../../_model/meta/class';
import { Feedback } from '../../_model/feedback';
import { SelectionModel } from '@angular/cdk/collections';
import { Marketplace } from '../../_model/marketplace';
import { Helpseeker } from '../../_model/helpseeker';
import { Volunteer } from '../../_model/volunteer';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { CoreUserImagePathService } from '../../_service/core-user-imagepath.service';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-asset-inbox',
  templateUrl: './asset-inbox.component.html',
  styleUrls: ['./asset-inbox.component.scss']
})
export class AssetInboxComponent implements OnInit {

  output = '';
  submitPressed: boolean;

  datasource = new MatTableDataSource<ClassInstanceDTO | Feedback>();
  displayedColumns;
  displayedColumnsVolunteer = ['checkboxes', 'label', 'archetype', 'issuer', 'date'];
  displayedColumnsHelpseeker = ['checkboxes', 'label', 'archetype', 'user', 'date'];

  selection = new SelectionModel<ClassInstanceDTO | Feedback>(true, []);

  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() marketplace: Marketplace;
  @Input() inboxOwner: string;
  @Output() submit = new EventEmitter();

  issuers: Helpseeker[] = [];
  volunteers: Volunteer[] = [];
  userImagePaths: any[];

  constructor(
    private helpseekerService: CoreHelpSeekerService,
    private volunteerService: CoreVolunteerService,
    private userImagePathService: CoreUserImagePathService

  ) { }

  ngOnInit() {

    if (!isNullOrUndefined(this.classInstanceDTOs)) {
      this.classInstanceDTOs.sort((a, b) => a.blockchainDate.valueOf() - b.blockchainDate.valueOf());

      Promise.all([
        this.helpseekerService.findAll().toPromise().then((issuers: Helpseeker[]) => {
          this.issuers = issuers;
        }),

        this.volunteerService.findAll().toPromise().then((volunteers: Volunteer[]) => {
          this.volunteers = volunteers;
        })
      ]).then(() => {
        this.fetchImagePaths();
      });
    } else {
      this.fetchImagePaths();
      this.classInstanceDTOs = [];
    }

    if (this.inboxOwner === 'volunteer') {
      this.displayedColumns = this.displayedColumnsVolunteer;
    } else {
      this.displayedColumns = this.displayedColumnsHelpseeker;
    }
    this.datasource.data = this.classInstanceDTOs;

  }

  fetchImagePaths() {
    const users: (Volunteer | Helpseeker)[] = [];
    users.push(...this.issuers);
    users.push(...this.volunteers);
    this.userImagePathService.getImagePathsById(users.map(u => u.id)).toPromise().then((ret: any) => {
      console.log(ret);
      this.userImagePaths = ret;
    });
  }


  onSubmit() {
    console.log(this.selection);
    if (!this.selection.isEmpty() || this.inboxOwner === 'helpseeker-dashboard') {
      this.submit.emit(this.selection.selected);
    }
  }

  getDateString(dateNumber: number) {
    const date = new Date(dateNumber);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }

  getNameForEntry(personId: string, type: string) {
    let person: Volunteer | Helpseeker;
    if (type === 'issuer') {
      person = this.issuers.find(i => i.id === personId);
    } else {
      person = this.volunteers.find(i => i.id === personId);
    }
    if (isNullOrUndefined(person)) {
      return '';
    }

    return person.firstname + ' ' + person.lastname;
  }

  getIssuerPositionForEntry(personId: string) {
    const helpseeker = this.issuers.find(p => p.id === personId);

    if (isNullOrUndefined(helpseeker) || isNullOrUndefined(helpseeker.position)) {
      return '';
    } else {
      return '(' + helpseeker.position + ')';
    }

  }

  findNameProperty(entry: ClassInstanceDTO) {
    if (isNullOrUndefined(entry.name)) {
      return '';
    } else {
      return entry.name;
    }
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

  getArchetypeIcon(entry: ClassInstanceDTO) {
    if (isNullOrUndefined(entry.imagePath)) {

      if (entry.classArchetype === ClassArchetype.COMPETENCE) {
        return '/assets/competence.jpg';
      } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
        return '/assets/icons/award.svg';
      } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
        return '/assets/TODO';
      } else if (entry.classArchetype === ClassArchetype.TASK) {
        return '/assets/cog.png';
      } else {
        return '/assets/NONE';
      }
    } else {
      return entry.imagePath;
    }

  }

  getArchetypeName(entry: ClassInstanceDTO) {
    if (entry.classArchetype === ClassArchetype.COMPETENCE) {
      return 'Kompetenz';
    } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
      return 'Verdienst';
    } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
      return 'Funktion';
    } else if (entry.classArchetype === ClassArchetype.TASK) {
      return 'Tätigkeit';
    } else {
      return '';
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



  navigateBack() {
    window.history.back();
  }

}