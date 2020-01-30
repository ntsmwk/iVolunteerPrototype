import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../_service/login.service';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { StoredChartService } from '../../_service/stored-chart.service';
import { Volunteer } from '../../_model/volunteer';
import { Participant } from '../../_model/participant';
import { CIP } from '../../_model/classInstancePropertyConstants';
import { isNullOrUndefined } from 'util';
import { ClassInstance } from '../../_model/meta/Class';
import { StoredChart } from '../../_model/stored-chart';


@Component({
  selector: 'fuse-management-summary',
  templateUrl: './achievements-management-summary.component.html',
  styleUrls: ['./achievements-management-summary.component.scss']
})
export class AchievementsManagementSummaryComponent implements OnInit {
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

  // comparison chart
  comparisonXlabel = 'Jahr';
  comparisonYlabel = 'Anzahl Tätigkeiten';
  colorScheme = 'cool';
  colors = [
    {
      name: 'Feuerwehr',
      value: '#a72920'
    },
    {
      name: 'Rotes Kreuz',
      value: '#b2b2b2'
    },
    {
      name: 'Musikverein',
      value: '#05913A'
    }
  ];

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
  autoScale = true;
  legend = false;
  legendPosition = 'below';
  tooltipDisabled = false;

  private comparisonData: any[] = [];
  comparisonYear: string;
  volunteer: any;
  marketplace: any;
  classInstances: any[];
  filteredClassInstances: any[];
  yearsMap: any;


  // ---- 
  // cards 
  cardColor: string = '#232837';

  durationTotal = [
    {
      "name": "Feuerwehr",
      "value": 3549
    },
    {
      "name": "Rotes Kreuz",
      "value": 883
    },
    {
      "name": "Musikverein",
      "value": 1632
    },
  ];

  numberTotal = [
    {
      "name": "Feuerwehr",
      "value": 1207
    },
    {
      "name": "Rotes Kreuz",
      "value": 401
    },
    {
      "name": "Musikverein",
      "value": 425
    },
  ];

  duration2019 = [
    {
      "name": "Feuerwehr",
      "value": 346
    },
    {
      "name": "Rotes Kreuz",
      "value": 83
    },
    {
      "name": "Musikverein",
      "value": 173
    },
  ];

  number2019 = [
    {
      "name": "Feuerwehr",
      "value": 104
    },
    {
      "name": "Rotes Kreuz",
      "value": 31
    },
    {
      "name": "Musikverein",
      "value": 49
    },
  ];

  sumDurationTotal: number;
  sumNumberTotal: number;
  sumDuration2019: number;
  sumNumber2019: number;

  fakeDataMusic = [
    {
      "name": "2012",
      "value": 0,
    },
    {
      "name": "2013",
      "value": 0,
    },
    {
      "name": "2014",
      "value": 0,
    },
    {
      "name": "2015",
      "value": 19,
    },
    {
      "name": "2016",
      "value": 18,
    },
    {
      "name": "2017",
      "value": 19,
    },
    {
      "name": "2018",
      "value": 29,
    },
    {
      "name": "2019",
      "value": 49,
    },
  ];

