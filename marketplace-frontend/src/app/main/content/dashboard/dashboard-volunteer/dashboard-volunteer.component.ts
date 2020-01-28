import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ShareDialog } from './share-dialog/share-dialog.component';
import { CoreVolunteerService } from '../../_service/core-volunteer.service';
import { LoginService } from '../../_service/login.service';
import { Participant } from '../../_model/participant';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../../_model/marketplace';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { ClassInstance, ClassArchetype } from '../../_model/meta/Class';
import { CoreUserImagePathService } from '../../_service/core-user-imagepath.service';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';

@Component({
  selector: 'dashboard-volunteer',
  templateUrl: './dashboard-volunteer.component.html',
  styleUrls: ['./dashboard-volunteer.component.scss'],
})
export class DashboardVolunteerComponent implements OnInit {
  widget0: any;
  widget1: any;
  widget2: any;
  widget3: any;

  volunteer: Participant;
  marketplace: Marketplace;
  registeredMarketplaces: Marketplace[];

  isLoaded: boolean;

  dataSourceComp = new MatTableDataSource<ClassInstance>();
  dataSourceFeedback = new MatTableDataSource<ClassInstance>();
  dataSourceRepository = new MatTableDataSource<ClassInstance>();

  // displayedColumnsCompetence = ['issuer', 'label_competence', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  // displayedColumnsFeedback = ['issuer', 'label_feedback', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  // displayedColumnsTasks = ['issuer', 'label_tasks', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];

  displayedColumnsRepository = ['issuer', 'asset', 'date', 'actions'];

  issuerIds: string[] = [];
  issuers: Participant[] = [];
  userImagePaths: any[];


  constructor(public dialog: MatDialog,
    private coreVolunteerService: CoreVolunteerService,
    private coreHelpseekerService: CoreHelpSeekerService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,

    private classInstanceService: ClassInstanceService,
    private userImagePathService: CoreUserImagePathService
  ) {

  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = participant;
      Promise.all([
        this.marketplaceService.findAll().toPromise().then((ret: Marketplace[]) => {
          this.marketplace = ret.pop();
        }),

        this.coreVolunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise().then((ret: Marketplace[]) => {
          this.registeredMarketplaces = ret;

        }),
      ]).then(() => {
        if (isNullOrUndefined(this.registeredMarketplaces) || this.registeredMarketplaces.length <= 0) {
          this.coreVolunteerService.registerMarketplace(this.volunteer.id, this.marketplace.id).toPromise().then(() => {
            this.loadDashboardContent();
          });
        } else {
          this.loadDashboardContent();
        }
      });
    });
  }

  loadDashboardContent() {
    Promise.all([
      this.classInstanceService.getClassInstancesInUserRepository(this.marketplace, this.volunteer.id).toPromise().then((instances: ClassInstance[]) => {

        instances = instances.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());
        if (instances.length > 25) {
          instances = instances.slice(0, 25);
        }

        this.dataSourceRepository.data = instances;
        this.issuerIds.push(...instances.map(t => t.issuerId));
      })

    ]).then(() => {
      this.issuerIds = this.issuerIds.filter((elem, index, self) => {
        return index === self.indexOf(elem);
      });
      console.log(this.issuerIds);
      Promise.all([
      this.userImagePathService.getImagePathsById(this.issuerIds).toPromise().then((ret: any) => {
        console.log(ret);
        this.userImagePaths = ret;
      }),
      this.coreHelpseekerService.findByIds(this.issuerIds).toPromise().then((ret: any) => {
        this.issuers = ret;
      })
      
      ]).then(() => {
        this.isLoaded = true;
      });
    });
  }

  getDateString(dateNumber: number) {
    const date = new Date(dateNumber);
    return 'am ' + date.toLocaleDateString() + ', um ' + date.toLocaleTimeString();
  }

  getImagePathById(id: string) {
    const ret = this.userImagePaths.find((userImagePath) => {
      return userImagePath.userId === id;
    });

    if (isNullOrUndefined(ret)) {
      return '/assets/images/avatars/profile.jpg';
    } else {
      return ret.imagePath;
    }
  }

  getIssuerById(id: string) {
   return this.issuers.find((issuer) => {
      return issuer.id === id;
    });
  }

  getIssuerName(issuerId: string) {
    
    const person = this.issuers.find((i) => i.id === issuerId);
   
    let result = '';

    if (isNullOrUndefined(person)) {
      return result;
    }

    result = person.firstname + ' ' + person.lastname;
    return result;
  }

  getIssuerPosition(issuerId: string) {
    const person = this.issuers.find((i) => i.id === issuerId);
    if (isNullOrUndefined(person) || isNullOrUndefined(person.position)) {
      return '';
    } else {
      return '(' + person.position + ')';
    }
  }

  findNameProperty(entry: ClassInstance) {
    if (isNullOrUndefined(entry.properties)) {
      return '';
    }

    let name =  entry.properties.find(p => p.id === 'name');

    if (isNullOrUndefined(name)) {
      name = entry.properties.find(p => p.name === 'taskName');
    }

    if (isNullOrUndefined(name) || isNullOrUndefined(name.values) || isNullOrUndefined(name.values[0])) {
      return '';
    } else {
      return ': ' + name.values[0];
    }


  }

  getArchetypeIcon(entry: ClassInstance) {
    if (isNullOrUndefined(entry.imagePath)) {

      if (entry.classArchetype === ClassArchetype.COMPETENCE) {
        return '/assets/competence.jpg';
      } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
        return '/assets/icons/achievements_black.png';
      } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
        return '/assets/TODO';
      } else if (entry.classArchetype === ClassArchetype.TASK) {
        return '/assets/cog.png';
      } else {
        return '/assets/NONE';
      }
    } else {
      return entry.imagePath;
    }

  }

  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: 'Freigabe' }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
    });
  }

  triggerStoreDialog() {

    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: 'store' }
    });


    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      this.toggleShareInRep();
      console.log('done');
    });
  }


  toggleShareInRep() {

  }



}

export interface DialogData {
  name: string;
}

