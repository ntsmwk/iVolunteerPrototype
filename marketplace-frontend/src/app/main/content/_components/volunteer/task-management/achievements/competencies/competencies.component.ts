import { Component, OnInit, Input, SimpleChanges } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { LoginService } from "app/main/content/_service/login.service";
import { StoredChartService } from "app/main/content/_service/stored-chart.service";
import { StoredChart } from "app/main/content/_model/stored-chart";
import { User } from "app/main/content/_model/user";

@Component({
  selector: "fuse-competencies",
  templateUrl: "./competencies.component.html",
  styleUrls: ["./competencies.component.scss"],
  animations: fuseAnimations,
})
export class CompetenciesComponent implements OnInit {
  private volunteer: User;
  private marketplace: Marketplace;

  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() selectedTenants: Tenant[];

  trainingData: any[];
  trainingData2: any[];
  taskData: any[];

  // allData: any[];
  // currYearData: any[];
  // lastYearData: any[];
  // meanYearData: any[];

  // chart options
  colorScheme = "cool";
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  legendTitle: string = " ";
  showXAxisLabel: boolean = false;
  showYAxisLabel: boolean = true;
  yAxisLabel1: string = "Stunden";
  yAxisLabel2: string = "Anzahl";
  animations: boolean = true;

  tt1Lernbereitschaft: string[] = ["Ausbildung (aktiv)", "Probe"];
  dateLocale = new Intl.DateTimeFormat("de-AT", { month: "short" });

  constructor(
    private loginService: LoginService,
    private storedChartService: StoredChartService
  ) {}

  async ngOnInit() {
    this.volunteer = <User>await this.loginService.getLoggedIn().toPromise();
  }

