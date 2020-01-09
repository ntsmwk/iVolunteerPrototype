import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTableDataSource } from '@angular/material';
import { Feedback } from '../../_model/feedback';
import { HelpseekerService } from '../../_service/helpseeker.service';
import { Helpseeker } from '../../_model/helpseeker';
import { Marketplace } from '../../_model/marketplace';
import { isNullOrUndefined } from 'util';
import { SelectionModel } from '@angular/cdk/collections';


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

  constructor(
    private helpseekerService: HelpseekerService
  ) { }

  ngOnInit() {

    if (!isNullOrUndefined(this.classInstances)) {
      this.classInstances.sort((a, b) => a.timestamp.valueOf() - b.timestamp.valueOf());

      this.helpseekerService.findAll(this.marketplace).toPromise().then((issuers: Helpseeker[]) => {
        this.issuers = issuers;
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

  getNameForEntry(issuerId: string) {
    const issuer = this.issuers.find((i) => i.id === issuerId);

    if (isNullOrUndefined(issuer)) {
      return '';
    }

    let result = '';

    if (!isNullOrUndefined(issuer.lastname)) {
      if (!isNullOrUndefined(issuer.nickname)) {
        result = result + issuer.nickname;
      } else if (!isNullOrUndefined(issuer.firstname)) {
        result = result + issuer.firstname;
      }
      if (!isNullOrUndefined(issuer.middlename)) {
        result = result + ' ' + issuer.middlename;
      }
      result = result + ' ' + issuer.lastname;
    } else {
      result = result + issuer.username;
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
