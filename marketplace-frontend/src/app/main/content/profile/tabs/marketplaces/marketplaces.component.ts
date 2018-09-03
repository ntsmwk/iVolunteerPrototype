import {Component, OnInit} from '@angular/core';
import {Marketplace} from '../../../_model/marketplace';
import {LoginService} from '../../../_service/login.service';
import {CoreMarketplaceService} from '../../../_service/core-marketplace.service';
import {CoreVolunteerService} from '../../../_service/core-volunteer.service';
import {Participant} from '../../../_model/participant';


@Component({
  selector: 'fuse-profile-marketplaces',
  templateUrl: './marketplaces.component.html',
  styleUrls: ['./marketplaces.component.scss']
})
export class FuseProfileMarketplacesComponent implements OnInit {
  registeredMarketplaces: Array<Marketplace> = [];

  constructor(private loginService: LoginService,
              private marketplaceService: CoreMarketplaceService,
              private volunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.volunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((registeredMarketplaces: Marketplace[]) => {
        this.registeredMarketplaces = registeredMarketplaces;
      });
    });
  }
}