  ngOnChanges(changes: SimpleChanges) {
    // console.error('competencies', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case "classInstanceDTOs": {
            if (typeof changes.classInstanceDTOs.currentValue != "undefined") {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;

              this.classInstanceDTOs = this.classInstanceDTOs.sort(
                (a, b) => a.dateFrom.valueOf() - b.dateFrom.valueOf()
              );

              this.generateChartData();

              // this.calcMean();
              // this.currYearData = [... this.getYearData('2019')];
              // this.lastYearData = this.getYearData('2018');
              // this.meanYearData = this.getYearData('mean');
            }
            break;
          }
          case "selectedTenants": {
            if (typeof changes.selectedTenants.currentValue != "undefined") {
              this.selectedTenants = changes.selectedTenants.currentValue;
            }
          }
        }
      }
    }
  }

  onSelect(event) {
    console.log(event);
  }

  generateChartData() {
    let list = this.classInstanceDTOs.map((ci) => {
      return {
        year: new Date(ci.dateFrom).getFullYear().toString(),
        month: this.dateLocale.format(new Date(ci.dateFrom)),
        duration: ci.duration,
        tt1: ci.taskType1,
        tt2: ci.taskType2,
        tt3: ci.taskType3,
      };
    });

    // Ausbildung
    let filteredList = list.filter((entry) => {
      return this.tt1Lernbereitschaft.indexOf(entry.tt1) >= 0;
    });

    let uniqueYears = [...new Set(filteredList.map((item) => item.year))];

    let dataA1 = [];

    if (uniqueYears.length === 1) {
      // sort by month

      let uniqueMonths = [];
      for (var month = 0; month < 12; month++) {
        var d = new Date(Date.UTC(2000, month, 1, 0, 0, 0));
        uniqueMonths.push(this.dateLocale.format(d));
      }

      uniqueMonths.forEach((month) => {
        let currentMonthList = filteredList.filter((entry) => {
          return entry.month == month;
        });

        let map = new Map<string, number[]>(); // 0: number, 1: duration
        let data2 = [];

        currentMonthList.forEach((t) => {
          if (t.duration != null) {
            if (map.get(t.tt2)) {
              map.set(t.tt2, [
                Number(map.get(t.tt2)[0]) + 1,
                Number(map.get(t.tt2)[1]) + Number(t.duration),
              ]);
            } else {
              map.set(t.tt2, [1, Number(t.duration)]);
            }
          }
        });

        data2 = [];
        Array.from(map.entries()).forEach((entry) => {
          data2.push({
            name: entry[0],
            value: Number(entry[1][1]),
            extra: { number: Number(entry[1][0]) },
          });
        });
        dataA1.push({ name: month, series: data2 });
      });
    } else {
      uniqueYears.forEach((year) => {
        let currentYearList = filteredList.filter((entry) => {
          return entry.year == year;
        });

        let map = new Map<string, number[]>(); // 0: number, 1: duration
        let data2 = [];

        currentYearList.forEach((t) => {
          if (t.duration != null) {
            if (map.get(t.tt2)) {
              map.set(t.tt2, [
                Number(map.get(t.tt2)[0]) + 1,
                Number(map.get(t.tt2)[1]) + Number(t.duration),
              ]);
            } else {
              map.set(t.tt2, [1, Number(t.duration)]);
            }
          }
        });

        data2 = [];
        Array.from(map.entries()).forEach((entry) => {
          data2.push({
            name: entry[0],
            value: Number(entry[1][1]),
            extra: { number: Number(entry[1][0]) },
          });
        });
        dataA1.push({ name: year, series: data2 });
      });
    }

    for (let i = 0; i < dataA1.length; i++) {
      dataA1[i].series.sort((a, b) => b.name.localeCompare(a.name));
    }
    this.trainingData = [...dataA1];

    // /Ausbildung

    // Ausbildung2
    let dataA2 = [];

    let uniqueTt2 = [...new Set(filteredList.map((item) => item.tt2))];

    uniqueTt2.forEach((tt2) => {
      let oneTt2 = list.filter((entry) => {
        return entry.tt2 == tt2;
      });

      let map = new Map<string, number[]>(); // 0: number, 1: duration
      let data2 = [];

      oneTt2.forEach((t) => {
        if (t.duration != null) {
          if (uniqueYears.length === 1) {
            if (map.get(t.month)) {
              map.set(t.month, [
                Number(map.get(t.month)[0]) + 1,
                map.get(t.month)[1] + Number(t.duration),
              ]);
            } else {
              map.set(t.month, [1, Number(t.duration)]);
            }
          } else {
            if (map.get(t.year)) {
              map.set(t.year, [
                Number(map.get(t.year)[0]) + 1,
                map.get(t.year)[1] + Number(t.duration),
              ]);
            } else {
              map.set(t.year, [1, Number(t.duration)]);
            }
          }
        }
      });

      data2 = [];
      Array.from(map.entries()).forEach((entry) => {
        data2.push({
          name: entry[0],
          value: Number(entry[1][0]),
          extra: { duration: Number(entry[1][1]) },
        });
      });
      dataA2.push({ name: tt2, series: data2 });
    });

    this.trainingData2 = [...dataA2];
    // /Ausbildung2

    // Verschiedene Tätigkeiten (TT2)
    uniqueTt2 = [...new Set(list.map((item) => item.tt2))];
    let tt2Data = [];

    uniqueTt2.forEach((tt2) => {
      let oneTt2 = list.filter((entry) => {
        return entry.tt2 == tt2;
      });

      let map = new Map<string, number[]>(); // 0: number, 1: duration
      let data2 = [];

      oneTt2.forEach((t) => {
        if (t.duration != null) {
          if (uniqueYears.length === 1) {
            if (map.get(t.month)) {
              map.set(t.month, [
                Number(map.get(t.month)[0]) + 1,
                map.get(t.month)[1] + Number(t.duration),
              ]);
            } else {
              map.set(t.month, [1, Number(t.duration)]);
            }
          } else {
            if (map.get(t.year)) {
              map.set(t.year, [
                Number(map.get(t.year)[0]) + 1,
                map.get(t.year)[1] + Number(t.duration),
              ]);
            } else {
              map.set(t.year, [1, Number(t.duration)]);
            }
          }
        }
      });

      data2 = [];
      Array.from(map.entries()).forEach((entry) => {
        data2.push({
          name: entry[0],
          value: Number(entry[1][1]),
          extra: { number: Number(entry[1][0]) },
        });
      });
      tt2Data.push({ name: tt2, series: data2 });
    });

    this.taskData = [...tt2Data];
    // /Verschiedene Tätigkeiten (TT2)
  }

  exportChart(source: string) {
    let storedChart: StoredChart;

    switch (source) {
      case "Stunden":
        storedChart = new StoredChart(
          "STUNDEN absolvierter Ausbildungen",
          "ngx-charts-bar-vertical-stacked",
          JSON.stringify(this.trainingData),
          this.volunteer.id
        );
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;

      case "Anzahl":
        storedChart = new StoredChart(
          "ANZAHL absolvierter Ausbildungen",
          "ngx-charts-line-chart",
          JSON.stringify(this.trainingData2),
          this.volunteer.id
        );
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;

      case "Taetigkeitsart":
        storedChart = new StoredChart(
          "Engagement in verschiedenen Tätigkeitsarten im Zeitverlauf",
          "ngx-charts-area-chart-stacked",
          JSON.stringify(this.taskData),
          this.volunteer.id
        );
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }

  // calcMean() {
  //   let mean = new Map<string, number[]>(); // name -> [duration, number]
  //   let cntYears = 0;

  //   this.trainingData.forEach(td => {
  //     if (td.name != '2019') { // minus current year: String(new Date().getFullYear()-1)
  //       ++cntYears;
  //       td.series.forEach(s => {
  //         if (mean.get(s.name)) {
  //           mean.set(s.name, [mean.get(s.name)[0] + s.value, mean.get(s.name)[1] + s.extra.number]);
  //         } else {
  //           mean.set(s.name, [s.value, s.extra.number]);
  //         }
  //       });
  //     }
  //   });

  //   Array.from(mean.keys()).forEach(e => {
  //     mean.set(e, [mean.get(e)[0] / cntYears, mean.get(e)[1] / cntYears]);
  //   });

  //   let data = [];
  //   Array.from(mean.entries()).forEach(entry => {
  //     data.push({ name: entry[0], value: entry[1][0], extra: { number: entry[1][1] } });
  //   });

  //   this.allData = [...this.trainingData];
  //   this.allData.push({ name: 'mean', series: data });
  // }

  // getYearData(year: string) {
  //   return this.allData.filter(a => {
  //     return a.name === year
  //   }).map(b => {
  //     let data = [];
  //     b.series.forEach(c => {
  //       data.push({ name: c.name, duration: c.value, number: c.extra.number });
  //     });
  //     return data;
  //   });
  // }
}
