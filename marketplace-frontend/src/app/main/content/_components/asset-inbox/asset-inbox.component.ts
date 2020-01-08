import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTable, MatTableDataSource } from '@angular/material';
import { Feedback } from '../../_model/feedback';


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
  @Output() submit = new EventEmitter();

  allInstances: (ClassInstance | Feedback)[] = [];
 
  constructor() {  }
 
  ngOnInit() {
    console.log('Asset Inbox');
    console.log(this.classInstances);
    this.allInstances.push(...this.classInstances);
    this.allInstances.push(...this.feedbackInstances);
    this.allInstances.sort((a, b) => a.timestamp.valueOf() - b.timestamp.valueOf());
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


  navigateBack() {
    window.history.back();
  }

}
