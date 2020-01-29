import { Component, OnInit, ViewChild } from '@angular/core';
import { fuseAnimations } from '../../../../../../@fuse/animations';
import { Participant } from '../../../_model/participant';
import { LoginService } from '../../../_service/login.service';
import { ClassInstanceService } from '../../../_service/meta/core/class/class-instance.service';
import { Marketplace } from '../../../_model/marketplace';
import { CoreMarketplaceService } from '../../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { ClassInstance } from '../../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';
import { CoreVolunteerService } from '../../../_service/core-volunteer.service';
import { Volunteer } from '../../../_model/volunteer';
import { ArrayService } from '../../../_service/array.service';
import * as moment from 'moment';
import * as shape from 'd3-shape';
import { CIP } from '../../../_model/classInstancePropertyConstants';
import { StoredChartService } from '../../../_service/stored-chart.service';
import { StoredChart } from '../../../_model/stored-chart';

import * as Highcharts from 'highcharts';
import HC_drilldown from 'highcharts/modules/drilldown.js';
import HC_sunburst from 'highcharts/modules/sunburst';
HC_drilldown(Highcharts);
HC_sunburst(Highcharts);

@Component({
  selector: 'fuse-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss'],
  animations: fuseAnimations
})

export class TasksComponent implements OnInit {
  fakeChecked: boolean = false;

  prevNodeLevel: number = null;


  IVOLUNTEER_UUID = CIP.IVOLUNTEER_UUID;
  IVOLUNTEER_SOURCE = CIP.IVOLUNTEER_SOURCE;
  TASK_ID = CIP.TASK_ID;
  TASK_NAME = CIP.TASK_NAME;
  TASK_TYPE_1 = CIP.TASK_TYPE_1;
  TASK_TYPE_2 = CIP.TASK_TYPE_2;
  TASK_TYPE_3 = CIP.TASK_TYPE_3;
  TASK_TYPE_4 = CIP.TASK_TYPE_4;
  TASK_DESCRIPTION = CIP.TASK_DESCRIPTION;
  ZWECK = CIP.ZWECK;
  ROLLE = CIP.ROLLE;
  RANG = CIP.RANG;
  PHASE = CIP.PHASE;
  ARBEITSTEILUNG = CIP.ARBEITSTEILUNG;
  EBENE = CIP.EBENE;
  TASK_DATE_FROM = CIP.TASK_DATE_FROM;
  TASK_DATE_TO = CIP.TASK_DATE_TO;
  TASK_DURATION = CIP.TASK_DURATION;
  TASK_LOCATION = CIP.TASK_LOCATION;
  TASK_GEO_INFORMATION = CIP.TASK_GEO_INFORMATION;

  private volunteer: Participant;
  private marketplace: Marketplace;
  private classInstances: ClassInstance[] = [];
  private tableDataSource = new MatTableDataSource<ClassInstance>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  private displayedColumns: string[] = ['taskName', 'taskDateFrom', 'taskDuration'];

  private timelineData: any[];
  private weekdayData: any[];
  private dayNightData: any[];
  private locationData: any[];
  private rangData: any[];
  private filteredClassInstances: ClassInstance[] = [];


  // Todo: set chart properties for each type separately
  // timeline
  colorScheme = 'cool';
  schemeType = 'ordinal';
  showGridLines = true;
  animations = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Datum';
  yAxisLabel = 'Dauer [h]';
  noBarWhenZero = true;
  showLabels = true;
  curve = shape.curveStep;
  autoScale = true;
  legend = false;
  legendPosition = 'below';
  tooltipDisabled = false;
  // dateTickFormatting(val: any): string {
  //    if (val instanceof Date) {
  //      return (<Date>val).toLocaleDateString('de-DE');
  //    }
  // }

