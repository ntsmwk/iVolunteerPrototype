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
import { Subject } from 'rxjs';
import * as shape from 'd3-shape';


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
  private sunburstData: any[] = [];
  private weekdayData: any[] = [];
  private dayNightData: any[][];
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

  @ViewChild('lineChart', { static: false }) lineChart: any;

  // sunburst
  // dounut

  selectedYaxis: string;
  selectedYear: string;

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
    this.selectedYaxis = 'Dauer';
    this.selectedYear = 'gesamt';

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

          }
        });
      });
    });
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
    this.timelineFilterFrom = new Date(this.lineChart.xDomain[0]);
    this.timelineFilterTo = new Date(this.lineChart.xDomain[1]);


    this.filteredClassInstances = this.classInstances.filter(c => {
      return (moment(c.properties[14].values[0]).isAfter(moment(this.timelineFilterFrom)) &&
        moment(c.properties[14].values[0]).isBefore(moment(this.timelineFilterTo)));
    });

    this.generateOtherChartsData();

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

    this.tableDataSource.data = this.filteredClassInstances;
    this.paginator._changePageSize(this.paginator.pageSize);
  }


  generateChartData() {
    let data1 = []
    let data2 = []

    switch (this.selectedYaxis) {
      case 'Dauer':




        // sunburst chart
        /*
        let stringData = '[';

        let list = this.filteredClassInstances
          .map(ci => {
            return ({ tt1: ci.properties[4].values[0], tt2: ci.properties[5].values[0], duration: ci.properties[16].values[0] })
          });

        let distinctTaskType1 = new Set();
        list.forEach(l => {
          distinctTaskType1.add(l.tt1);
        });
        console.error('distinct tt1', distinctTaskType1);

        distinctTaskType1.forEach(tt1 => {
          let tt1List = list.filter(l => {
            return (tt1 === l.tt1)
          });

          let tasktype2cnt: Map<string, number> = new Map<string, number>();
          tt1List.forEach(l => {
            if (tasktype2cnt.get(l.tt2)) {
              tasktype2cnt.set(l.tt2, Number(tasktype2cnt.get(l.tt2)) + Number(l.duration));
            } else {
              tasktype2cnt.set(l.tt2, l.duration);
            }
          });

          data2.push({name: tt1, children: 4})
          stringData.concat('{"name":"');
          stringData.concat(String(tt1));
          stringData.concat('","children":')
          Array.from(tasktype2cnt.entries()).forEach(entry => {
            if (entry[0] != null && entry[1] != null) {
              stringData.concat('{"name:""');
              stringData.concat(String(entry[0]));
              stringData.concat('","value:"');
              stringData.concat(String(entry[1]));
              stringData.concat('},')
             // data2.push({ name: tt1, children: { name: entry[0], value: entry[1] } })
            }
          });

          stringData.concat('},')

        });

        console.error('stringData', stringData);
        //console.error('data2', JSON.stringify(data2));
        // end sunburst
        */

        // 
        break;




    }

    // this.sunburstData = [...data2];
  }



  arbeitsteilungData = [
    {
      "name": "Einzeln",
      "value": 38
    },
    {
      "name": "Team",
      "value": 86
    }
  ]

  /*
  sunburstData = [
    {
      name: 'Einsatz',
      children: [
        {
          name: 'Technisch', children: [
            { name: 'T1', value: 50 },
            { name: 'T2', value: 60 },
            { name: 'T3', value: 20 }
          ]
        },
        {
          name: 'Brand', children: [
            { name: 'B1', value: 50 },
            { name: 'B2', value: 60 },
            { name: 'B3', value: 20 },
            { name: 'B4', value: 20 }
          ]
        },
        {
          name: 'Schadstoff', children: [
            { name: 'S1', value: 50 },
            { name: 'S2', value: 60 },
            { name: 'S3', value: 20 }
          ]
        }
      ]
    },
    {
      name: 'Übung',
      children: [
        { name: 'Schulung', value: 120 },
        { name: 'Übung', value: 120 }
      ]
    },
    {
      name: 'Bewerb',
      children: [
        { name: 'Prüfung', value: 120 },
        {
          name: 'Vorbereitung', children: [
            { name: 'APAS', value: 120 },
            { name: 'APTE', value: 120 }
          ]
        }
      ]
    },
    { name: 'Ausbildung', value: 120 },

    {
      name: 'Veranstaltung',
      children: [
        { name: 'Feuerwehrfest', value: 120 },
        { name: 'Ausflug', value: 120 },
        { name: 'Kirchgang', value: 120 },
      ]
    },
    {
      name: 'Verwaltung',
      children: [
        { name: 'Inspektion', value: 120 },
        { name: 'Besprechung', value: 120 },
      ]
    }
  ];


*/
}

