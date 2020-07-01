import {
  Component,
  OnInit,
  OnChanges,
  Input,
  SimpleChanges,
} from "@angular/core";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { Marketplace } from "app/main/content/_model/marketplace";
import { StoredChartService } from "app/main/content/_service/stored-chart.service";
import * as moment from "moment";
import { StoredChart } from "app/main/content/_model/stored-chart";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "app-donut",
  templateUrl: "./donut.component.html",
  styleUrls: ["./donut.component.scss"],
})
export class DonutComponent implements OnInit, OnChanges {
  @Input() type: string;

  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() selectedYaxis: string;
  @Input() selectedYear: string;
  @Input() selectedTaskType: string;
  @Input() timelineFilter: { from: Date; to: Date };

  @Input() marketplace: Marketplace;
  @Input() volunteer: User;

  filteredClassInstanceDTOs: ClassInstanceDTO[];
  donutData: any[];

  uniqueTt1: any[];
  uniqueTt2: any[];

  animation = true;
  labels = false;
  tooltipDisabled = false;
  legend = false;
  explodeSlices = false;
  doughnut = true;
  arcWidth = "0.6";
  scheme = "cool";

  constructor(private storedChartService: StoredChartService) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    // console.error('donut changes', changes);
    let changed = false;

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case "classInstanceDTOs": {
            if (typeof changes.classInstanceDTOs.currentValue != "undefined") {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;

              let list = this.classInstanceDTOs.map((ci) => {
                return {
                  tt1: ci.taskType1,
                  tt2: ci.taskType2,
                  tt3: ci.taskType3,
                };
              });
              this.uniqueTt1 = [...new Set(list.map((item) => item.tt1))];
              this.uniqueTt2 = [...new Set(list.map((item) => item.tt2))];

              // this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.sort((a, b) => {
              //   return new Date(b.dateFrom).getTime() - new Date(a.dateFrom).getTime();
              // });

              changed = true;
            }
            break;
          }
          case "selectedYaxis": {
            if (typeof this.selectedYaxis != "undefined") {
              this.selectedYaxis = changes.selectedYaxis.currentValue;
              changed = true;
            }
            break;
          }
          case "selectedYear": {
            if (typeof changes.selectedYear.currentValue != "undefined") {
              this.selectedYear = changes.selectedYear.currentValue;
              changed = true;
            }
            break;
          }
          case "selectedTaskType": {
            if (typeof changes.selectedTaskType.currentValue != "undefined") {
              this.selectedTaskType = changes.selectedTaskType.currentValue;
              changed = true;
            }
            break;
          }
          case "timelineFilter":
            {
              if (typeof changes.timelineFilter.currentValue != "undefined") {
                this.timelineFilter = changes.timelineFilter.currentValue;
                changed = true;
              }
            }
            break;
        }
      }

      if (changed) {
        this.generateData();
      }
    }
  }

  generateData() {
    // filter everything here
    if (this.timelineFilter.from == null) {
      if (this.selectedYear === "Gesamt") {
        this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
      } else {
        this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((c) => {
          return moment(c.dateFrom).isSame(moment(this.selectedYear), "year");
        });
      }
    } else {
      this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((c) => {
        return (
          moment(c.dateFrom).isSameOrAfter(
            moment(this.timelineFilter.from),
            "day"
          ) &&
          moment(c.dateFrom).isSameOrBefore(
            moment(this.timelineFilter.to),
            "day"
          )
        );
      });
    }

    if (this.selectedTaskType != null) {
      if (this.uniqueTt1.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
          (c) => {
            return c.taskType1 === this.selectedTaskType;
          }
        );
      } else if (this.uniqueTt2.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
          (c) => {
            return c.taskType2 === this.selectedTaskType;
          }
        );
      }
    }

    switch (this.type) {
      case "Wochentag":
        this.generateWeekdayData();
        break;

      case "Tageszeit":
        this.generateDayTimeData();
        break;

      case "Orte":
        this.generatePlacesData();
        break;

      case "Rang":
        this.generateRankData();
        break;
    }
  }

  generateWeekdayData() {
    let weekdayList = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);
      return {
        weekday: moment(ci.dateFrom).locale("de").format("dddd"),
        value: value,
      };
    });

    let weekdayMap: Map<string, number> = new Map<string, number>();
    weekdayList.forEach((t) => {
      if (weekdayMap.get(t.weekday)) {
        weekdayMap.set(
          t.weekday,
          Number(weekdayMap.get(t.weekday)) + Number(t.value)
        );
      } else {
        weekdayMap.set(t.weekday, Number(t.value));
      }
    });

    let data = [];
    Array.from(weekdayMap.entries()).forEach((entry) => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data.push({ name: entry[0], value: entry[1] });
      }
    });

    this.donutData = [...data];
  }

  generatePlacesData() {
    let placesList = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);
      return { location: ci.location, value: value };
    });

    let locationMap: Map<string, number> = new Map<string, number>();
    placesList.forEach((t) => {
      if (locationMap.get(t.location)) {
        locationMap.set(
          t.location,
          Number(locationMap.get(t.location)) + Number(t.value)
        );
      } else {
        locationMap.set(t.location, Number(t.value));
      }
    });

    let data = [];
    Array.from(locationMap.entries()).forEach((entry) => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        if (entry[0] === "") {
          data.push({ name: "keine Angabe", value: Number(entry[1]) });
        } else {
          data.push({ name: entry[0], value: Number(entry[1]) });
        }
      }
    });

    data.sort((a, b) => b.value - a.value);
    let data2 = data.slice(0, 12);
    this.donutData = [...data2];
  }

  generateDayTimeData() {
    let daytimeList = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);

      let key;
      let hours = new Date(ci.dateFrom).getHours();
      hours >= 7 && hours <= 18 ? (key = "Tag") : (key = "Nacht");

      return { dayNight: key, value: value };
    });

    let dayNightMap: Map<string, number> = new Map<string, number>();
    daytimeList.forEach((t) => {
      if (dayNightMap.get(t.dayNight)) {
        dayNightMap.set(
          t.dayNight,
          Number(dayNightMap.get(t.dayNight)) + Number(t.value)
        );
      } else {
        dayNightMap.set(t.dayNight, Number(t.value));
      }
    });

    let data = [];
    Array.from(dayNightMap.entries()).forEach((entry) => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data.push({ name: entry[0], value: entry[1] });
      }
    });

    this.donutData = [...data];
  }

  generateRankData() {
    let rankList = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);
      return { rang: ci.rank, value: value };
    });

    let rangMap: Map<string, number> = new Map<string, number>();
    rankList.forEach((t) => {
      if (rangMap.get(t.rang)) {
        rangMap.set(t.rang, Number(rangMap.get(t.rang)) + Number(t.value));
      } else {
        rangMap.set(t.rang, Number(t.value));
      }
    });

    let data = [];
    let empty: number = 0;

    Array.from(rangMap.entries()).forEach((entry) => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        if (entry[0] === "") {
          data.push({ name: "keine Angabe", value: Number(entry[1]) });
        } else {
          data.push({ name: entry[0], value: Number(entry[1]) });
        }
      }
    });
    this.donutData = [...data];
  }

  onDonutSelect(event) {}

  exportChart() {
    let storedChart: StoredChart;
    storedChart = new StoredChart(
      this.type,
      "ngx-charts-pie-chart",
      JSON.stringify(this.donutData),
      this.volunteer.id
    );
    this.storedChartService.save(this.marketplace, storedChart).toPromise();
  }
}
