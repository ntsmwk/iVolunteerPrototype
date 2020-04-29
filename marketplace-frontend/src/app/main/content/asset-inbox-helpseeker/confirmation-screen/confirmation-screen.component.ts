import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Marketplace } from '../../_model/marketplace';
import { Participant } from '../../_model/participant';
import { isNullOrUndefined } from 'util';
import { ClassInstance } from '../../_model/meta/class';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { LoginService } from '../../_service/login.service';



@Component({
  selector: 'helpseeker-inbox-confirmation-screen',
  templateUrl: './confirmation-screen.component.html',
  styleUrls: ['./confirmation-screen.component.scss'],
})
export class HelpseekerConfirmationScreenComponent implements OnInit {

  // dataSource = new MatTableDataSource<any>();
  // displayedColumns = ['data', 'issuer', 'label', 'details'];

  isLoaded: boolean;
  marketplace: Marketplace;
  participant: Participant;
  classInstances: ClassInstance[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private loginService: LoginService,

  ) {

    if (!isNullOrUndefined(this.router.getCurrentNavigation().extras.state)) {
      this.marketplace = this.router.getCurrentNavigation().extras.state.marketplace;
      this.participant = this.router.getCurrentNavigation().extras.state.participant;
      this.classInstances = this.router.getCurrentNavigation().extras.state.classInstances;
    }

  }

  ngOnInit() {
    console.log(this.marketplace);
    console.log(this.participant);

    if (isNullOrUndefined(this.marketplace) || isNullOrUndefined(this.participant)) {
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

      });
    }
  }

  onBackClickInbox() {
    this.router.navigate(['main/helpseeker/asset-inbox'], { state: { 'instances': undefined, 'marketplace': this.marketplace, 'participant': this.participant } });
  }

  onBackClickDashboard() {
    this.router.navigate(['main/dashboard']);
  }

  onBackClickNewTask() {
    this.router.navigate(['main/tasks/all']);

  }


}


