import { Component, OnInit, Input, Output, EventEmitter, ViewChild, SimpleChanges, OnChanges } from '@angular/core';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/class';
import * as shape from 'd3-shape';
import * as moment from 'moment';

@Component({
  selector: 'app-timeline-filter',
  templateUrl: './timeline-filter.component.html',
  styleUrls: ['./timeline-filter.component.scss']
})
export class TimelineFilterComponent implements OnInit, OnChanges {
  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() selectedTaskType: string;

  @Input() selectedYaxis: string;
  @Output() selectedYaxisChange = new EventEmitter<string>();

  @Input() timelineFilter: { from: Date, to: Date };
  @Output() timelineFilterChange = new EventEmitter<any>();

  @Input() selectedYear: string;
  @Output() selectedYearChange = new EventEmitter<string>();

  filteredClassInstanceDTOs: ClassInstanceDTO[];

  @ViewChild('lineChart', { static: false }) lineChart: any;

  uniqueYears: any[];

  timelineChartData: { name: string, series: { name: Date, value: number }[] }[];
  colorScheme = 'cool';
  schemeType = 'ordinal';
  legend = false;
  showGridLines = true;
  tooltipDisabled = false;
  xAxis = true;
  yAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Datum';
  yAxisLabel = 'Dauer [h]';
  animations = true;
  autoScale = true;
  timeline = true;
  curve = shape.curveStep;

  removable = true;

  uniqueTt1: any[];
  uniqueTt2: any[];
  uniqueTt3: any[];

  constructor() {
    this.timelineChartData = [{ name: 'Tätigkeit', series: [] }];
  }

  ngOnInit() {
    this.selectedYaxis = 'Dauer [h]';
    this.updateSelectedYaxis(this.selectedYaxis);

    this.selectedYear = 'Gesamt';
    this.updateSelectedYear(this.selectedYear);

    this.timelineFilter = { from: null, to: null };
    this.updateTimelineFilter(this.timelineFilter);
  }

  ngOnChanges(changes: SimpleChanges) {
    // console.error('timeline-filter changes', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case 'classInstanceDTOs': {
            if (typeof changes.classInstanceDTOs.currentValue != 'undefined') {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;
              this.filteredClassInstanceDTOs = this.classInstanceDTOs;

              let list = this.filteredClassInstanceDTOs
                .map(ci => {
                  let date = new Date(ci.dateFrom);
                  return ({ year: date.getFullYear().toString(), tt1: ci.taskType1, tt2: ci.taskType2, tt3: ci.taskType3 })
                });

              this.uniqueYears = [...new Set(list.map(item => item.year))];
              this.uniqueYears.push('Gesamt');
              this.uniqueTt1 = [...new Set(list.map(item => item.tt1))];
              this.uniqueTt2 = [...new Set(list.map(item => item.tt2))];
              this.uniqueTt3 = [...new Set(list.map(item => item.tt2))];

              this.generateTimelineData();
            }
            break;
          }

