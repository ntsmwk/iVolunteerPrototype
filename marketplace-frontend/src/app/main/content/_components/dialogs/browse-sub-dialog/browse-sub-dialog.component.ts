import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { isNullOrUndefined } from 'util';

export class BrowseSubDialogData {
  title: string;

  entries: { id: string, label1: string, label2?: string, label3?: string, date: Date }[];
  columnTitles: string[];
  displayedColumns: string[];
}

@Component({
  selector: 'browse-sub-dialog',
  templateUrl: './browse-sub-dialog.component.html',
  styleUrls: ['./browse-sub-dialog.component.scss']
})
export class BrowseSubDialogComponent implements OnInit {

  @Input() data: BrowseSubDialogData;
  @Output() entryClicked: EventEmitter<any> = new EventEmitter<any>();

  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  displayedColumns = ['label1', 'label2', 'label3', 'date'];
  columnTitles = ['Text 1', 'Text 2', 'Text 3', 'Datum'];

  loaded: boolean;

  constructor(

  ) {

  }

  ngOnInit() {

    if (!isNullOrUndefined(this.data.displayedColumns)) {
      this.displayedColumns = this.data.displayedColumns;
    }

    if (!isNullOrUndefined(this.data.columnTitles)) {
      this.columnTitles = this.data.columnTitles;
    }



    console.log(this.data);
  }


  navigateBack() {
    window.history.back();
  }

}
