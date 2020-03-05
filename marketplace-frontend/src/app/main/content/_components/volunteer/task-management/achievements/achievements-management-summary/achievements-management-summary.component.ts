import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../../../../_service/login.service';
import { ClassInstanceService } from '../../../../../_service/meta/core/class/class-instance.service';
import { CoreMarketplaceService } from '../../../../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { CoreVolunteerService } from '../../../../../_service/core-volunteer.service';
import { StoredChartService } from '../../../../../_service/stored-chart.service';
import { Volunteer } from '../../../../../_model/volunteer';
import { Participant } from '../../../../../_model/participant';
import { isNullOrUndefined } from 'util';
import { ClassInstance, ClassInstanceDTO } from '../../../../../_model/meta/Class';
import { StoredChart } from '../../../../../_model/stored-chart';
import { TenantService } from '../../../../../_service/core-tenant.service';


@Component({
  selector: 'fuse-management-summary',
  templateUrl: './achievements-management-summary.component.html',
  styleUrls: ['./achievements-management-summary.component.scss']
})
export class AchievementsManagementSummaryComponent implements OnInit {

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
  volunteer: Volunteer;
  marketplace: any;
  classInstanceDTOs: ClassInstanceDTO[];
  filteredClassInstanceDTOs: ClassInstanceDTO[];
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

  private tenantName: string = 'FF_Eidenberg';
  private tenantId: string;

  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
    private volunteerService: CoreVolunteerService,
    private storedChartService: StoredChartService,
    private coreTenantService: TenantService
  ) { }

  ngOnInit() {
    this.comparisonYear = '2012';

    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {
      this.volunteer = volunteer;

      Promise.all([
        this.marketplaceService.findAll().toPromise(),
        this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
      ]).then((values: any[]) => {

        // TODO: 
        this.marketplace = values[0][0];

        this.classInstanceService.getUserClassInstancesByArcheType(this.marketplace, 'TASK', this.volunteer.id, this.volunteer.subscribedTenants).toPromise().then((ret: ClassInstanceDTO[]) => {
          if (!isNullOrUndefined(ret)) {
            //this.classInstanceDTOs = ret.filter(ci => ci.name=='PersonTask');
            this.classInstanceDTOs = ret;

            this.classInstanceDTOs.forEach((ci, index, object) => {
              if (ci.duration == null) {
                object.splice(index, 1);
              }
            });

            this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
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
    let yearsList = this.classInstanceDTOs.map(ci => {
      return ({ year: (new Date(ci.dateFrom).getFullYear()).toString(), value: 1 });
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