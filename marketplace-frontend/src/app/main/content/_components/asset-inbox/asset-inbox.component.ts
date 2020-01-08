import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTable, MatTableDataSource } from '@angular/material';
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
  displayedColumns = ['checkboxes', 'archetype', 'label', 'issuer', 'date'];
  selection = new SelectionModel<ClassInstance | Feedback>(true, []);



  @Input() classInstances: ClassInstance[];
  @Input() feedbackInstances: Feedback[];
  @Input() marketplace: Marketplace;
  @Output() submit = new EventEmitter();

  allInstances: (ClassInstance | Feedback)[] = [];
  issuers: Helpseeker[] = [];

  constructor(
    private helpseekerService: HelpseekerService
  ) { }

  ngOnInit() {
    console.log('Asset Inbox');
    console.log(this.classInstances);
    this.allInstances.push(...this.classInstances);
    this.allInstances.push(...this.feedbackInstances);
    this.allInstances.sort((a, b) => a.timestamp.valueOf() - b.timestamp.valueOf());

    this.helpseekerService.findAll(this.marketplace).toPromise().then((issuers: Helpseeker[]) => {
      console.log(issuers);
      this.issuers = issuers;
    });

    this.datasource.data = this.allInstances;
    // TESTING
    // this.dataSource.data = [];

  }

  onSubmit() {
    this.submit.emit(this.classInstances);
  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
  }

  getNameForEntry(issuerId: string) {
    const issuer = this.issuers.find((i) => i.id === issuerId);

    if (isNullOrUndefined(issuer)) {
      return '';
    }

    let result: string = '';

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
