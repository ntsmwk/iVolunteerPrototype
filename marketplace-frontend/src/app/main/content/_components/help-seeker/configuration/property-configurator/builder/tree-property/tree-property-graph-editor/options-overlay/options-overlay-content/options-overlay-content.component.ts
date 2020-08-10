import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { TreePropertyEntry } from "app/main/content/_model/meta/property/tree-property";

export class TreePropertyOptionsOverlayContentData {
  enumEntry: TreePropertyEntry;
}

@Component({
  selector: "tree-property-options-overlay-content",
  templateUrl: "./options-overlay-content.component.html",
  styleUrls: ["./options-overlay-content.component.scss"],
})
export class TreePropertyOptionsOverlayContentComponent implements OnInit {
  @Input() inputData: TreePropertyOptionsOverlayContentData;
  @Output() result = new EventEmitter<TreePropertyOptionsOverlayContentData>();

  typeSelection: string;

  constructor(private _sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.inputData.enumEntry.selectable
      ? (this.typeSelection = "selector")
      : (this.typeSelection = "label");
  }

  onSubmit() {
    this.inputData.enumEntry.selectable = this.typeSelection === "selector";

    const outer = this;
    window.setTimeout(function () {
      outer.result.emit(this.inputData);
    }, 400);
  }
}
