import { Component, Input, OnInit, Output, EventEmitter }  from '@angular/core';
import { ClassInstance } from '../../_model/meta/Class';
import { MatTable, MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-asset-inbox',
  templateUrl: './asset-inbox.component.html',
  styleUrls: ['./asset-inbox.component.scss']
})
export class AssetInboxComponent implements OnInit {
 
  output = '';
  submitPressed: boolean;


  dataSource = new MatTableDataSource<ClassInstance>();
  displayedColumns = ['archetype', 'label', 'issuer', 'date'];


  @Input() classInstances: ClassInstance[];

 
  constructor() {  }
 
  ngOnInit() {
    console.log("Asset Inbox");
    console.log(this.classInstances);
    this.dataSource.data = this.classInstances;
    this.dataSource.data = [];
    
  }
 
  onSubmit() {
   
  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
  }


  navigateBack() {
    window.history.back();
  }

}