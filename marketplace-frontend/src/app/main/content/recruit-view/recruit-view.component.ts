import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { StoredChartService } from '../_service/stored-chart.service';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Marketplace } from '../_model/marketplace';
import { StoredChart } from '../_model/stored-chart';
import { isNullOrUndefined } from 'util';



@Component({
  selector: 'recruit-view',
  templateUrl: './recruit-view.component.html',
  styleUrls: ['./recruit-view.component.scss'],
})
export class RecruitViewComponent implements OnInit {

  // TODO
  dataSourceComp = new MatTableDataSource<any>();
  displayedColumns = ['label', 'issuer', 'date1', 'evidence', 'details', 'date2', 'status'];
  marketplace: Marketplace;
  participant: Participant;
  duringVerify: boolean;
  verifyStage: number;
  charts: StoredChart[];

  weekdayData;


  constructor(
    private storedChartService: StoredChartService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
  ) {
  }

  ngOnInit() {

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
      this.storedChartService.findAll(this.marketplace).toPromise().then((storedCharts: StoredChart[]) => {
        this.charts = storedCharts;

        if (this.charts.findIndex(c => c.title === "Wochentag") >= 0) {
          this.weekdayData = JSON.parse(this.charts.find(c => c.title === "Wochentag").data);
        }
      });
    });



    // TODO
    // this.dataSourceComp.data = COMP_DATA_NEW;

  }


  showWochentagDiagram() {
    return !isNullOrUndefined(this.weekdayData);
  }

  pressedVerifyAll() {

  }
}