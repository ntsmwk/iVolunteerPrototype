import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '../../../../../@fuse/animations';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { Marketplace } from '../../_model/marketplace';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { ClassInstance } from '../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { MatTableDataSource } from '@angular/material/table';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { Volunteer } from '../../_model/volunteer';
import { ArrayService } from '../../_service/array.service';
import * as moment from 'moment';
import { Subject } from 'rxjs';

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
  private dataSource = new MatTableDataSource<ClassInstance>();
  private timelineData: any[] = [];
  private filteredClassInstances: ClassInstance[] = [];

  update$: Subject<any> = new Subject();

  // Todo: set chart properties for each type separately
  // timeline
  colorScheme = 'cool';
  schemeType = 'ordinal';
  showGridLines = true;
  animations = false;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Datum';
  yAxisLabel = 'Dauer [h]';
  noBarWhenZero = true;
  showLabels = true;
  // sunburst
  // dounut

  selectedYaxis: string;
  selectedYear: string;



  constructor(private loginService: LoginService,
    private arrayService: ArrayService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
    private volunteerService: CoreVolunteerService
  ) { }


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
            // for table, currently not used
            this.dataSource.data = this.classInstances;
            this.filteredClassInstances = [...this.classInstances];
            this.generateChartData();
          }
        });
      });
    });
  }


  onTimelineFilter(event) {
    //console.log('timeline filter', event);
    console.log(event[0]);
    console.log(event[1]);

    // TODO
    let filteredClassInstances = this.classInstances.filter(c => {
      moment(c.properties[14].values[0]).isAfter(moment(event[0])) &&
        moment(c.properties[14].values[0]).isBefore(moment(event[1]))
    });

    console.log(filteredClassInstances);

  }


  onYaxisChange(val: string) {
    this.selectedYaxis = val;
    this.generateChartData();
  }

  onYearChange(value) {
    this.selectedYear = value;
    let filtered: ClassInstance[] = [];

    if (this.selectedYear === 'gesamt') {
      this.filteredClassInstances = [...this.classInstances];
    } else {
      this.filteredClassInstances = this.classInstances.filter(c => {
        return (moment(c.properties[14].values[0]).isSame(moment(this.selectedYear), 'year'));
      });
    }
    this.generateChartData();
  }

  generateChartData() {
    let data = []
    switch (this.selectedYaxis) {
      case 'Dauer':
        this.filteredClassInstances.forEach(c => {
          let date = new Date(c.properties[14].values[0]).setHours(0, 0, 0, 0);
          let duration = c.properties[16].values[0];
          let id = c.id;
          if (duration != null && date != null) {
            data.push({ name: date, value: duration });
          }
        });
        break;

      case 'Anzahl':
        console.error(this.filteredClassInstances);
        let transformed = this.filteredClassInstances
          .map(ci => {
            return ({ name: new Date(ci.properties[14].values[0]).setHours(0, 0, 0, 0), value: 1 })
          })

        let groupedMap: Map<number, number> = new Map<number, number>();
        transformed.forEach(t => {
          if (groupedMap.get(t.name)) {
            groupedMap.set(t.name, groupedMap.get(t.name) + 1)
          } else {
            groupedMap.set(t.name, 1);
          }
        });
        console.error(groupedMap);
        Array.from(groupedMap.entries()).forEach(entry => data.push({ name: entry[0], value: entry[1] }));
        console.error(data);

        break;

      case 'Distanz':
        break;
    }

    this.timelineData = [...data];
  }


  // private groupBy(xs, key) {
  //   return xs.reduce(function(rv, x) {
  //     (rv[x[key]] = rv[x[key]] || []).push(x);
  //     return rv;
  //   }, {});
  // };





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

  sunburstChartData = [
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
        { name: 'Vorbereitung', children: [
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
}
