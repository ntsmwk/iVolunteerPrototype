import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild, Output } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { FeedbackService } from 'app/main/content/_service/feedback.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant, ParticipantRole } from 'app/main/content/_model/participant';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { isNullOrUndefined } from 'util';
import { ClassInstance } from 'app/main/content/_model/meta/Class';
import { Router } from '@angular/router';
import { Feedback } from 'app/main/content/_model/feedback';

@Component({
  selector: 'inbox-overlay',
  templateUrl: './inbox-overlay.component.html',
  styleUrls: ['./inbox-overlay.component.scss'],
})
export class InboxOverlayComponent implements OnInit {

  constructor(
    private router: Router,
    private marketplaceService: CoreMarketplaceService,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private element: ElementRef) {

  }

  isLoaded: boolean;
  @ViewChild('innerDiv', { static: false }) innerDiv: ElementRef;
  @ViewChild('actionDiv', { static: false }) actionDiv: ElementRef;

  @Output() closeOverlay: EventEmitter<boolean> = new EventEmitter<boolean>();


  marketplace: Marketplace;
  participant: Participant;
  participantRole: ParticipantRole;
  classInstances: ClassInstance[] = [];

  dataSource = new MatTableDataSource<ClassInstance | Feedback>();
  displayedColumns = ['archetype', 'label', 'date'];

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
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        this.participantRole = role;

        if (role === 'VOLUNTEER') {
          this.classInstanceService.getClassInstancesByUserIdInInbox(this.marketplace, this.participant.id).toPromise().then((ret: ClassInstance[]) => {

            this.drawInboxElements(ret);

            this.isLoaded = true;
          });
        } else if (role === 'HELP_SEEKER') {
          this.classInstanceService.getClassInstancesByIssuerIdInInbox(this.marketplace, this.participant.id).toPromise().then((ret: ClassInstance[]) => {

              this.drawInboxElements(ret);
              this.isLoaded = true;
          });
        }
      });
    });

  }

  drawInboxElements(classInstances: ClassInstance[]) {
    if (!isNullOrUndefined(classInstances)) {
      classInstances.sort((a, b) => a.timestamp.valueOf() - b.timestamp.valueOf());

      if (classInstances.length > 5) {
        classInstances = classInstances.slice(0, 5);
      }

      this.classInstances = classInstances;
      this.dataSource.data = classInstances;
      // console.log(ret);
      // console.log("=====");
      // console.log(this.element);
      // console.log(this.element.nativeElement.parentElement);//offsetWidth ; offsetHeight
      // console.log(this.element.nativeElement.parentElement.offsetWidth);
      // console.log(this.element.nativeElement.parentElement.offsetHeight);

      this.innerDiv.nativeElement.style.width = (this.element.nativeElement.parentElement.offsetWidth - 8) + 'px';
      this.innerDiv.nativeElement.style.height = (this.element.nativeElement.parentElement.offsetHeight - 58) + 'px';
      this.innerDiv.nativeElement.style.overflow = 'hidden';
      // this.actionDiv.nativeElement.style.width = (this.element.nativeElement.parentElement.offsetWidth - 8) + 'px';
      this.actionDiv.nativeElement.style.height = '18px';
    }
  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
  }

  showInboxClicked() {
    this.closeOverlay.emit(true);
    this.router.navigate(['/main/volunteer/asset-inbox'], { state: { marketplace: this.marketplace, participant: this.participant } });
  }


}


