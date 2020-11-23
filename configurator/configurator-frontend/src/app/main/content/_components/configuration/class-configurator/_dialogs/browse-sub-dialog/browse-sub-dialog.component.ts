import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { isNullOrUndefined } from 'util';

export class ClassBrowseSubDialogData {
  title: string;
  entries: { id: string; name: string; date: Date }[];
  sourceReference: 'LEFT' | 'RIGHT';
}

@Component({
  selector: "browse-class-sub-dialog",
  templateUrl: './browse-sub-dialog.component.html',
  styleUrls: ['./browse-sub-dialog.component.scss']
})
export class BrowseClassSubDialogComponent implements OnInit {
  @Input() data: ClassBrowseSubDialogData;
  @Input() browseMode: boolean;
  @Output() subDialogReturn: EventEmitter<{ cancelled: boolean, entryId: string, sourceReference: 'LEFT' | 'RIGHT'; }> = new EventEmitter();

  datasource: MatTableDataSource<{ id: string, name: string, date: Date }> = new MatTableDataSource();

  currentSortKey: 'name' | 'date';
  currentSortType: 'az' | 'za' = 'az';

  currentFilter: string;

  constructor() { }

  ngOnInit() {
    this.datasource.data = this.data.entries;
    this.sortClicked('date');
  }

  handleRowClick(entry: any) {
    this.subDialogReturn.emit({
      cancelled: false,
      entryId: entry.id,
      sourceReference: this.data.sourceReference
    });
  }

  handleBackClick() {
    this.subDialogReturn.emit({
      cancelled: true,
      entryId: undefined,
      sourceReference: this.data.sourceReference
    });
  }

  applyFilter(event: Event) {
    this.currentFilter = (event.target as HTMLInputElement).value;
    this.datasource.filter = this.currentFilter.trim().toLowerCase();
  }

  sortClicked(sortKey: 'name' | 'date') {
    if (this.currentSortKey === sortKey) {
      this.switchSortType();
    } else {
      this.currentSortType = 'az';
    }
    if (sortKey === 'date') {
      this.datasource.data = this.data.entries.sort(
        (a, b) => b.date.valueOf() - a.date.valueOf()
      );
    }
    if (sortKey === 'name') {
      this.datasource.data = this.data.entries.sort((a, b) =>
        b.name.trim().localeCompare(a.name.trim())
      );
    }

    if (this.currentSortType === 'za') {
      this.datasource.data.reverse();
    }
    this.currentSortKey = sortKey;

    if (!isNullOrUndefined(this.currentFilter)) {
      this.datasource.filter = this.currentFilter.trim().toLowerCase();
    }
  }

  switchSortType() {
    this.currentSortType === 'az'
      ? (this.currentSortType = 'za')
      : (this.currentSortType = 'az');
  }
}
