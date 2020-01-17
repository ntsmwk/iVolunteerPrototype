import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Marketplace } from '../_model/marketplace';
import { Participant } from '../_model/participant';
import { ClassInstance } from '../_model/meta/Class';
import { ClassInstanceService } from '../_service/meta/core/class/class-instance.service';
import { isNullOrUndefined } from 'util';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { LoginService } from '../_service/login.service';


@Component({
  selector: 'asset-inbox-volunteer',
  templateUrl: './asset-inbox-volunteer.component.html',
  styleUrls: ['./asset-inbox-volunteer.component.scss'],
})
export class AssetInboxVolunteerComponent implements OnInit {

  isLoaded: boolean;
  marketplace: Marketplace;
  participant: Participant;
  classInstances: ClassInstance[];

  submitPressed: boolean;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private loginService: LoginService,

  ) {

    console.log(this.router.getCurrentNavigation().extras.state);
    if (!isNullOrUndefined(this.router.getCurrentNavigation().extras.state)) {
      this.marketplace = this.router.getCurrentNavigation().extras.state.marketplace;
      this.participant = this.router.getCurrentNavigation().extras.state.participant;
    }

  }

  ngOnInit() {
    console.log(this.marketplace);
    console.log(this.participant);

    if (!isNullOrUndefined(this.marketplace) && !isNullOrUndefined(this.participant)) {
      console.log('if branch');
      this.loadInboxEntries();
    } else {
      console.log('else branch');
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
        this.loadInboxEntries();
      });
    }
  }

  loadInboxEntries() {
    this.classInstanceService.getClassInstancesInUserInbox(this.marketplace, this.participant.id).toPromise().then((ret: ClassInstance[]) => {

      console.log(ret);
      this.classInstances = ret;
      this.isLoaded = true;

    });
  }

  close() {
  }

  onAssetInboxSubmit(classInstances: ClassInstance[]) {
    console.log('submitted - returned');

    this.classInstanceService.setClassInstanceInUserRepository(this.marketplace, classInstances.map(c => c.id), true).toPromise().then(() => {

      // this.router.navigate(['/main/volunteer/asset-inbox/confirm'], {state: {'instances': classInstances, 'marketplace': this.marketplace, 'participant': this.participant}});
      this.router.navigate(['/main/dashboard']);
    });

  }



}


