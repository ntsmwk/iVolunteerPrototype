import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTable, MatTableDataSource } from '@angular/material';
import { Feedback } from '../../_model/feedback';
import { HelpseekerService } from '../../_service/helpseeker.service';
import { Helpseeker } from '../../_model/helpseeker';
import { Marketplace } from '../../_model/marketplace';
import { isNullOrUndefined } from 'util';


@Component({
  selector: 'app-asset-inbox',
  templateUrl: './asset-inbox.component.html',
  styleUrls: ['./asset-inbox.component.scss']
})
export class AssetInboxComponent implements OnInit {
 
  output = '';
  submitPressed: boolean;


  dataSource = new MatTableDataSource<ClassInstance | Feedback>();
  displayedColumns = ['archetype', 'label', 'issuer', 'date'];


  @Input() classInstances: ClassInstance[];
  @Input() feedbackInstances: Feedback[];
  @Input() marketplace: Marketplace;
  @Output() submit = new EventEmitter();

  allInstances: (ClassInstance | Feedback)[] = [];
  issuers: Helpseeker[] = [];
 
  constructor(
    private helpseekerService: HelpseekerService
  ) {  }
 
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

    this.dataSource.data = this.allInstances;
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


  navigateBack() {
    window.history.back();
  }

}
