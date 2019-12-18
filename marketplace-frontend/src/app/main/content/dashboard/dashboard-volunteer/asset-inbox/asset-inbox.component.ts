import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'asset-inbox',
  templateUrl: './asset-inbox.component.html',
  styleUrls: ['./asset-inbox.component.scss'],
})
export class AssetInboxComponent implements OnInit {

  dataSourceInbox = new MatTableDataSource<any>();
  dispalyedColumnsInbox = ['data', 'issuer', 'label', 'details'];

  @Output() showInbox: EventEmitter<any> = new EventEmitter();



  constructor() {

  }

  ngOnInit() {


  }

  close() {
    this.showInbox.emit(false);
  }



}


