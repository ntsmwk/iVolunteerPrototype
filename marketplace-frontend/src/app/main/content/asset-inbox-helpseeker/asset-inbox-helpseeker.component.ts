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
  selector: 'asset-inbox-helpseeker',
  templateUrl: './asset-inbox-helpseeker.component.html',
  styleUrls: ['./asset-inbox-helpseeker.component.scss'],
})
export class AssetInboxHelpseekerComponent implements OnInit {

  // dataSource = new MatTableDataSource<any>();
  // displayedColumns = ['data', 'issuer', 'label', 'details'];

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

  }

  ngOnInit() {
    console.log(this.marketplace);
    console.log(this.participant);


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

  loadInboxEntries() {
    this.classInstanceService.getClassInstancesInIssuerInbox(this.marketplace, this.participant.id).toPromise().then((ret: ClassInstance[]) => {
      console.log(ret);
      this.classInstances = ret;
      this.isLoaded = true;
    });

  }

  close() {
  }

  onAssetInboxSubmit(classInstances: ClassInstance[]) {
    this.classInstanceService.setClassInstanceInIssuerInbox(this.marketplace, classInstances.map(c => c.id), false).toPromise().then(() => {

      this.router.navigate(['/main/helpseeker/asset-inbox/confirm'], {state: {'instances': classInstances, 'marketplace': this.marketplace, 'participant': this.participant}});

    });

  }



}


