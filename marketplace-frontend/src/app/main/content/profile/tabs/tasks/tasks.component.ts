import { AfterViewInit, Component, OnInit } from '@angular/core';
import { fuseAnimations } from '../../../../../../@fuse/animations';
import * as Chart from 'chart.js';
import { Volunteer } from '../../../_model/volunteer';
import { VolunteerProfile, CompetenceTableRow } from '../../../_model/volunteer-profile';
import { VolunteerProfileService } from '../../../_service/volunteer-profile.service';
import { VolunteerRepositoryService } from '../../../_service/volunteer-repository.service';
import { isNullOrUndefined, isArray, error } from 'util';
import { TaskEntry } from '../../../_model/task-entry';
import { CompetenceEntry } from '../../../_model/competence-entry';
import { LoginService } from '../../../_service/login.service';
import { ArrayService } from '../../../_service/array.service';
import { CoreMarketplaceService } from '../../../_service/core-marketplace.service';
import { ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { Participant } from '../../../_model/participant';
import { CoreVolunteerService } from '../../../_service/core-volunteer.service';
import { isEmpty } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { MatTableDataSource } from '@angular/material';
import { DataSource } from '@angular/cdk/table';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'fuse-profile-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss'],
  providers: [DatePipe],
  animations: fuseAnimations
})
export class FuseProfileTaskComponent implements OnInit {
  canvas: any;
  ctx: any;

  dataSource: TasksDataSource;

  columns = [
    { columnDef: 'name', marketplace: null, columnType: 'text', header: 'Name', cell: (row: TaskEntry) => `${row.taskName}` },
  ];

  displayedColumns: any[];

  tasks: TaskEntry[] = [];
  combinedTasks: TaskEntry[] = [];


  private volunteer: Volunteer;
  private publicProfiles: Map<string, VolunteerProfile> = new Map();
  private privateProfile: VolunteerProfile;

  public commonProfile: VolunteerProfile;

  marketplaces: Marketplace[] = [];

  constructor(
    private arrayService: ArrayService,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private coreVolunteerService: CoreVolunteerService,
    private datePipe: DatePipe,
    private volunteerProfileService: VolunteerProfileService,
    private volunteerRepositoryService: VolunteerRepositoryService) {

  }

  ngOnInit() {
    this.columns.push({ columnDef: 'timestamp', marketplace: null, columnType: 'text', header: 'Achieved on', cell: (row: TaskEntry) => this.datePipe.transform(row.timestamp, 'dd.MM.yyyy') });
    this.loadFinishedTasks();
  }

  loadFinishedTasks() {
    this.commonProfile = new VolunteerProfile();
    this.commonProfile.taskList = [];
    this.privateProfile = new VolunteerProfile();

    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
      this.volunteer = volunteer;
      if (!isArray(selected_marketplaces)) {
        return;
      }
      selected_marketplaces.forEach((mp: Marketplace) => {
        this.columns.push({ columnDef: mp.id, marketplace: mp, columnType: 'function', header: mp.name, cell: (row: TaskEntry) => this.handleTaskMarketplace(mp, row) })
      });

      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id)
        .toPromise()
        .then((marketplaces: Marketplace[]) => {
          marketplaces
            .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
            .forEach(mp => {
              this.loadPublicVolunteerProfile(this.volunteer, mp);
            });
        });

      this.volunteerRepositoryService.findByVolunteer(volunteer)
        .toPromise()
        .then((volunteerProfile: VolunteerProfile) => {
          this.privateProfile = volunteerProfile;
          this.onLoadingComplete();
        });
    });
  }

  handleTaskMarketplace(marketplace: Marketplace, taskEntry: TaskEntry): string {
    if (this.privateProfileContains(taskEntry) && this.publicProfileContains(taskEntry, marketplace)) {
      return 'REVOKE'
    } else if (!this.privateProfileContains(taskEntry) && this.publicProfileContains(taskEntry, marketplace)) {
      return 'SYNC';
    } else if (this.privateProfileContains(taskEntry) && !this.publicProfileContains(taskEntry, marketplace)) {
      return 'PUBLISH';
    }
    throw new error('please reload page...');
  }

  private privateProfileContains(taskEntry: TaskEntry): boolean {
    if (this.privateProfile.taskList) {
      return this.privateProfile.taskList.find(c => c.taskId == taskEntry.taskId) != null;
    }
  }

  private publicProfileContains(taskEntry: TaskEntry, marketplace: Marketplace): boolean {
    if (this.publicProfiles.get(marketplace.id) && this.publicProfiles.get(marketplace.id).taskList) {
      return this.publicProfiles.get(marketplace.id).taskList.filter(c => c.taskId == taskEntry.taskId).length > 0;
    }
    return false;
  }

  private loadPublicVolunteerProfile(volunteer: Volunteer, marketplace: Marketplace) {
    return this.volunteerProfileService.findByVolunteer(volunteer, marketplace.url)
      .toPromise()
      .then((volunteerProfile: VolunteerProfile) => {
        if (volunteerProfile) {
          this.publicProfiles.set(marketplace.id, volunteerProfile);
          this.onLoadingComplete();
        }
      });
  }

  private onLoadingComplete() {
    if (this.privateProfile && this.privateProfile.taskList && this.privateProfile.taskList.length > 0) {
      this.combinedTasks = this.privateProfile.taskList;
    }
    this.publicProfiles.forEach((p, id) => {
      this.combinedTasks = this.arrayService.concat(this.combinedTasks, p.taskList);
    });
    this.dataSource = new TasksDataSource(this.combinedTasks);
    this.displayedColumns = this.columns.map(x => x.columnDef);
  }

  publishTask(taskEntry: TaskEntry, marketplace: Marketplace) {
    this.volunteerProfileService.shareTaskByVolunteer(this.volunteer, taskEntry, marketplace.url).toPromise().then(() => {
      this.loadFinishedTasks();
    });
  }

  revokeTask(taskEntry: TaskEntry, marketplace: Marketplace) {
    this.volunteerProfileService.revokeTaskByVolunteer(this.volunteer, taskEntry, marketplace.url).toPromise().then(() => {
      this.loadFinishedTasks();
    });
  }

  synchronizeTask(taskEntry: TaskEntry, marketplace: Marketplace) {
    this.volunteerRepositoryService.synchronizeTask(this.volunteer, taskEntry).toPromise().then(() => {
      this.loadFinishedTasks();
    });
  }
}


export class TasksDataSource extends DataSource<TaskEntry> {

  constructor(
    private tasks: TaskEntry[]
  ) {
    super();
  }

  connect(): Observable<TaskEntry[]> {
    return of(this.tasks);
  }

  disconnect() { }
}