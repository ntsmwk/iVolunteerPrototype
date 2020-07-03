import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatTab, MatTableDataSource } from '@angular/material';

export class ClassBrowseSubDialogData {
  title: string;

  entries: { id: string, name: string, date: Date }[];
  sourceReference: 'PRODUCER' | 'CONSUMER';

  marketplace: Marketplace;
}

@Component({
  selector: 'browse-class-sub-dialog',
  templateUrl: './browse-sub-dialog.component.html',
  styleUrls: ['./browse-sub-dialog.component.scss']
})
export class BrowseClassSubDialogComponent implements OnInit {

  @Input() data: ClassBrowseSubDialogData;
  @Input() browseMode: boolean;
  @Output() subDialogReturn: EventEmitter<{ cancelled: boolean, entryId: string, sourceReference: 'PRODUCER' | 'CONSUMER' }>
    = new EventEmitter<{ cancelled: boolean, entryId: string, sourceReference: 'PRODUCER' | 'CONSUMER' }>();

  datasource: MatTableDataSource<{ id: string, name: string, date: Date }> = new MatTableDataSource();

  constructor(
  ) {
  }

  ngOnInit() {
    this.datasource.data = this.data.entries;
  }

  handleRowClick(entry: any) {
    this.subDialogReturn.emit({ cancelled: false, entryId: entry.id, sourceReference: this.data.sourceReference });
  }

  handleBackClick() {
    this.subDialogReturn.emit({ cancelled: true, entryId: undefined, sourceReference: this.data.sourceReference });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }


}
