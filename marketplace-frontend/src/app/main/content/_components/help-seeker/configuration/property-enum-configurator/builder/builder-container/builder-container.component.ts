import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { PropertyDefinition } from "app/main/content/_model/meta/property";

import { ActivatedRoute, Router, Params } from "@angular/router";
import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "app-builder-container",
  templateUrl: "./builder-container.component.html",
  styleUrls: ["./builder-container.component.scss"],
})
export class BuilderContainerComponent implements OnInit {
  @Input() marketplace: Marketplace;
  @Input() helpseeker: User;
  @Input() allPropertyDefinitions: PropertyDefinition<any>[];
  @Input() builderType: string;
  @Input() entryId: string;
  @Input() sourceString: string;
  @Output() result: EventEmitter<PropertyDefinition<any>> = new EventEmitter<
    PropertyDefinition<any>
  >();
  @Output() management: EventEmitter<String> = new EventEmitter();

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit() {}

  onSelectionChange() {
    if (this.sourceString !== "dialog") {
      const queryParams: Params = { type: this.builderType };

      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: queryParams,
        queryParamsHandling: "merge",
        skipLocationChange: true,
      });
    }
  }

  handleResultEvent(event) {
    this.result.emit(event);
  }

  handleManagementEvent(event) {
    this.management.emit(event);
  }

  isRadioGroupDisabled() {
    return !isNullOrUndefined(this.entryId);
  }

  navigateBack() {
    window.history.back();
  }
}
