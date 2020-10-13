import { Component, OnInit, Input, SimpleChanges } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "fuse-tasks",
  templateUrl: "./tasks.component.html",
  styleUrls: ["./tasks.component.scss"],
  animations: fuseAnimations,
})
export class TasksComponent implements OnInit {
  volunteer: User;
  marketplace: Marketplace;

  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() selectedTenants: Tenant[];
  selectedYaxis: string;
  selectedYear: string;
  selectedTaskType: string;
  timelineFilter: { from: Date; to: Date };

  constructor() {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    // console.error('tasks', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case "classInstanceDTOs": {
            if (typeof changes.classInstanceDTOs.currentValue != "undefined") {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;
            }
            break;
          }
          case "selectedTenants": {
            if (typeof changes.selectedTenants.currentValue != "undefined") {
              this.selectedTenants = changes.selectedTenants.currentValue;
            }
          }
        }
      }
    }
  }

  selectedYaxisChange(selectedYaxis) {
    this.selectedYaxis = selectedYaxis;
  }

  timelineFilterChange(timelineFilter) {
    this.timelineFilter = timelineFilter;
  }

  selectedYearChange(selectedYear) {
    this.selectedYear = selectedYear;
  }

  selectedTaskTypeChange(selectedTaskType) {
    this.selectedTaskType = selectedTaskType;
  }
}
