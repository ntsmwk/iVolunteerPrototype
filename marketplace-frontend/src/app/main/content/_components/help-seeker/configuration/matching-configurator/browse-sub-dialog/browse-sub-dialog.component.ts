import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';

export class MatchingBrowseSubDialogData {
  title: string;

  entries: { id: string, name: string, producer: string, consumer: string, date: Date }[];

  marketplace: Marketplace;
}

@Component({
  selector: 'browse-matching-sub-dialog',
  templateUrl: './browse-sub-dialog.component.html',
  styleUrls: ['./browse-sub-dialog.component.scss']
})
export class BrowseMatchingSubDialogComponent implements OnInit {

  @Input() data: MatchingBrowseSubDialogData;
  @Input() browseMode: boolean;
  @Output() subDialogReturn: EventEmitter<{ cancelled: boolean, entryId: string }> = new EventEmitter<{ cancelled: boolean, entryId: string }>();

  constructor(
  ) {

  }

  ngOnInit() {

    // DEBUG
    // this.data.entries.push(...this.data.entries);
    // this.data.entries.push(...this.data.entries);
    // this.data.entries.push(...this.data.entries);
  }

  handleRowClick(entry: any) {
    this.subDialogReturn.emit({ cancelled: false, entryId: entry.id });
  }

  handleBackClick() {
    this.subDialogReturn.emit({ cancelled: true, entryId: undefined });
  }


}
