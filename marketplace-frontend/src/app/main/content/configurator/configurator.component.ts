import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, PropertyKind, Property } from '../_model/configurables/Property';

import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ConfiguratorService } from '../_service/configurator.service';
import { ConfigurableClass } from '../_model/configurables/Configurable';
import { Participant } from '../_model/participant';
import { fuseAnimations } from '@fuse/animations';
import { RelationshipService } from '../_service/relationship.service';
import { Relationship } from '../_model/configurables/Relationship';





@Component({
  selector: 'app-configurator',
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss'],
})
export class ConfiguratorComponent implements OnInit {

  marketplace: Marketplace;
  configurableClasses: ConfigurableClass[];
  relationships: Relationship[];


  isLoaded: boolean = false;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private configuratorService: ConfiguratorService,
    private relationshipService: RelationshipService) { }

  ngOnInit() {
    // get marketplace
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {

        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;

          Promise.all([
            this.configuratorService.getAllConfigClasses(this.marketplace).toPromise().then((configurableClasses: ConfigurableClass[]) => {
              this.configurableClasses = configurableClasses;              
            }),
            
            this.relationshipService.getAllRelationships(this.marketplace).toPromise().then((relationships: Relationship[]) => {
              this.relationships = relationships;
            })

          ]).then( () => {
            this.isLoaded = true
          });
        }
      });
    });
  }





  navigateBack() {
    window.history.back();
  }

}
