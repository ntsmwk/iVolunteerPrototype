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
import { Competence } from '../../../_model/competence';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'fuse-profile-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  providers: [DatePipe],
  animations: fuseAnimations
})
export class FuseProfileCompetenciesComponent implements OnInit, AfterViewInit {
  canvas: any;
  ctx: any;

  dataSource: MatDataSource;

  columns = [
    { columnDef: 'name', marketplace: null, columnType: 'text', header: 'Name', cell: (row: CompetenceEntry) => `${row.competenceName}` },
  ];
  displayedColumns: any[];

  competencies: CompetenceEntry[] = [];
  combinedCompetencies: CompetenceEntry[] = [];


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
    this.commonProfile = new VolunteerProfile();
    this.commonProfile.competenceList = [];

    this.columns.push({ columnDef: 'timestamp', marketplace: null, columnType: 'text', header: 'Timestamp', cell: (row: CompetenceEntry) => this.datePipe.transform(row.timestamp, 'dd.MM.yyyy') });

    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
      this.volunteer = volunteer;
      if (!isArray(selected_marketplaces)) {
        return;
      }
      selected_marketplaces.forEach((mp: Marketplace) => {
        this.columns.push({ columnDef: mp.id, marketplace: mp, columnType: 'function', header: mp.name, cell: (row: CompetenceEntry) => this.handleCompetenceMarketplace(mp, row) })
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

  handleCompetenceMarketplace(marketplace: Marketplace, competenceEntry: CompetenceEntry): string {

    if (this.privateProfileContains(competenceEntry) && this.publicProfileContains(competenceEntry, marketplace)) {
      return 'REVOKE'
    } else if (!this.privateProfileContains(competenceEntry) && this.publicProfileContains(competenceEntry, marketplace)) {
      return 'SYNC';
    } else if (this.privateProfileContains(competenceEntry) && !this.publicProfileContains(competenceEntry, marketplace)) {
      return 'PUBLISH';
    }
    throw new error('please reload page...');
    return '';
  }

  private privateProfileContains(competenceEntry: CompetenceEntry): boolean {
    return this.privateProfile.competenceList.find(c => c.competenceId == competenceEntry.competenceId) != null;
  }

  private publicProfileContains(competenceEntry: CompetenceEntry, marketplace: Marketplace) {
    if (this.publicProfiles.has(marketplace.id)) {
      return this.publicProfiles.get(marketplace.id).competenceList.find(c => c.competenceId == competenceEntry.competenceId);
    }
    return false;
  }

  ngAfterViewInit() {
    // this.canvas = document.getElementById('myChart');
    // this.ctx = this.canvas.getContext('2d');
    // const myChart = new Chart(this.ctx, {
    //   type: 'radar',
    //   data: {
    //     labels: ['Professional skills', 'Methodical expertise', 'Social skills', 'Self competences'],
    //     datasets: [{
    //       label: 'You',
    //       backgroundColor: 'rgba(200,0,0,0.2)',
    //       data: [14, 10, 10, 8]
    //     }]
    //   },
    //   options: {}
    // });
  }

  private loadPublicVolunteerProfile(volunteer: Volunteer, marketplace: Marketplace) {
    return this.volunteerProfileService.findByVolunteer(volunteer, marketplace.url)
      .toPromise()
      .then((volunteerProfile: VolunteerProfile) => {
        this.publicProfiles.set(marketplace.id, volunteerProfile);
        this.onLoadingComplete();
      });
  }

  private onLoadingComplete() {
    this.combinedCompetencies = this.privateProfile.competenceList;
    this.publicProfiles.forEach((p, id) => {
      this.combinedCompetencies = this.arrayService.concat(this.combinedCompetencies, p.competenceList);
    });

    this.dataSource = new MatDataSource(this.combinedCompetencies);
    this.displayedColumns = this.columns.map(x => x.columnDef);
  }

  publishCompetence(competenceEntry: CompetenceEntry, marketplace: Marketplace) {
    this.volunteerProfileService.shareCompetenceByVolunteer(this.volunteer, competenceEntry, marketplace.url).toPromise().then(() => {
      alert('Competence is published to marketplace.');
      // this.publicProfile.competenceList.push(competenceEntry);
    });
  }

  revokeCompetence(competenceEntry: CompetenceEntry, marketplace: Marketplace) {
    this.volunteerProfileService.revokeCompetenceByVolunteer(this.volunteer, competenceEntry, marketplace.url).toPromise().then(() => {
      alert('Competence is revoked from marketplace.');
      // this.publicProfile.competenceList = this.publicProfile.competenceList
      //   .filter((competence: CompetenceEntry) => competence.id !== competenceEntry.id);
    });
  }

  synchronizeCompetence(competenceEntry: CompetenceEntry, marketplace: Marketplace) {
    this.volunteerRepositoryService.synchronizeCompetence(this.volunteer, competenceEntry).toPromise().then(() => {
      alert('Competence is synchronized to local repository.');
      // this.privateProfile.competenceList.push(competenceEntry);
    });
  }

}


export class MatDataSource extends DataSource<CompetenceEntry> {

  constructor(
    private competencies: CompetenceEntry[]
  ) {
    super();
  }

  connect(): Observable<CompetenceEntry[]> {
    return of(this.competencies);
  }

  disconnect() { }
}