  // sunburst chart
  sunburstData = [];
  ngxColorsCool = ['#a8385d',
    '#7aa3e5',
    '#a27ea8',
    '#aae3f5',
    '#adcded',
    '#a95963',
    '#8796c0',
    '#7ed3ed',
    '#50abcc',
    '#ad6886'];

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {
    chart: {
      height: 900,
      margin: [0, 0, 0, 0]

    },
    title: {
      text: undefined
    },
    tooltip: {
      valueDecimals: 2
    },
    credits: {
      enabled: false
    },
    plotOptions: {
      series: {
        dataLabels: {
          enabled: true,
          style: {
            fontSize: '17px',
            color: '#ffffff'
          }
        }
      }
    },
    series: [{
      type: "sunburst",
      data: this.sunburstData,
      allowTraversingTree: true,
      cursor: 'pointer',
      //   levels: [{
      //     level: 1,
      //     levelIsConstant: false,
      //     dataLabels: {
      //         filter: {
      //             property: 'outerArcLength',
      //             operator: '>',
      //             value: 64
      //         }
      //     }
      // }, {
      //     level: 2,
      //     colorByPoint: true
      // },
      // {
      //     level: 3,
      //     colorVariation: {
      //         key: 'brightness',
      //         to: -0.5
      //     }
      // }, {
      //     level: 4,
      //     colorVariation: {
      //         key: 'brightness',
      //         to: 0.5
      //     }
      // }],
      //   dataLabels: {
      //     format: '{point.name}',
      //     filter: {
      //       property: 'innerArcLength',
      //       operator: '>',
      //       value: 16
      //     }
      //   }
    }]
  };

  prevClickedNode: any = null;
  prevFilteredClassInstances: any[];
  uniqueTt1: any[];
  uniqueTt2: any[];
  uniqueTt3: any[];


  // filter chips
  chipTimelineFilterFrom: Date = null;
  chipTimelineFilterTo: Date = null;
  chipTaskType: string = null;
  chipSelectedYear: string = null;
  removable = true;

  @ViewChild('lineChart', { static: false }) lineChart: any;

  selectedYaxis: string;
  selectedYear: string;
  yearsMap: Map<string, number>;
  timelineFilterFrom: Date;
  timelineFilterTo: Date;

  public timelineChartData: { name: string, series: { name: Date, value: number }[] }[];

  sunburstCenterName: string = 'Tätigkeiten';

  constructor(private loginService: LoginService,
    private arrayService: ArrayService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
    private volunteerService: CoreVolunteerService,
    private storedChartService: StoredChartService
  ) {
    this.timelineChartData = [{ name: 'Tätigkeit', series: [] }];
  }

