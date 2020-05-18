import {
  Component,
  OnInit,
  OnChanges,
  AfterViewInit,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  SimpleChanges,
} from "@angular/core";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import {
  MatPaginator,
  MatSort,
  MatTableDataSource,
  Sort,
} from "@angular/material";
import * as moment from "moment";
import * as Highcharts from "highcharts";
import HC_sunburst from "highcharts/modules/sunburst";
import { Router } from "@angular/router";

HC_sunburst(Highcharts);

@Component({
  selector: "app-sunburst-table",
  templateUrl: "./sunburst-table.component.html",
  styleUrls: ["./sunburst-table.component.scss"],
})
export class SunburstTableComponent
  implements OnInit, OnChanges, AfterViewInit {
  @Input() classInstanceDTOs: ClassInstanceDTO[];
  @Input() timelineFilter: { from: Date; to: Date };

  @Input() selectedYear: string;
  @Input() selectedYaxis: string;

  @Input() selectedTaskType: string;
  @Output() selectedTaskTypeChange = new EventEmitter<string>();

  filteredClassInstanceDTOs: ClassInstanceDTO[];

  prevNodeLevel: number = null;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  tableDataSource = new MatTableDataSource<ClassInstanceDTO>();
  displayedColumns: string[] = ["taskName", "taskDateFrom", "taskDuration"];

  sunburstData = [];
  ngxColorsCool = [
    "#a8385d",
    "#7aa3e5",
    "#a27ea8",
    "#aae3f5",
    "#adcded",
    "#a95963",
    "#8796c0",
    "#7ed3ed",
    "#50abcc",
    "#ad6886",
  ];

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {
    chart: {
      height: 900,
      margin: [0, 0, 0, 0],
    },
    title: {
      text: undefined,
    },
    tooltip: {
      valueDecimals: 2,
    },
    credits: {
      enabled: false,
    },
    plotOptions: {
      series: {
        dataLabels: {
          enabled: true,
          style: {
            fontSize: "17px",
            color: "#ffffff",
          },
        },
      },
    },
    series: [
      {
        type: "sunburst",
        data: this.sunburstData,
        allowTraversingTree: true,
        cursor: "pointer",
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
      },
    ],
  };

  prevClickedNode: any = null;
  uniqueTt1: any[];
  uniqueTt2: any[];
  uniqueTt3: any[];

  tt1ColorMap: Map<any, any>;

  sunburstCenterName: string = "Tätigkeiten";

  constructor(private router: Router) {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.tableDataSource.paginator = this.paginator;

    Highcharts.getOptions().colors.splice(0, 0, "transparent");
    Highcharts.chart("sunburstChart", this.chartOptions).update({
      plotOptions: {
        series: {
          events: {
            click: (event) => {
              this.onSunburstChange(event);
            },
          },
        },
      },
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    // console.error('sunburst-table changes', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case "classInstanceDTOs": {
            if (typeof changes.classInstanceDTOs.currentValue != "undefined") {
              this.filteredClassInstanceDTOs = this.classInstanceDTOs;

              let list = this.filteredClassInstanceDTOs.map((ci) => {
                return {
                  tt1: ci.taskType1,
                  tt2: ci.taskType2,
                  tt3: ci.taskType3,
                };
              });
              this.uniqueTt1 = [...new Set(list.map((item) => item.tt1))];
              this.uniqueTt2 = [...new Set(list.map((item) => item.tt2))];
              this.uniqueTt3 = [...new Set(list.map((item) => item.tt2))];

              this.uniqueTt1.sort();
              this.tt1ColorMap = new Map();
              this.uniqueTt1.forEach((tt1, index) => {
                this.tt1ColorMap.set(
                  tt1,
                  this.ngxColorsCool[index % this.ngxColorsCool.length]
                );
              });

              this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.sort(
                (a, b) => {
                  return (
                    new Date(b.dateFrom).getTime() -
                    new Date(a.dateFrom).getTime()
                  );
                }
              );

              this.tableDataSource.data = this.filteredClassInstanceDTOs;
              this.tableDataSource.paginator = this.paginator;

              this.generateSunburstData();
            }
            break;
          }

          case "selectedYaxis": {
            if (
              typeof changes.selectedYaxis.currentValue != "undefined" &&
              typeof this.filteredClassInstanceDTOs != "undefined"
            ) {
              this.generateSunburstData();
            }
            break;
          }

          case "timelineFilter": {
            if (
              typeof changes.timelineFilter.currentValue != "undefined" &&
              typeof this.filteredClassInstanceDTOs != "undefined"
            ) {
              if (this.timelineFilter.from != null) {
                this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
                  (c) => {
                    return (
                      moment(c.dateFrom).isSameOrAfter(
                        moment(this.timelineFilter.from), 'day'
                      ) &&
                      moment(c.dateFrom).isSameOrBefore(
                        moment(this.timelineFilter.to), 'day'
                      )
                    );
                  }
                );

                if (this.selectedTaskType != null) {
                  this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
                    (c) => {
                      return c.taskType1 === this.selectedTaskType;
                    }
                  );
                }
                this.generateSunburstData();
              }
            }
            break;
          }

          case "selectedYear": {
            if (
              typeof changes.selectedYear.currentValue != "undefined" &&
              typeof this.filteredClassInstanceDTOs != "undefined"
            ) {
              if (this.selectedYear != null) {
                if (this.selectedYear === "Gesamt") {
                  this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
                } else {
                  this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
                    (c) => {
                      return moment(c.dateFrom).isSame(
                        moment(this.selectedYear),
                        "year"
                      );
                    }
                  );
                }

                if (this.selectedTaskType != null) {
                  this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
                    (c) => {
                      return c.taskType1 === this.selectedTaskType;
                    }
                  );
                }

                this.generateSunburstData();
              }
            }
            break;
          }
        }
      }
    }
  }

  generateSunburstData() {
    if (this.prevNodeLevel > 0 || this.prevNodeLevel != undefined) {
      this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
      this.selectedTaskType = null;
      this.updateSelectedTaskType(this.selectedTaskType);
    }

    let list = this.filteredClassInstanceDTOs.map((ci) => {
      let value: number;
      this.selectedYaxis === "Anzahl" ? (value = 1) : (value = ci.duration);
      return {
        tt1: ci.taskType1,
        tt2: ci.taskType2,
        tt3: ci.taskType3,
        value: value,
      };
    });

    let data = [];

    // insert 0 entry
    data.push({ id: "0", parent: "", name: this.sunburstCenterName });

    // insert tt1 entries
    this.uniqueTt1.forEach((tt1, index) => {
      // TODO use color map here, modulo, so to start from beginning...

      data.push({
        id: (index + 1).toString(),
        parent: "0",
        name: tt1,
        color: this.ngxColorsCool[index],
      });
    });

    // insert tt2 entries (for each tt1 separetly)
    this.uniqueTt1.forEach((tt1) => {
      let tt1List = list.filter((l) => {
        return tt1 === l.tt1;
      });

      let tt2Map: Map<string, number> = new Map<string, number>();
      tt1List.forEach((entry) => {
        if (tt2Map.get(entry.tt2)) {
          tt2Map.set(
            entry.tt2,
            Number(tt2Map.get(entry.tt2)) + Number(entry.value)
          );
        } else {
          tt2Map.set(entry.tt2, entry.value);
        }
      });

      let indexParent = this.uniqueTt1.indexOf(tt1) + 1;

      Array.from(tt2Map.entries()).forEach((entry, index) => {
        if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
          data.push({
            id: indexParent + "-" + (index + 1).toString(),
            parent: indexParent.toString(),
            name: entry[0],
            value: Number(entry[1]),
          });
        }
      });
    });

    // snapshot before any tt3 data is added
    let dataTt2 = [...data];

    // insert tt3 entries (for each tt2 separetly)
    this.uniqueTt2.forEach((tt2) => {
      let tt2List = list.filter((l) => {
        return tt2 === l.tt2;
      });

      let tt3Map: Map<string, number> = new Map<string, number>();
      tt2List.forEach((entry) => {
        if (tt3Map.get(entry.tt3)) {
          tt3Map.set(
            entry.tt3,
            Number(tt3Map.get(entry.tt3)) + Number(entry.value)
          );
        } else {
          tt3Map.set(entry.tt3, entry.value);
        }
      });

      let indexParent: string = null;
      dataTt2.forEach((d) => {
        if (d.name === tt2) {
          indexParent = d.id;
        }
      });

      Array.from(tt3Map.entries()).forEach((entry, index) => {
        if (entry[0] != null && entry[1] != null && !isNaN(entry[1])) {
          data.push({
            id: indexParent + "-" + (index + 1).toString(),
            parent: indexParent.toString(),
            name: entry[0],
            value: Number(entry[1]),
          });
        }
      });
    });

    this.sunburstData = [...data];
    this.chartOptions.series = [
      {
        type: "sunburst",
        allowTraversingTree: true,
        cursor: "pointer",
        data: this.sunburstData,
      },
    ];
    Highcharts.chart("sunburstChart", this.chartOptions);

    this.tableDataSource.data = this.filteredClassInstanceDTOs;
    this.tableDataSource.paginator = this.paginator;
  }

  onSunburstChange(event) {
    if (event.point.id === "0") {
      // Tätigkeitsart clicked
      this.prevClickedNode = null;
      this.selectedTaskType = null;

      this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
    } else {
      if (event.point.node.level < 4) {
        if (event.point.node.level <= this.prevNodeLevel) {
          // drillup

          let parent = null;
          this.sunburstData.forEach((d) => {
            if (d.id === event.point.parent) {
              parent = d;
            }
          });

          if (parent.name === this.sunburstCenterName) {
            this.selectedTaskType = null;
            this.prevClickedNode = null;
            this.prevNodeLevel = 1;
            this.filteredClassInstanceDTOs = [...this.classInstanceDTOs];
          } else {
            this.selectedTaskType = parent.name;
            this.prevClickedNode = event.point.name;
            this.prevNodeLevel = event.point.node.level;
            this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
              (c) => {
                return c.taskType1 === parent.name;
              }
            );
          }
        } else {
          // drilldown
          this.prevClickedNode = event.point.name;
          this.prevNodeLevel = event.point.node.level;

          if (this.uniqueTt1.indexOf(event.point.name) > -1) {
            // filter for tt1
            this.selectedTaskType = event.point.name;
            this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
              (c) => {
                return c.taskType1 === event.point.name;
              }
            );
          } else if (this.uniqueTt2.indexOf(event.point.name) > -1) {
            // filter for tt2
            this.filteredClassInstanceDTOs = this.classInstanceDTOs.filter(
              (c) => {
                return c.taskType2 === event.point.name;
              }
            );
            this.selectedTaskType = event.point.name;
          }
        }
      }
    }

    // TIME FILTER
    if (
      this.timelineFilter.from === null ||
      typeof this.timelineFilter.from === "undefined"
    ) {
      // filter by year
      if (this.selectedYear === "Gesamt") {
        this.filteredClassInstanceDTOs = [...this.filteredClassInstanceDTOs];
      } else {
        this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
          (c) => {
            return moment(c.dateFrom).isSame(moment(this.selectedYear), "year");
          }
        );
      }
    } else {
      // filter by timeline from to
      this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.filter(
        (c) => {
          return (
            moment(c.dateFrom).isSameOrAfter(moment(this.timelineFilter.from), 'day') &&
            moment(c.dateFrom).isSameOrBefore(moment(this.timelineFilter.to), 'day')
          );
        }
      );
    }

    this.filteredClassInstanceDTOs = this.filteredClassInstanceDTOs.sort(
      (a, b) => {
        return new Date(b.dateFrom).getTime() - new Date(a.dateFrom).getTime();
      }
    );

    this.updateSelectedTaskType(this.selectedTaskType);

    // update table
    this.tableDataSource.data = this.filteredClassInstanceDTOs;
    this.tableDataSource.paginator = this.paginator;
  }

  updateSelectedTaskType(selectedTaskType) {
    this.selectedTaskType = selectedTaskType;
    this.selectedTaskTypeChange.emit(selectedTaskType);
  }

  getStyle(tt1) {
    return {
      "background-color": this.tt1ColorMap.get(tt1) + "9B", // opacity
    };
  }

  sortData(sort: Sort) {
    this.tableDataSource.data = this.tableDataSource.data.sort((a, b) => {
      const isAsc = sort.direction === "asc";
      switch (sort.active) {
        case "taskName":
          return this.compare(a.name, b.name, isAsc);
        case "taskDateFrom":
          return this.compare(a.dateFrom, b.dateFrom, isAsc);
        case "taskDuration":
          return this.compare(a.duration, b.duration, isAsc);
        default:
          return 0;
      }
    });
  }

  compare(
    a: number | string | Date,
    b: number | string | Date,
    isAsc: boolean
  ) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  navigateToClassInstanceDetails(row) {
    this.router.navigate(["main/details/" + row.id]);
  }
}
