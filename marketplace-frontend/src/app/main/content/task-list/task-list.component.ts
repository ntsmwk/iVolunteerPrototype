import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';

import { Task } from '../_model/task';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { LoginService } from '../_service/login.service';
import { TaskService } from '../_service/task.service';
import { fuseAnimations } from '@fuse/animations';
import { isNullOrUndefined } from 'util';
import { ClassInstance } from '../_model/meta/class';
import { MatPaginator, MatSort } from '@angular/material';
import { ClassInstanceService } from '../_service/meta/core/class/class-instance.service';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss'],
  animations: fuseAnimations

})
export class FuseTaskListComponent implements OnInit, AfterViewInit {

  marketplace: Marketplace;

  private classInstances: ClassInstance[] = [];
  private tableDataSource = new MatTableDataSource<ClassInstance>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  private displayedColumns: string[] = ['taskName', 'taskType1', 'taskType2', 'taskDateFrom', 'taskDuration', 'verified'];

  private participant: Participant;

  // IVOLUNTEER_UUID = CIP.IVOLUNTEER_UUID;
  // IVOLUNTEER_SOURCE = CIP.IVOLUNTEER_SOURCE;
  // TASK_ID = CIP.TASK_ID;
  // TASK_NAME = CIP.TASK_NAME;
  // TASK_TYPE_1 = CIP.TASK_TYPE_1;
  // TASK_TYPE_2 = CIP.TASK_TYPE_2;
  // TASK_TYPE_3 = CIP.TASK_TYPE_3;
  // TASK_TYPE_4 = CIP.TASK_TYPE_4;
  // TASK_DESCRIPTION = CIP.TASK_DESCRIPTION;
  // ZWECK = CIP.ZWECK;
  // ROLLE = CIP.ROLLE;
  // RANG = CIP.RANG;
  // PHASE = CIP.PHASE;
  // ARBEITSTEILUNG = CIP.ARBEITSTEILUNG;
  // EBENE = CIP.EBENE;
  // TASK_DATE_FROM = CIP.TASK_DATE_FROM;
  // TASK_DATE_TO = CIP.TASK_DATE_TO;
  // TASK_DURATION = CIP.TASK_DURATION;
  // TASK_LOCATION = CIP.TASK_LOCATION;
  // TASK_GEO_INFORMATION = CIP.TASK_GEO_INFORMATION;


  constructor(private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private classInstanceService: ClassInstanceService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.loadAllTasks();
  }

  onRowSelect(task: Task) {
    // TODO @ALEX if edit of classInstances works ...
    // this.router.navigate(['/main/task/' + task.marketplaceId + '/' + task.id]);
  }

  private loadAllTasks() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;

          this.classInstanceService.getClassInstancesByArcheType(this.marketplace, 'TASK', this.participant.username === 'MVS' ? 'MV' : 'FF').toPromise().then((ret: ClassInstance[]) => {
            if (!isNullOrUndefined(ret)) {
              this.classInstances = ret;
              this.paginator.length = this.classInstances.length;
              this.tableDataSource.data = this.classInstances;
              this.tableDataSource.paginator = this.paginator;

            }
          });
        }
      });
    });
  }

  addTask() {
    this.router.navigate(['/main/task-select']);
  }

  private isFF() {
    return this.participant.username == 'FFA';
  }

  private isMV() {
    return this.participant.username === 'MVS';
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }

}
