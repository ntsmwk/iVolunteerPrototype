import { Component, OnInit, ViewChild, Output, HostListener } from '@angular/core';
import { fuseAnimations } from '../../../../../@fuse/animations';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { Marketplace } from '../../_model/marketplace';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { ClassInstance } from '../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { Volunteer } from '../../_model/volunteer';
import { ArrayService } from '../../_service/array.service';
import * as moment from 'moment';
import { Subject, timer } from 'rxjs';
import * as shape from 'd3-shape';
import * as Highcharts from 'highcharts';
import HC_sunburst from 'highcharts/modules/sunburst';
HC_sunburst(Highcharts);

/*
timer(0, 500)
  .subscribe(() => this.filterApply())
*/

@Component({
  selector: 'fuse-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss'],
  animations: fuseAnimations
})

export class TasksComponent implements OnInit {
  private volunteer: Participant;
  private marketplace: Marketplace;
  private classInstances: ClassInstance[] = [];
  private tableDataSource = new MatTableDataSource<ClassInstance>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  private displayedColumns: string[] = ['taskType1', 'taskName', 'taskDateFrom', 'taskDateTo', 'taskDuration'];

  private timelineData: any[] = [];
  // private sunburstData: any[] = [];
  private weekdayData: any[] = [];
  private dayNightData: any[][];
  private comparisonData: any[] = [];
  private filteredClassInstances: ClassInstance[] = [];

