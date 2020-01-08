import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '../../../../../@fuse/animations';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { Marketplace } from '../../_model/marketplace';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { ClassInstance } from '../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { Volunteer } from '../../_model/volunteer';
import * as moment from 'moment';
import { CIP } from '../../_model//classInstancePropertyConstants';


@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  animations: fuseAnimations

})
export class CompetenciesComponent implements OnInit {
  private volunteer: Participant;
  private marketplace: Marketplace;
  classInstances: ClassInstance[];

  trainingData: any[];

  // chart options
  colorScheme = 'cool';
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  legendTitle: string = ' ';
  showXAxisLabel: boolean = false;
  showYAxisLabel: boolean = true;
  yAxisLabel: string = 'Stunden';
  animations: boolean = true;

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

  constructor(private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private volunteerService: CoreVolunteerService
  ) { }


  ngOnInit() {
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


            this.generateChartData();

          }
        });
      });
    });
  }

  onSelect(event) {
    console.log(event);
  }

  generateChartData() {
    let list = this.classInstances.map(ci => {
      return ({
        year: (new Date(ci.properties[this.TASK_DATE_FROM].values[0]).getFullYear()).toString(),
        duration: ci.properties[this.TASK_DURATION].values[0],
        tt1: ci.properties[this.TASK_TYPE_1].values[0],
        tt2: ci.properties[this.TASK_TYPE_2].values[0]
      });
    }).filter(entry => {
      return entry.tt1 == 'Ausbildung (aktiv)';
    });

    let uniqueYears = [...new Set(list.map(item => item.year))];
    let data = [];

    uniqueYears.forEach(year => {
      let oneYear = list.filter(entry => {
        return entry.year == year;
      });

      let map = new Map<string, number>();
      let data2 = [];

      oneYear.forEach(t => {
        if (map.get(t.tt2)) {
          map.set(t.tt2, Number(map.get(t.tt2)) + Number(t.duration))
        } else {
          map.set(t.tt2, Number(t.duration));
        }

        data2 = [];
        Array.from(map.entries()).forEach(entry => {
          if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
            data2.push({ name: entry[0], value: Number(entry[1]) });
          }
        });
      });
      data.push({ name: year, series: data2 });

    });

    this.trainingData = [...data];
  }


  
}