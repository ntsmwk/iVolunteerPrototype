import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { isNullOrUndefined } from 'util';

export class BrowseSubDialogData {
  title: string;

  entries: { id: string, label1: string, label2?: string, label3?: string, date: Date }[];
  columnTitles: { id?: string, label1?: string, label2?: string, label3?: string, date?: string };
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
  displayedColumns = ['id', 'label1', 'label2', 'label3', 'date'];
  columnTitles = { id: 'ID', label1: 'Text 1', label2: 'Text 2', label3: 'Text 3', date: 'Datum' };


  loaded: boolean;

  constructor(

  ) {

  }

  ngOnInit() {

    if (!isNullOrUndefined(this.data.displayedColumns)) {
      this.displayedColumns = this.data.displayedColumns;
    }

    if (!isNullOrUndefined(this.data.columnTitles)) {
      this.columnTitles.id = this.data.columnTitles.id;
      this.columnTitles.label1 = this.data.columnTitles.label1;
      this.columnTitles.label2 = this.data.columnTitles.label2;
      this.columnTitles.label3 = this.data.columnTitles.label3;
      this.columnTitles.date = this.data.columnTitles.date;
    }

    this.dataSource.data = this.data.entries;
  }

  handleRowClick(row) {
    this.entryClicked.emit(row.id);
  }


  navigateBack() {
    window.history.back();
  }

}
