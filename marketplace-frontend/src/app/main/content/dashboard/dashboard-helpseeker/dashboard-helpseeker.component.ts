import { OnInit, Component } from "@angular/core";
import { ArrayService } from "../../_service/array.service";
import { LoginService } from "../../_service/login.service";
import { CoreMarketplaceService } from "../../_service/core-marketplace.service";
import { CoreVolunteerService } from "../../_service/core-volunteer.service";
import { CoreHelpSeekerService } from "../../_service/core-helpseeker.service";
import { Subscription } from "rxjs";
import { Participant } from "../../_model/participant";
import { Marketplace } from "../../_model/marketplace";
import { fuseAnimations } from "@fuse/animations";
import { isNullOrUndefined } from 'util';
import { ClassInstanceService } from '../../_service/meta/core/class/class-instance.service';
import { ClassInstance } from '../../_model/meta/Class';
import { Router } from '@angular/router';


@Component({
  selector: 'dashboard-helpseeker',
  templateUrl: './dashboard-helpseeker.component.html',
  styleUrls: ['dashboard-helpseeker.scss'],
  animations: fuseAnimations
})
export class DashboardHelpSeekerComponent implements OnInit {


  // public marketplaces = new Array<Marketplace>();
  marketplace: Marketplace;
  participant: Participant;
  classInstances: ClassInstance[];
  isLoaded: boolean;





  constructor(private arrayService: ArrayService,
    private loginService: LoginService,
    private router: Router,
    private classInstanceService: ClassInstanceService,
    private marketplaceService: CoreMarketplaceService,
    private helpSeekerService: CoreHelpSeekerService) {
  }

  ngOnInit() {
    // this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
    //     this.helpSeeker = participant;
    //     this.loadSuggestedMarketplaces();
    // });

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
      console.log("confirm");
      this.router.navigate(['main/helpseeker/asset-inbox/confirm'], { state: { 'instances': classInstances, 'marketplace': this.marketplace, 'participant': this.participant } });
    });
  }

  // private loadSuggestedMarketplaces() {
  //     Promise.all([
  //         this.marketplaceService.findAll().toPromise(),
  //         this.helpSeekerService.findRegisteredMarketplaces(this.helpSeeker.id).toPromise()
  //     ]).then((values: any[]) => {
  //         this.marketplaces = values[0];
  //         if (values[1]) {
  //             this.marketplaces = this.arrayService.removeAll(values[0], [values[1]]);
  //         }
  //     });
  // }

  // registerMarketplace(marketplace) {
  //     this.helpSeekerService.registerMarketplace(this.helpSeeker.id, marketplace.id).toPromise().then(() => {
  //         this.loadSuggestedMarketplaces();
  //     });
  // }
}