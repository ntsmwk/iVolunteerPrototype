import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTableDataSource } from '@angular/material';
import { Feedback } from '../../_model/feedback';
import { HelpseekerService } from '../../_service/helpseeker.service';
import { Helpseeker } from '../../_model/helpseeker';
import { Marketplace } from '../../_model/marketplace';
import { isNullOrUndefined } from 'util';
import { SelectionModel } from '@angular/cdk/collections';
import { Volunteer } from '../../_model/volunteer';
import { VolunteerService } from '../../_service/volunteer.service';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';


@Component({
  selector: 'app-asset-inbox',
  templateUrl: './asset-inbox.component.html',
  styleUrls: ['./asset-inbox.component.scss']
})
export class AssetInboxComponent implements OnInit {

  output = '';
  submitPressed: boolean;

  datasource = new MatTableDataSource<ClassInstance | Feedback>();
  displayedColumns = ['checkboxes', 'archetype', 'label', 'issuer', 'user', 'date'];
  selection = new SelectionModel<ClassInstance | Feedback>(true, []);

  @Input() classInstances: ClassInstance[];
  @Input() marketplace: Marketplace;
  @Output() submit = new EventEmitter();

  issuers: Helpseeker[] = [];
  volunteers: Volunteer[] = [];

  constructor(
    private helpseekerService: HelpseekerService,
    private volunteerService: CoreVolunteerService
  ) { }

  ngOnInit() {

    if (!isNullOrUndefined(this.classInstances)) {
      this.classInstances.sort((a, b) => a.timestamp.valueOf() - b.timestamp.valueOf());

      this.helpseekerService.findAll(this.marketplace).toPromise().then((issuers: Helpseeker[]) => {
        this.issuers = issuers;
      });
      
      this.volunteerService.findAll().toPromise().then((volunteers: Volunteer[]) => {
        this.volunteers = volunteers;
      });
    } else {
      this.classInstances = [];
    }
    this.datasource.data = this.classInstances;
  }

  onSubmit() {
    console.log(this.selection);
    if (!this.selection.isEmpty()) {
      this.submit.emit(this.selection.selected);
    }
  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
  }

  getNameForEntry(personId: string, type: string) {
    let person: Volunteer | Helpseeker;
    if (type === 'issuer') {
    person = this.issuers.find((i) => i.id === personId);
    } else {
      person = this.volunteers.find((i) => i.id === personId);
    }
    if (isNullOrUndefined(person)) {
      return '';
    }

    let result = '';

    if (!isNullOrUndefined(person.lastname)) {
      if (!isNullOrUndefined(person.nickname)) {
        result = result + person.nickname;
      } else if (!isNullOrUndefined(person.firstname)) {
        result = result + person.firstname;
      }
      if (!isNullOrUndefined(person.middlename)) {
        result = result + ' ' + person.middlename;
      }
      result = result + ' ' + person.lastname;
    } else {
      result = result + person.username;
    }

    return result;
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
