import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatListModule} from '@angular/material/list'

import { TaskService } from '../_service/task.service';
import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { Property, PropertyKind } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';
import { PropertyService } from '../_service/property.service';

@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss']
})
export class FusePropertyDetailComponent implements OnInit {

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  property: Property<any>;
  isLoaded: boolean;
  columnsToDisplay = ['value'];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private propertyService: PropertyService) {
      this.isLoaded = false;
      }

  ngOnInit() {
    console.log("Navigated Property Detail Page");
    
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    ]).then(() => {
      
      this.route.params.subscribe(params => this.loadProperty(params['marketplaceId'], params['propertyId']));
    });

  }


  loadProperty(marketplaceId: string, propId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.propertyService.getProperty(marketplace, propId).toPromise().then((property: Property<any>) => {
        this.property = property;    
      }).then(() => {
        console.log("DETAIL PAGE FOR PROPERTY " + this.property.id);
        console.log(this.property.name + ": " + this.property.value);
        console.log("Kind: " + this.property.kind);
        console.log("Default Value: " + this.property.defaultValue)
        console.log("============================");
        console.log(this.property);
        this.isLoaded = true;
      });
    });  
  }

  navigateBack() {
    window.history.back();
  }
}