  ngOnInit() {
    Highcharts.getOptions().colors.splice(0, 0, 'transparent');

    this.selectedYaxis = 'Dauer';
    this.selectedYear = 'Gesamt';
    this.chipSelectedYear = 'Gesamt';

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = participant as Volunteer;

      Promise.all([
        this.marketplaceService.findAll().toPromise(),
        this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      ]).then((values: any[]) => {

        // TODO: 
        this.marketplace = values[0][0];

        this.classInstanceService.getClassInstancesByArcheTypeFake(this.marketplace, 'TASK').toPromise().then((ret: ClassInstance[]) => {
          if (!isNullOrUndefined(ret)) {
            this.classInstances = ret;

            this.classInstances.forEach((ci, index, object) => {
              if (ci.properties[this.TASK_DURATION].values[0] == 'null') {
                object.splice(index, 1);
              }
            });

            this.filteredClassInstances = [...this.classInstances];

            let list = this.filteredClassInstances
              .map(ci => {
                return ({ tt1: ci.properties[this.TASK_TYPE_1].values[0], tt2: ci.properties[this.TASK_TYPE_2].values[0], tt3: ci.properties[this.TASK_TYPE_3].values[0] })
              });
            this.uniqueTt1 = [...new Set(list.map(item => item.tt1))];
            this.uniqueTt2 = [...new Set(list.map(item => item.tt2))];
            this.uniqueTt3 = [...new Set(list.map(item => item.tt2))];


            this.tableDataSource.data = this.classInstances;
            this.tableDataSource.paginator = this.paginator;

            this.generateTimelineData();
            this.generateSunburstData();
            this.generateOtherChartsData();
          }
        });
      });
    });

    Highcharts.chart('sunburstChart', this.chartOptions).update({
      plotOptions: {
        series: {
          events: {
            click: (event) => {
              this.onSunburstChanged(event);
            },
          },
        },
      }
    });

  }

  onYaxisChange(val: string) {
    this.selectedYaxis = val;
    this.yAxisLabel = val;
    this.generateTimelineData();
    this.generateOtherChartsData();
    this.generateSunburstData();
  }

  onSunburstChanged(event) {
    if (event.point.id === '0') {
      // Tätigkeitsart clicked
      this.prevClickedNode = null;
      this.chipTaskType = null;

      this.filteredClassInstances = [...this.classInstances];

    } else {
      if (event.point.node.level < 4) {
        if (event.point.node.level <= this.prevNodeLevel) {
          // drillup

          let parent = null;
          this.sunburstData.forEach(d => {
            if (d.id === event.point.parent) {
              parent = d;
            }
          });

          if (parent.name === this.sunburstCenterName) {
            this.chipTaskType = null;
            this.prevClickedNode = null;
            this.prevNodeLevel = 1;
            this.filteredClassInstances = [...this.classInstances];

          } else {
            this.chipTaskType = parent.name;
            this.prevClickedNode = event.point.name;
            this.prevNodeLevel = event.point.node.level;
            this.filteredClassInstances = this.classInstances.filter(c => {
              return c.properties[this.TASK_TYPE_1].values[0] === parent.name;
            });
          }


        } else {
          // drilldown
          this.prevClickedNode = event.point.name;
          this.prevNodeLevel = event.point.node.level;


          if (this.uniqueTt1.indexOf(event.point.name) > -1) {
            // filter for tt1
            this.chipTaskType = event.point.name;
            this.filteredClassInstances = this.classInstances.filter(c => {
              return c.properties[this.TASK_TYPE_1].values[0] === event.point.name;
            });

          } else if (this.uniqueTt2.indexOf(event.point.name) > -1) {
            // filter for tt2
            this.filteredClassInstances = this.classInstances.filter(c => {
              return c.properties[this.TASK_TYPE_2].values[0] === event.point.name;
            });

            this.chipTaskType = event.point.name;
          }
        }
      }
    }

    // TIME FILTERN

    if (this.chipSelectedYear != null) {
      // nach jahr filtern

      if (this.selectedYear === 'Gesamt') {
        this.filteredClassInstances = [...this.filteredClassInstances];
      } else {
        this.filteredClassInstances = this.filteredClassInstances.filter(c => {
          return (moment(c.properties[this.TASK_DATE_FROM].values[0]).isSame(moment(this.selectedYear), 'year'));
        });
      }

    } else {
      // nach timeline filtern

      this.filteredClassInstances = this.filteredClassInstances.filter(c => {
        return (moment(c.properties[this.TASK_DATE_FROM].values[0]).isAfter(moment(this.timelineFilterFrom)) &&
          moment(c.properties[this.TASK_DATE_FROM].values[0]).isBefore(moment(this.timelineFilterTo)));
      });
    }


    this.generateTimelineData();
    this.generateOtherChartsData();
  }

  onYearChange(value) {
    this.selectedYear = value;
    this.chipSelectedYear = this.selectedYear;

    this.chipTimelineFilterFrom = null;
    this.chipTimelineFilterTo = null;

    if (this.selectedYear === 'Gesamt') {
      this.filteredClassInstances = [...this.classInstances];
    } else {
      this.filteredClassInstances = this.classInstances.filter(c => {
        return (moment(c.properties[this.TASK_DATE_FROM].values[0]).isSame(moment(this.selectedYear), 'year'));
      });
    }

    this.lineChart.filteredDomain = null;
    this.lineChart.update();

    this.generateTimelineData();
    this.generateOtherChartsData();
    this.generateSunburstData();
  }

  filterTimelineApply() {
    let a = new Date(this.lineChart.xDomain[0]);
    let b = new Date(this.lineChart.xDomain[1]);

    this.chipSelectedYear = null;

    if (a != this.timelineFilterFrom || b != this.timelineFilterTo) {
      this.timelineFilterFrom = a;
      this.timelineFilterTo = b;

      this.chipTimelineFilterFrom = this.timelineFilterFrom;
      this.chipTimelineFilterTo = this.timelineFilterTo;

      this.filteredClassInstances = this.classInstances.filter(c => {
        return (moment(c.properties[this.TASK_DATE_FROM].values[0]).isAfter(moment(this.timelineFilterFrom)) &&
          moment(c.properties[this.TASK_DATE_FROM].values[0]).isBefore(moment(this.timelineFilterTo)));
      });

      this.generateOtherChartsData();
      this.generateSunburstData();

    }
  }

  generateTimelineData() {
    let data1 = [];

    let timelineList = this.filteredClassInstances.map(ci => {
      let value;
      (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];
      return ({ date: new Date(ci.properties[this.TASK_DATE_FROM].values[0]).setHours(0, 0, 0, 0), value: Number(value) });
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

  generateSunburstData() {
    this.chipTaskType = null;

    // sunburst data
    let list = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];

        return ({ tt1: ci.properties[this.TASK_TYPE_1].values[0], tt2: ci.properties[this.TASK_TYPE_2].values[0], tt3: ci.properties[this.TASK_TYPE_3].values[0], value: value })
      });


    let data = [];

    // insert 0 entry
    data.push({ id: '0', parent: '', name: this.sunburstCenterName });

    // insert tt1 entries
    this.uniqueTt1.forEach((tt1, index) => {
      data.push({ id: (index + 1).toString(), parent: '0', name: tt1, color: this.ngxColorsCool[index] });
    });

    // insert tt2 entries (for each tt1 separetly)
    this.uniqueTt1.forEach(tt1 => {
      let tt1List = list.filter(l => {
        return (tt1 === l.tt1);
      });

      let tt2Map: Map<string, number> = new Map<string, number>();
      tt1List.forEach(entry => {
        if (tt2Map.get(entry.tt2)) {
          tt2Map.set(entry.tt2, Number(tt2Map.get(entry.tt2)) + Number(entry.value))
        } else {
          tt2Map.set(entry.tt2, entry.value);
        }
      });

      let indexParent = this.uniqueTt1.indexOf(tt1) + 1;

      Array.from(tt2Map.entries()).forEach((entry, index) => {
        if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
          data.push({ id: indexParent + '-' + (index + 1).toString(), parent: indexParent.toString(), name: entry[0], value: Number(entry[1]) });
        }
      });
    });

    // snapshot before any tt3 data is added
    let dataTt2 = [...data];

    // insert tt3 entries (for each tt2 separetly)
    this.uniqueTt2.forEach(tt2 => {
      let tt2List = list.filter(l => {
        return (tt2 === l.tt2);
      });

      let tt3Map: Map<string, number> = new Map<string, number>();
      tt2List.forEach(entry => {
        if (tt3Map.get(entry.tt3)) {
          tt3Map.set(entry.tt3, Number(tt3Map.get(entry.tt3)) + Number(entry.value))
        } else {
          tt3Map.set(entry.tt3, entry.value);
        }
      });

      let indexParent: string = null;
      dataTt2.forEach(d => {
        if (d.name === tt2) {
          indexParent = d.id;
        }
      });

      Array.from(tt3Map.entries()).forEach((entry, index) => {
        if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
          data.push({ id: indexParent + '-' + (index + 1).toString(), parent: indexParent.toString(), name: entry[0], value: Number(entry[1]) });
        }
      });


    });

    this.sunburstData = [...data];
    //console.error('sunburstData', this.sunburstData);

    this.chartOptions.series = [
      {
        type: 'sunburst',
        allowTraversingTree: true,
        cursor: 'pointer',
        data: this.sunburstData
      }
    ];

    Highcharts.chart('sunburstChart', this.chartOptions);

  }


  generateOtherChartsData() {
    let data = [];

    // donut: Wochentag
    let weekdayList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];
        return ({ weekday: moment(ci.properties[this.TASK_DATE_FROM].values[0]).locale("de").format('dddd'), value: value })
      });

    // console.error('weekdayList', weekdayList);

    let weekdayMap: Map<string, number> = new Map<string, number>();
    weekdayList.forEach(t => {
      if (weekdayMap.get(t.weekday)) {
        weekdayMap.set(t.weekday, Number(weekdayMap.get(t.weekday)) + Number(t.value))
      } else {
        weekdayMap.set(t.weekday, Number(t.value));
      }
    });

    data = [];
    Array.from(weekdayMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data.push({ name: entry[0], value: entry[1] })
      }
    });
    this.weekdayData = [...data];


    // donut: day night
    let dayNightList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];

        let key;
        let hours = new Date(ci.properties[this.TASK_DATE_FROM].values[0]).getHours();
        (hours >= 7 && hours <= 18) ? key = 'Tag' : key = 'Nacht';

        return ({ dayNight: key, value: value })
      });

    let dayNightMap: Map<string, number> = new Map<string, number>();
    dayNightList.forEach(t => {
      if (dayNightMap.get(t.dayNight)) {
        dayNightMap.set(t.dayNight, Number(dayNightMap.get(t.dayNight)) + Number(t.value))
      } else {
        dayNightMap.set(t.dayNight, Number(t.value));
      }
    });

    data = [];
    Array.from(dayNightMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        data.push({ name: entry[0], value: entry[1] })
      }
    });

    this.dayNightData = [...data];

    // donut: taskLocation
    let locationList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];
        return ({ location: ci.properties[this.TASK_LOCATION].values[0], value: value })
      });

    let locationMap: Map<string, number> = new Map<string, number>();
    locationList.forEach(t => {
      if (locationMap.get(t.location)) {
        locationMap.set(t.location, Number(locationMap.get(t.location)) + Number(t.value))
      } else {
        locationMap.set(t.location, Number(t.value));
      }
    });

    data = [];
    Array.from(locationMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        if (entry[0] === '') {
          data.push({ name: 'keine Angabe', value: Number(entry[1]) });

        } else {
          data.push({ name: entry[0], value: Number(entry[1]) });

        }
      }
    });
    data.sort((a, b) => b.value - a.value);
    let data2 = data.slice(0, 12);
    this.locationData = [...data2];

    // donut: rang
    // console.error('this.filteredClassInstances', this.filteredClassInstances);
    let rangList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[this.TASK_DURATION].values[0];
        return ({ rang: ci.properties[this.RANG].values[0], value: value })
      });

    // console.error('rangList', rangList);

    let uniqueRang = [...new Set(rangList.map(item => item.rang))];
    // console.error('uniqueRang', uniqueRang);


    let rangMap: Map<string, number> = new Map<string, number>();
    rangList.forEach(t => {
      if (rangMap.get(t.rang)) {
        rangMap.set(t.rang, Number(rangMap.get(t.rang)) + Number(t.value))
      } else {
        rangMap.set(t.rang, Number(t.value));
      }
    });

    // console.error('rangMap', rangMap);


    data = [];
    Array.from(rangMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
        if (entry[0] === '') {
          data.push({ name: 'keine Angabe', value: Number(entry[1]) });

        } else {
          data.push({ name: entry[0], value: Number(entry[1]) });

        }
      }
    });
    this.rangData = [...data];
    // console.error('rangData', this.rangData);

    this.tableDataSource.data = this.filteredClassInstances;
    this.paginator._changePageSize(this.paginator.pageSize);
  }

  exportChart(event, source: string) {
    let storedChart: StoredChart;
    switch (source) {
      case 'Wochentag':
        storedChart = new StoredChart('Wochentag', 'ngx-charts-pie-chart', JSON.stringify(this.weekdayData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;

      case 'Tageszeit':
        storedChart = new StoredChart('Tageszeit', 'ngx-charts-pie-chart', JSON.stringify(this.dayNightData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;

      case 'Ort':
        storedChart = new StoredChart('Meistbesuchte Orte', 'ngx-charts-pie-chart', JSON.stringify(this.locationData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;

      case 'Rang':
        storedChart = new StoredChart('Rang', 'ngx-charts-pie-chart', JSON.stringify(this.rangData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }
}