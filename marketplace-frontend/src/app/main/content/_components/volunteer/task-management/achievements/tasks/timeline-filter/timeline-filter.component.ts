import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  SimpleChanges,
  OnChanges,
} from "@angular/core";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import * as shape from "d3-shape";
import * as moment from "moment";
import { LineChartComponent } from '@swimlane/ngx-charts';

@Component({
  selector: "app-timeline-filter",
  templateUrl: "./timeline-filter.component.html",
  styleUrls: ["./timeline-filter.component.scss"],
})
export class TimelineFilterComponent implements OnInit, OnChanges {
  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() selectedTaskType: string;
  @Output() selectedYaxisChange = new EventEmitter<string>();
  @Output() timelineFilterChange = new EventEmitter<any>();
  @Output() selectedYearChange = new EventEmitter<string>();

  selectedYaxis: string = "Dauer [Stunden]";
  timelineFilter: { from: Date; to: Date } = { from: null, to: null };
  selectedYear: string = "Gesamt";
  filteredClassInstanceDTOs: ClassInstanceDTO[];

  @ViewChild("lineChart", { static: false }) lineChart: LineChartComponent;

  uniqueYears: any[];

  timelineChartData: {
    name: string;
    series: { name: Date; value: number }[];
  }[];
  colorScheme = "cool";
  schemeType = "ordinal";
  legend = false;
  showGridLines = true;
  tooltipDisabled = false;
  xAxis = true;
  yAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = "Datum";
  yAxisLabel = "Dauer [Stunden]";
  animations = true;
  autoScale = true;
  timeline = true;
  curve = shape.curveStep;

  removable = true;

  uniqueTt1: any[];
  uniqueTt2: any[];
  uniqueTt3: any[];

  constructor() {
    this.timelineChartData = [{ name: "Tätigkeit", series: [] }];
  }

  ngOnInit() {
    this.updateSelectedYaxis();
    this.updateSelectedYear();
    this.updateTimelineFilter();
  }

  ngOnChanges(changes: SimpleChanges) {
    let changed = false;
    // console.error('timeline-filter changes', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case "classInstanceDTOs": {
            if (typeof changes.classInstanceDTOs.currentValue != "undefined") {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;

              let list = this.classInstanceDTOs.map((ci) => {
                let date = new Date(ci.dateFrom);
                return {
                  year: date.getFullYear().toString(),
                  tt1: ci.taskType1,
                  tt2: ci.taskType2,
                  tt3: ci.taskType3,
                };
              });

              this.uniqueYears = [...new Set(list.map((item) => item.year))];
              this.uniqueYears.push("Gesamt");
              this.uniqueYears.sort();

              this.uniqueTt1 = [...new Set(list.map((item) => item.tt1))];
              this.uniqueTt2 = [...new Set(list.map((item) => item.tt2))];
              this.uniqueTt3 = [...new Set(list.map((item) => item.tt2))];

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
        }
      }
    }

    if (changed) {
      this.generateTimelineData();
    }
  }

  onYearChange(value) {
    this.selectedYear = value.toString();
    this.timelineFilter.from = null;
    this.timelineFilter.to = null;

    this.lineChart.filteredDomain = null;
    this.lineChart.update();

    this.generateTimelineData();

    this.updateSelectedYear();
    this.updateTimelineFilter();
  }

  onYaxisChange(val: string) {
    this.selectedYaxis = val;
    this.yAxisLabel = val;

    this.generateTimelineData();
    this.updateSelectedYaxis();
  }

  filterTimelineApply() {
    this.timelineFilter.from = new Date(this.lineChart.xDomain[0]);
    this.timelineFilter.to = new Date(this.lineChart.xDomain[1]);

    this.selectedYear = null;

    this.updateTimelineFilter();
    this.updateSelectedYear();
  }

  generateTimelineData() {
    if (this.timelineFilter.from == null) {
      if (this.selectedYear === "Gesamt") {
        this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
      } else {
        this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter((c) => {
          return moment(c.dateFrom).isSame(moment(this.selectedYear), "year");
        });
      }
    } else {
      this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
    }

    if (this.selectedTaskType != null) {
      if (this.uniqueTt1.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter((c) => {
          return c.taskType1 === this.selectedTaskType;
        });
      } else if (this.uniqueTt2.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter((c) => {
          return c.taskType2 === this.selectedTaskType;
        });
      }
    }

    let timelineList = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);
      return {
        date: new Date(ci.dateFrom).setHours(0, 0, 0, 0),
        value: Number(value),
      };
    });

    let timelineMap: Map<number, number> = new Map<number, number>();
    timelineList.forEach((t) => {
      if (timelineMap.get(t.date)) {
        timelineMap.set(
          t.date,
          Number(timelineMap.get(t.date)) + Number(t.value)
        );
      } else {
        timelineMap.set(t.date, Number(t.value));
      }
    });

    let data1 = [];
    Array.from(timelineMap.entries()).forEach((entry) => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data1.push({ name: new Date(entry[0]), value: Number(entry[1]) });
      }
    });

    this.timelineChartData[0].series = data1;
    this.timelineChartData = [...this.timelineChartData];
  }

  updateSelectedYaxis() {
    this.selectedYaxisChange.emit(this.selectedYaxis);
  }

  updateSelectedYear() {
    this.selectedYearChange.emit(this.selectedYear);
  }

  updateTimelineFilter() {
    this.timelineFilterChange.emit(JSON.parse(JSON.stringify(this.timelineFilter)));
  }

  dateTickFormatting(value) {
    let locale = "de-DE";
    let formatOptions;

    if (value.getSeconds() !== 0) {
      formatOptions = { second: "2-digit" };
    } else if (value.getMinutes() !== 0) {
      formatOptions = { hour: "2-digit", minute: "2-digit" };
    } else if (value.getHours() !== 0) {
      formatOptions = { hour: "2-digit" };
    } else if (value.getDate() !== 1) {
      formatOptions =
        value.getDay() === 0
          ? { month: "short", day: "2-digit" }
          : { weekday: "short", day: "2-digit" };
    } else if (value.getMonth() !== 0) {
      formatOptions = { month: "long" };
    } else {
      formatOptions = { year: "numeric" };
    }

    return new Intl.DateTimeFormat(locale, formatOptions).format(value);
  }

  dateTickFormatting2(date) {
    if (date instanceof Date) {
      return (<Date>date).toLocaleString("de-DE");
    }
  }

  getTooltip(data) {
    let model = JSON.parse(data);

    let date = new Date(model.name);
    let dateString = date.toLocaleDateString("de-DE");

    let result = "";
    if (this.selectedYaxis === "Anzahl") {
      result = dateString + "\n" + "Anzahl: " + model.value;
    } else {
      result = dateString + "\n" + "Dauer: " + model.value + " Stunden";
    }

    return result;
  }

  getSeriesTooltip(data) {
    let model = JSON.parse(data);
    model = model[0];

    let date = new Date(model.name);
    let dateString = date.toLocaleDateString("de-DE");

    let result = "";
    if (this.selectedYaxis === "Anzahl") {
      result = dateString + "\n" + "Anzahl: " + model.value;
    } else {
      result = dateString + "\n" + "Dauer: " + model.value + " Stunden";
    }

    return result;
  }
}
