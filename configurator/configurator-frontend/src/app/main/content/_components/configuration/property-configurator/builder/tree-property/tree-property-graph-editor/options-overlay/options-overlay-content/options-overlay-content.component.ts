import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TreePropertyEntry } from 'app/main/content/_model/configurator/property/tree-property';

export class TreePropertyOptionsOverlayContentData {
  treePropertyEntry: TreePropertyEntry;
}

@Component({
  selector: "tree-property-options-overlay-content",
  templateUrl: './options-overlay-content.component.html',
  styleUrls: ['./options-overlay-content.component.scss'],
})
export class TreePropertyOptionsOverlayContentComponent implements OnInit {
  @Input() inputData: TreePropertyOptionsOverlayContentData;
  @Output() result = new EventEmitter<TreePropertyOptionsOverlayContentData>();

  typeSelection: string;

  constructor() { }

  ngOnInit() {
    this.inputData.treePropertyEntry.selectable
      ? (this.typeSelection = 'selector')
      : (this.typeSelection = 'label');
  }

  onSubmit() {
    this.inputData.treePropertyEntry.selectable = this.typeSelection === 'selector';

    const outer = this;
    window.setTimeout(function () {
      outer.result.emit(this.inputData);
    }, 400);
  }
}
