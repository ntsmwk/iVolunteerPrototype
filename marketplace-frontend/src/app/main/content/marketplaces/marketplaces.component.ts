import {Component, OnDestroy, OnInit} from '@angular/core';
import {Marketplace} from '../_model/marketplace';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {CoreService} from '../_service/core.service';
import {Volunteer} from '../_model/volunteer';
import {Participant} from '../_model/participant';
import {ArrayService} from '../_service/array.service';
import {MessageService} from '../_service/message.service';
import {CoreVolunteerService} from '../_service/core-volunteer.service';


@Component({
  selector: 'fuse-marketplaces',
  templateUrl: './marketplaces.component.html',
  styleUrls: ['./marketplaces.component.scss']
})
export class MarketplacesComponent implements OnInit {
  volunteer: Volunteer;
  allMarketplaces: Array<Marketplace> = [];
  registeredMarketplaces: Array<Marketplace> = [];


  constructor(private route: ActivatedRoute,
              private loginService: LoginService,
              private coreService: CoreService,
              private arrayService: ArrayService,
              private messageService: MessageService,
              private coreVolunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.volunteer = volunteer;
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        this.registeredMarketplaces = marketplaces;
      });
    });

    this.coreService.findMarketplaces().toPromise().then((marketplaces: Array<Marketplace>) =>
      this.allMarketplaces = marketplaces);

  }

  subscribe(marketplace: Marketplace) {
    this.coreVolunteerService.registerMarketplace(this.volunteer.id, marketplace.marketplaceId).toPromise().then(() => {
      this.registeredMarketplaces.push(marketplace);
    });

    this.messageService.broadcast('marketplaceSelectionChanged', {});

  }

  isUnsubscribed(marketplace: Marketplace) {
    return !(this.arrayService.contains(this.registeredMarketplaces, marketplace));
  }


}