  update$: Subject<any> = new Subject();

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
      height: 700
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
    series: [
      {
        type: "sunburst",
        allowTraversingTree: true,
        cursor: 'pointer',
        data: this.sunburstData
      }
    ]
  };

  // ----

  // comparison chart
  comparisonXlabel = 'Jahr';
  comparisonYlabel = 'Anzahl Tätigkeiten';


  @ViewChild('lineChart', { static: false }) lineChart: any;

  selectedYaxis: string;
  selectedYear: string;

  comparisonYear: string;
  yearsMap: Map<string, number>;


  timelineFilterFrom: Date;
  timelineFilterTo: Date

  public newTimelineChartData: { name: string, series: { name: Date, value: number }[] }[];

  constructor(private loginService: LoginService,
    private arrayService: ArrayService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
    private volunteerService: CoreVolunteerService
  ) {
    this.newTimelineChartData = [{ name: 'Tätigkeit', series: [] }];
  }


  /*
    @HostListener("mouseup", ["$event"]) onMouseUp(event: Event) {
      console.log(event.type);
      console.log(event.target)
    }
  
    @HostListener('lineChart: mouseleave', ['$event'])
    onDragStart(ev:Event) {
        console.log(ev);
    }
  */

  ngOnInit() {
    // Splice in transparent for the center circle
    Highcharts.getOptions().colors.splice(0, 0, 'transparent');

    this.selectedYaxis = 'Dauer';
    this.selectedYear = 'gesamt';
    this.comparisonYear = '2015';

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = participant as Volunteer;

      Promise.all([
        this.marketplaceService.findAll().toPromise(),
        this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      ]).then((values: any[]) => {

        // TODO: 
        this.marketplace = values[0][0];

        this.classInstanceService.getClassInstancesByArcheType(this.marketplace, 'TASK').toPromise().then((ret: ClassInstance[]) => {
          if (!isNullOrUndefined(ret)) {
            this.classInstances = ret;

            this.tableDataSource.data = this.classInstances;
            this.tableDataSource.paginator = this.paginator;

            this.filteredClassInstances = [...this.classInstances];
            this.generateTimelineData();
            this.generateOtherChartsData();
            this.generateStaticChartData();

          }
        });
      });
    });

    Highcharts.chart('sunburstChart', this.chartOptions);
    }

  /*
    onTimelineFilter(event) {
      console.error(event);
  
      this.filteredClassInstances = this.classInstances.filter(c => {
        moment(c.properties[14].values[0]).isAfter(moment(event[0])) &&
          moment(c.properties[14].values[0]).isBefore(moment(event[1]))
      });
  
      this.generateOtherChartsData();
      console.error(this.filteredClassInstances);
  
      // visualize selected area
    }
  */

  onYaxisChange(val: string) {
    this.selectedYaxis = val;
    this.yAxisLabel = val;
    this.generateTimelineData();
    this.generateOtherChartsData();
  }

  onYearChange(value) {
    this.selectedYear = value;

    if (this.selectedYear === 'gesamt') {
      this.filteredClassInstances = [...this.classInstances];
    } else {
      this.filteredClassInstances = this.classInstances.filter(c => {
        return (moment(c.properties[14].values[0]).isSame(moment(this.selectedYear), 'year'));
      });
    }
    this.generateTimelineData();
    this.generateOtherChartsData();

  }

  generateTimelineData() {
    let data1 = [];

    // timeline
    let timelineList = this.filteredClassInstances.map(ci => {
      let value;
      (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[16].values[0];
      return ({ date: new Date(ci.properties[14].values[0]).setHours(0, 0, 0, 0), value: value })
    });


    let timelineMap: Map<number, number> = new Map<number, number>();
    timelineList.forEach(t => {
      if (timelineMap.get(t.date)) {
        timelineMap.set(t.date, Number(timelineMap.get(t.date)) + Number(t.value))
      } else {
        timelineMap.set(t.date, t.value);
      }
    });

    Array.from(timelineMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null) {
        data1.push({ name: new Date(entry[0]), value: Number(entry[1]) });
      }
    });

    this.newTimelineChartData[0].series = data1;
    this.newTimelineChartData = [...this.newTimelineChartData];

  }




  filterApply() {
    let a = new Date(this.lineChart.xDomain[0]);
    let b = new Date(this.lineChart.xDomain[1]);

    if (a != this.timelineFilterFrom || b != this.timelineFilterTo) {
      this.timelineFilterFrom = a;
      this.timelineFilterTo = b;


      this.filteredClassInstances = this.classInstances.filter(c => {
        return (moment(c.properties[14].values[0]).isAfter(moment(this.timelineFilterFrom)) &&
          moment(c.properties[14].values[0]).isBefore(moment(this.timelineFilterTo)));
      });

      this.generateOtherChartsData();
    }


  }

  generateOtherChartsData() {
    let data2 = [];
    let data3 = [];
    // sunburst

    // donut: Wochentag
    let weekdayList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[16].values[0];
        return ({ weekday: moment(ci.properties[14].values[0]).format('dddd'), value: value })
      });

    let weekdayMap: Map<string, number> = new Map<string, number>();
    weekdayList.forEach(t => {
      if (weekdayMap.get(t.weekday)) {
        weekdayMap.set(t.weekday, Number(weekdayMap.get(t.weekday)) + Number(t.value))
      } else {
        weekdayMap.set(t.weekday, t.value);
      }
    });

    Array.from(weekdayMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null) {
        data2.push({ name: entry[0], value: entry[1] })
      }
    });
    this.weekdayData = [...data2];


    // donut: day night
    let dayNightList = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[16].values[0];

        let key;
        let hours = new Date(ci.properties[14].values[0]).getHours();
        (hours >= 7 && hours <= 18) ? key = 'Tag' : key = 'Nacht';

        return ({ dayNight: key, value: value })
      });

    let dayNightMap: Map<string, number> = new Map<string, number>();
    dayNightList.forEach(t => {
      if (dayNightMap.get(t.dayNight)) {
        dayNightMap.set(t.dayNight, Number(dayNightMap.get(t.dayNight)) + Number(t.value))
      } else {
        dayNightMap.set(t.dayNight, t.value);
      }
    });

    Array.from(dayNightMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null) {
        data3.push({ name: entry[0], value: entry[1] })
      }
    });

    this.dayNightData = [...data3];

    // sunburst data
    let list = this.filteredClassInstances
      .map(ci => {
        let value;
        (this.selectedYaxis === 'Anzahl') ? value = 1 : value = ci.properties[16].values[0];

        return ({ tt1: ci.properties[4].values[0], tt2: ci.properties[5].values[0], value: value })
      });

    let distinctSet1 = new Set();

    list.forEach(l => {
      distinctSet1.add(l.tt1);

    });
    let distinctTaskType1 = Array.from(distinctSet1);

    let sunburst = [];
    // insert 0 entry
    sunburst.push({ id: '0', parent: '', name: 'Einsatzarten' });


    // insert tt1 entries
    distinctTaskType1.forEach((tt1, index) => {
      sunburst.push({ id: (index + 1).toString(), parent: '0', name: tt1, color: this.ngxColorsCool[index] });
    });

    // insert tt2 entries (for each tt1 separetly)
    distinctTaskType1.forEach(tt1 => {

      let tt1List = list.filter(l => {
        return (tt1 === l.tt1)
      });

      let tt2Map: Map<string, number> = new Map<string, number>();
      tt1List.forEach(entry => {
        if (tt2Map.get(entry.tt2)) {
          tt2Map.set(entry.tt2, Number(tt2Map.get(entry.tt2)) + Number(entry.value))
        } else {
          tt2Map.set(entry.tt2, entry.value);
        }
      });

      let indexTt1 = distinctTaskType1.indexOf(tt1) + 1;

      Array.from(tt2Map.entries()).forEach((entry, index) => {
        if (entry[0] != null && entry[1] != null) {
          sunburst.push({ id: indexTt1 + '-' + (index + 1).toString(), parent: indexTt1.toString(), name: entry[0], value: Number(entry[1]) });
        }
      });
    });

    this.sunburstData = [...sunburst];

    this.chartOptions.series = [
      {
        type: "sunburst",
        allowTraversingTree: true,
        cursor: 'pointer',
        data: this.sunburstData
      }
    ];

    Highcharts.chart('sunburstChart', this.chartOptions);

    // ---



    this.tableDataSource.data = this.filteredClassInstances;
    this.paginator._changePageSize(this.paginator.pageSize);
  }

  generateStaticChartData() {
    // yearComparison

    let yearsList = this.classInstances.map(ci => {
      return ({ year: (new Date(ci.properties[14].values[0]).getFullYear()).toString(), value: 1 });
    });

    this.yearsMap = new Map<string, number>();
    yearsList.forEach(t => {
      if (this.yearsMap.get(t.year)) {
        this.yearsMap.set(t.year, Number(this.yearsMap.get(t.year)) + Number(t.value))
      } else {
        this.yearsMap.set(t.year, t.value);
      }
    });

    let comparisonYearData = this.yearsMap.get(this.comparisonYear);

    let data4 = [];
    Array.from(this.yearsMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && entry[1] > 5) {
        data4.push({ name: entry[0], value: Number(entry[1]) - comparisonYearData });
      }
    });

    this.comparisonData = [...data4];


    // ----
  }

  onComparisonYearChanged(value) {
    this.comparisonYear = value;

    let comparisonYearData = this.yearsMap.get(this.comparisonYear);
    let data = [];

    Array.from(this.yearsMap.entries()).forEach(entry => {
      if (entry[0] != null && entry[1] != null && entry[1] > 5) {
        data.push({ name: entry[0], value: Number(entry[1]) - comparisonYearData });
      }
    });

    this.comparisonData = [...data];
  }




}