import { Component, OnInit } from '@angular/core';
import {Marketplace} from '../_model/marketplace';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {CoreService} from '../_service/core.service';
import {Volunteer} from '../_model/volunteer';
import {CoreVolunteerService} from '../_service/core.volunteer.service';
import { List } from 'lodash';

@Component({
  selector: 'fuse-marketplaces',
  templateUrl: './marketplaces.component.html',
  styleUrls: ['./marketplaces.component.scss']
})
export class MarketplacesComponent implements OnInit {
   volunteer: Volunteer;
   marketplaces: List<Marketplace> = [];
   registeredMarketplaces: List<Marketplace> = [];


  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private coreService: CoreService,
    private coreVolunteerService: CoreVolunteerService
  ){}

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) =>
      this.volunteer = volunteer);

    this.coreService.findMarketplaces().toPromise().then((marketplaces: List<Marketplace>) =>
    this.marketplaces = marketplaces);

    this.coreVolunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise().then((marketplaces: List<Marketplace>) =>
      this.registeredMarketplaces = marketplaces);


  }

}
