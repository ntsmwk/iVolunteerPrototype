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

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  animations: fuseAnimations

})
export class CompetenciesComponent implements OnInit {
  width: number = 1000;
  height: number = 300;
  view = [this.width, this.height];
  colorScheme = 'cool';
  schemeType = 'ordinal';
  showGridLines = true;
  animations = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Date';
  yAxisLabel = 'Duration [h]';
  noBarWhenZero = true;


  private volunteer: Participant;
  private marketplace: Marketplace;
  private returnedClassInstances: ClassInstance[];
  private dataSource = new MatTableDataSource<ClassInstance>();
  private timelineData: any[] = [];

  displayedColumns: string[] = ['taskName', 'taskDuration'];


  constructor(private loginService: LoginService,
    private arrayService: ArrayService,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private route: ActivatedRoute,
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
            this.dataSource.data.push(...ret);
            this.returnedClassInstances = this.dataSource.data;

            this.timelineData = this.generateChartData(this.returnedClassInstances);            

          }
        });
      });
    });


  }

  onFilter(event) {
    console.log('timeline filter', event);
  }

  generateChartData(classIntances: ClassInstance[]) {
    let data = [];

    for (let c of classIntances) {
      let date = new Date(c.properties[14].values[0]);
      let duration = c.properties[16].values[0];
      let id = c.id;

      if (duration != null && date != null) {
        data.push({ name: date, value: duration, extra: {id: id}});
      }
    }

    return data;

  }



}