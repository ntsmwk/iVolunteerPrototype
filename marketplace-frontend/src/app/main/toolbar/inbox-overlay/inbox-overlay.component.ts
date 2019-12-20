import { Component, OnInit, Input, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { FeedbackService } from 'app/main/content/_service/feedback.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant } from 'app/main/content/_model/participant';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { isNullOrUndefined } from "util";
import { ClassInstance } from 'app/main/content/_model/meta/Class';


@Component({
  selector: 'inbox-overlay',
  templateUrl: './inbox-overlay.component.html',
  styleUrls: ['./inbox-overlay.component.scss'],
})
export class InboxOverlayComponent implements OnInit {

  constructor(
    private marketplaceService: CoreMarketplaceService,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private feedbackService: FeedbackService,
    private element: ElementRef) {

  }


  @ViewChild('innerDiv', { static: false }) innerDiv: ElementRef;
  @ViewChild('actionDiv', { static: false }) actionDiv: ElementRef;


  marketplace: Marketplace;
  volunteer: Volunteer;
  classInstances: ClassInstance[] = [];
  dataSource = new MatTableDataSource<ClassInstance>();
  displayedColumns = [/*'issuer', */'label', 'date'];







  ngOnInit() {



    Promise.all([
      this.marketplaceService.findAll().toPromise().then((marketplaces: Marketplace[]) => {
        if (!isNullOrUndefined(marketplaces))
          this.marketplace = marketplaces[0]
      }),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
        this.volunteer = participant;
      })

    ]).then(() => {
      this.classInstanceService.getClassInstancesByUserIdInInbox(this.marketplace, this.volunteer.id).toPromise().then((ret: ClassInstance[]) => {
        if (!isNullOrUndefined(ret)) {
          this.dataSource.data = ret;
          this.classInstances = ret;
          console.log(ret);
          console.log("=====");
          console.log(this.element);
          console.log(this.element.nativeElement.parentElement);//offsetWidth ; offsetHeight
          console.log(this.element.nativeElement.parentElement.offsetWidth);
          console.log(this.element.nativeElement.parentElement.offsetHeight);

          this.innerDiv.nativeElement.style.width = (this.element.nativeElement.parentElement.offsetWidth - 8) + 'px';
          this.innerDiv.nativeElement.style.height = (this.element.nativeElement.parentElement.offsetHeight - 58) + 'px';

          // this.actionDiv.nativeElement.style.width = (this.element.nativeElement.parentElement.offsetWidth - 8) + 'px';
          this.actionDiv.nativeElement.style.height = '18px';


        }

      })
    });


  }

  getDateString(date: number) {
    return new Date(date).toLocaleDateString();
  }

  close() {

  }



}


