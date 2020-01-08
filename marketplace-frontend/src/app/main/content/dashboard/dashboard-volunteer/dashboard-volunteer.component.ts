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
import { FeedbackService } from '../../_service/feedback.service';
import { ClassInstance, ClassArchetype } from '../../_model/meta/Class';
import { Feedback } from '../../_model/feedback';
import { CoreUserImagePathService } from '../../_service/core-user-imagepath.service';

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
  dataSourceTasks = new MatTableDataSource<ClassInstance>();

  displayedColumnsCompetence = ['issuer', 'label_competence', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  displayedColumnsFeedback = ['issuer', 'label_feedback', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  displayedColumnsTasks = ['issuer', 'label_tasks', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];

  issuerIds: string[] = [];
  userImagePaths: any[];


  constructor(public dialog: MatDialog,
    private coreVolunteerService: CoreVolunteerService,
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
      this.classInstanceService.getClassInstancesInUserRepository(this.marketplace, this.volunteer.id).toPromise().then((ret: ClassInstance[]) => {

        let tasks = ret.filter((classInstance: ClassInstance) => {
          return classInstance.classArchetype === ClassArchetype.TASK;
        });

        tasks = tasks.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());
        if (tasks.length > 5) {
          tasks = tasks.slice(0, 5);
        }

        this.dataSourceTasks.data = tasks;
        this.issuerIds.push(...tasks.map(t => t.issuerId));

        let competences = ret.filter((classInstance: ClassInstance) => {
          return classInstance.classArchetype === ClassArchetype.COMPETENCE;
        });

        competences = competences.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());
        if (competences.length > 5) {
          competences = competences.slice(0, 5);
        }
        this.dataSourceComp.data = competences;
        this.issuerIds.push(...competences.map(t => t.issuerId));


        let feedback = ret.filter((classInstance: Feedback) => {
          return !isNullOrUndefined(classInstance.feedbackType);
        });

        feedback = feedback.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());
        if (feedback.length > 5) {
          feedback = feedback.slice(0, 5);
        }
        this.dataSourceFeedback.data = feedback;
        this.issuerIds.push(...feedback.map(t => t.issuerId));


      })

    ]).then(() => {
      this.issuerIds = this.issuerIds.filter((elem, index, self) => {
        return index === self.indexOf(elem);
      });
      console.log(this.issuerIds);
      this.userImagePathService.getImagePathsById(this.issuerIds).toPromise().then((ret: any) => {
        console.log(ret);
        this.userImagePaths = ret;

      }).then(() => {
        this.isLoaded = true;
      });
    });
  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
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

  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: 'share' }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      console.log('done');
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