  fakeDataRk = [
    {
      "name": "2012",
      "value": 20,
    },
    {
      "name": "2013",
      "value": 13,
    },
    {
      "name": "2014",
      "value": 4,
    },
    {
      "name": "2015",
      "value": 3,
    },
    {
      "name": "2016",
      "value": 1,
    },
    {
      "name": "2017",
      "value": 36,
    },
    {
      "name": "2018",
      "value": 41,
    },
    {
      "name": "2019",
      "value": 31,
    },
  ];
  uniqueYears: any[];



  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
    private volunteerService: CoreVolunteerService,
    private storedChartService: StoredChartService
  ) { }

  ngOnInit() {
    this.comparisonYear = '2012';

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = participant as Volunteer;

      Promise.all([
        this.marketplaceService.findAll().toPromise(),
        this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      ]).then((values: any[]) => {

        // TODO: 
        this.marketplace = values[0][0];

        this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK').toPromise().then((ret: ClassInstance[]) => {
          if (!isNullOrUndefined(ret)) {
            this.classInstances = ret;

            this.classInstances.forEach((ci, index, object) => {
              if (ci.properties[this.TASK_DURATION].values[0] == 'null') {
                object.splice(index, 1);
              }
            });

            this.filteredClassInstances = [...this.classInstances];
            this.generateStaticChartData();



            this.sumDurationTotal = this.durationTotal.reduce((a, c) => a+c.value, 0);
            this.sumNumberTotal = this.numberTotal.reduce((a, c) => a+c.value, 0);
            this.sumDuration2019 = this.duration2019.reduce((a, c) => a+c.value, 0);
            this.sumNumber2019 = this.number2019.reduce((a, c) => a+c.value, 0);
          }
        });
      });
    });

  }

  onComparisonYearChanged(value) {
    this.comparisonYear = value;

    let comparisonYearDataFeuerwehr = this.yearsMap.get(this.comparisonYear);
    let comparisonYearDataMusikverein = this.fakeDataMusic.find(d => {
      return d.name === this.comparisonYear;
    }).value;

    let comparisonYearDataRk = this.fakeDataRk.find(d => {
      return d.name === this.comparisonYear;
    }).value;

    let data = [];
    let dataFinal = [];

    this.uniqueYears.forEach(curYear => {
      let currentYearDataFeuerwehr = this.yearsMap.get(curYear);
      let currentYearDataMusikverein = this.fakeDataMusic.find(d => {
        return d.name === curYear;
      }).value;

      let currentYearDataRk = this.fakeDataRk.find(d => {
        return d.name === curYear;
      }).value;

      data = [];
      data.push({ name: 'Feuerwehr', value: currentYearDataFeuerwehr - comparisonYearDataFeuerwehr });
      data.push({ name: 'Rotes Kreuz', value: currentYearDataRk - comparisonYearDataRk });
      data.push({ name: 'Musikverein', value: currentYearDataMusikverein - comparisonYearDataMusikverein });
      dataFinal.push({ name: curYear, series: data });
    });

    this.comparisonData = [...dataFinal];

  }

  generateStaticChartData() {
    // yearComparison
    let yearsList = this.classInstances.map(ci => {
      return ({ year: (new Date(ci.properties[this.TASK_DATE_FROM].values[0]).getFullYear()).toString(), value: 1 });
    });

    this.yearsMap = new Map<string, number>();
    yearsList.forEach(t => {
      if (this.yearsMap.get(t.year)) {
        this.yearsMap.set(t.year, Number(this.yearsMap.get(t.year)) + Number(t.value))
      } else {
        this.yearsMap.set(t.year, t.value);
      }
    });

    let comparisonYearDataFeuerwehr = this.yearsMap.get(this.comparisonYear);
    let comparisonYearDataMusikverein = this.fakeDataMusic.find(d => {
      return d.name === this.comparisonYear;
    }).value;
    let comparisonYearDataRk = this.fakeDataRk.find(d => {
      return d.name === this.comparisonYear;
    }).value;
    let data = [];
    let dataFinal = [];

    this.uniqueYears = [...new Set(yearsList.map(item => item.year))];
    this.uniqueYears.forEach(curYear => {

      let currentYearDataFeuerwehr = this.yearsMap.get(curYear);
      let currentYearDataMusikverein = this.fakeDataMusic.find(d => {
        return d.name === curYear;
      }).value;
      let currentYearDataRk = this.fakeDataRk.find(d => {
        return d.name === curYear;
      }).value;

      data = [];
      data.push({ name: 'Feuerwehr', value: currentYearDataFeuerwehr - comparisonYearDataFeuerwehr });
      data.push({ name: 'Rotes Kreuz', value: currentYearDataRk - comparisonYearDataRk });
      data.push({ name: 'Musikverein', value: currentYearDataMusikverein - comparisonYearDataMusikverein });
      dataFinal.push({ name: curYear, series: data });
    });

    this.comparisonData = [...dataFinal];
    // /yearComparision

  }

  exportChart(source: string) {
    let storedChart: StoredChart;

    switch (source) {
      case 'Vergleich':
        storedChart = new StoredChart('Vergleich der Anzahl an Tätigkeiten', 'ngx-charts-bar-vertical-2d', JSON.stringify(this.comparisonData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }

   valueFormattingDuration(c) {
    return `${(c)} Stunden`;
  }

  valueFormattingNumber(c) {
    return `${(c)} Tätigkeiten`;
  }

}