          case 'selectedTaskType': {
            if (typeof changes.selectedTaskType.currentValue != 'undefined') {
              this.filterTaskType();
            }
          }
        }
      }
    }
  }

  onYearChange(value) {
    this.selectedYear = value.toString();
    this.timelineFilter.from = null;
    this.timelineFilter.to = null;

    if (this.selectedYear === 'Gesamt') {
      this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
    } else {
      this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(c => {
        return (moment(c.dateFrom).isSame(moment(this.selectedYear), 'year'));
      });
    }

    this.lineChart.filteredDomain = null;
    this.lineChart.update();

    this.generateTimelineData();

    this.updateSelectedYear(this.selectedYear);
    this.updateTimelineFilter(this.timelineFilter);
  }

  onYaxisChange(val: string) {
    this.selectedYaxis = val;
    this.yAxisLabel = val;

    this.generateTimelineData();

    this.updateSelectedYaxis(this.selectedYaxis);
  }

  filterTimelineApply() {
    this.timelineFilter.from = new Date(this.lineChart.xDomain[0]);
    this.timelineFilter.to = new Date(this.lineChart.xDomain[1]);

    this.selectedYear = null;

    this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(c => {
      return (moment(c.dateFrom).isAfter(moment(this.timelineFilter.from)) &&
        moment(c.dateFrom).isBefore(moment(this.timelineFilter.to)));
    });

    this.updateTimelineFilter(this.timelineFilter);
    this.updateSelectedYear(null);
  }

  filterTaskType() {
    if (this.selectedTaskType != null) {
      if (this.uniqueTt1.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(c => {
          return c.taskType1 === this.selectedTaskType;
        });
      } else if (this.uniqueTt2.indexOf(this.selectedTaskType) > -1) {
        this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(c => {
          return c.taskType2 === this.selectedTaskType;
        });
      }

    } else {
      // no tasktype filter
      this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
    }

    // filter by time
    if (this.timelineFilter.from === null || typeof this.timelineFilter.from === 'undefined') {
      // filter by year
      if (this.selectedYear === 'Gesamt') {
        this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
      } else {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(c => {
          return (moment(c.dateFrom).isSame(moment(this.selectedYear), 'year'));
        });
      }

    } else {
      // filter by timeline from to
      this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(c => {
        return (moment(c.dateFrom).isAfter(moment(this.timelineFilter.from)) &&
          moment(c.dateFrom).isBefore(moment(this.timelineFilter.to)));
      });

    }

    this.generateTimelineData();

  }

  generateTimelineData() {
    let data1 = [];

    let timelineList = this.filteredClassInstanceDTOs.map(ci => {
      let value: number;
      (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.duration;
      return ({ date: new Date(ci.dateFrom).setHours(0, 0, 0, 0), value: Number(value) });
    });

    let timelineMap: Map<number, number> = new Map<number, number>();
    timelineList.forEach(t => {
      if (timelineMap.get(t.date)) {
        timelineMap.set(t.date, Number(timelineMap.get(t.date)) + Number(t.value));
      } else {
        timelineMap.set(t.date, Number(t.value));
      }
    });

    Array.from(timelineMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data1.push({ name: new Date(entry[0]), value: Number(entry[1]) });
      }
    });

    this.timelineChartData[0].series = data1;
    this.timelineChartData = [...this.timelineChartData];
  }

  updateSelectedYaxis(selectedYaxis) {
    this.selectedYaxis = selectedYaxis;
    this.selectedYaxisChange.emit(selectedYaxis);
  }

  updateSelectedYear(selectedYear) {
    this.selectedYear = selectedYear;
    this.selectedYearChange.emit(selectedYear);
  }

  updateTimelineFilter(timelineFilter) {
    this.timelineFilter = timelineFilter;
    this.timelineFilterChange.emit(JSON.parse(JSON.stringify(timelineFilter)));
  }

  dateTickFormatting2(date) {
    if (date instanceof Date) {
      return (<Date>date).toLocaleString('de-DE');
    }
  }

  dateTickFormatting(value) {
    let locale = 'de-DE';
    let formatOptions;

    if (value.getSeconds() !== 0) {
      formatOptions = { second: '2-digit' };
    } else if (value.getMinutes() !== 0) {
      formatOptions = { hour: '2-digit', minute: '2-digit' };
    } else if (value.getHours() !== 0) {
      formatOptions = { hour: '2-digit' };
    } else if (value.getDate() !== 1) {
      formatOptions = value.getDay() === 0 ? { month: 'short', day: '2-digit' } : { weekday: 'short', day: '2-digit' };
    } else if (value.getMonth() !== 0) {
      formatOptions = { month: 'long' };
    } else {
      formatOptions = { year: 'numeric' };
    }

    return new Intl.DateTimeFormat(locale, formatOptions).format(value);
  }

  getTooltip(data) {
    let model = JSON.parse(data);

    let date = new Date(model.name);
    let dateString = date.toLocaleDateString("de-DE");

    let result = '';
    if (this.selectedYaxis === 'Anzahl') {
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

    let result = '';
    if (this.selectedYaxis === 'Anzahl') {
      result = dateString + "\n" + "Anzahl: " + model.value;

    } else {
      result = dateString + "\n" + "Dauer: " + model.value + " Stunden";
    }

    return result;

  }
}