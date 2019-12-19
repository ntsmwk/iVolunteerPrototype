import { Component, OnInit, Input, EventEmitter, ElementRef } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { FeedbackService } from 'app/main/content/_service/feedback.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant } from 'app/main/content/_model/participant';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { isNullOrUndefined } from "util";


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
    private feedbackService: FeedbackService) {

  }

  marketplace: Marketplace;
  volunteer: Volunteer;


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
      this.classInstanceService.getClassInstancesByUserIdInInbox(this.marketplace, this.volunteer.id).toPromise().then((ret: any) => {
        console.log(this.marketplace);
        console.log(this.volunteer);
        console.log(ret);
      })
    });


  }

  close() {

  }



}


