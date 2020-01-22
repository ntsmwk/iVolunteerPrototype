import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { StoredChartService } from '../_service/stored-chart.service';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Marketplace } from '../_model/marketplace';
import { StoredChart } from '../_model/stored-chart';
import { isNullOrUndefined } from 'util';
import { ClassInstance } from '../_model/meta/Class';
import { ClassInstanceService } from '../_service/meta/core/class/class-instance.service';
import { MatPaginator, MatSort } from '@angular/material';
import { CIP } from '../_model/classInstancePropertyConstants';



@Component({
  selector: 'recruit-view',
  templateUrl: './recruit-view.component.html',
  styleUrls: ['./recruit-view.component.scss'],
})
export class RecruitViewComponent implements OnInit, AfterViewInit {


  private tableDataSource = new MatTableDataSource<ClassInstance>([]);
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  // @ViewChild('paginator', {static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  private displayedColumns: string[] = ['taskType1', 'taskName', 'taskDateFrom', 'taskDuration'];
  marketplace: Marketplace;
  participant: Participant;
  duringVerify: boolean;
  verifyStage: number;
  charts: StoredChart[];
  private classInstances: ClassInstance[] = [];


  weekdayData;
  dayNightData;

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


  constructor(
    private storedChartService: StoredChartService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private classInstanceService: ClassInstanceService) {
  }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    Promise.all([
      this.marketplaceService.findAll().toPromise().then((marketplaces: Marketplace[]) => {
        if (!isNullOrUndefined(marketplaces)) {
          this.marketplace = marketplaces[0];
        }
      }),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
        this.participant = participant;
      })
    ]).then(() => {
      this.loadStoredCharts();
      this.loadTasks();
    });
  }


  private loadTasks() {
    this.classInstanceService.getClassInstancesByArcheType(this.marketplace, 'TASK').toPromise().then((ret: ClassInstance[]) => {
      if (!isNullOrUndefined(ret)) {
        this.classInstances = ret;
        this.tableDataSource.data = this.classInstances;
        this.paginator.length = this.classInstances.length;
        this.tableDataSource.paginator = this.paginator;
        // this.tableDataSource.paginator.length= this.classInstances.length;
      }
    });
  }

  private loadStoredCharts() {
    this.storedChartService.findAll(this.marketplace).toPromise().then((storedCharts: StoredChart[]) => {
      this.charts = storedCharts;
      if (this.charts.findIndex(c => c.title === "Wochentag") >= 0) {
        this.weekdayData = JSON.parse(this.charts.find(c => c.title === "Wochentag").data);
      }
      if (this.charts.findIndex(c => c.title == "Tageszeit") >= 0) {
        this.dayNightData = JSON.parse(this.charts.find(c => c.title === "Tageszeit").data);
      }
    });
  }

  showWochentagDiagram() {
    return !isNullOrUndefined(this.weekdayData);
  }

  pressedVerifyAll() {

  }
}