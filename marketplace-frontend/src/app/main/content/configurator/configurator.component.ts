import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';

import { ClassDefinitionService } from '../_service/meta/core/class/class-definition.service';
import { ClassDefintion } from '../_model/meta/Class';
import { Participant } from '../_model/participant';
import { RelationshipService } from '../_service/meta/core/relationship/relationship.service';
import { Relationship } from '../_model/meta/Relationship';





@Component({
  selector: 'app-configurator',
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss'],
})
export class ConfiguratorComponent implements OnInit {

  marketplace: Marketplace;
  configurableClasses: ClassDefintion[];
  relationships: Relationship[];


  isLoaded: boolean = false;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private classDefinitionService: ClassDefinitionService,
    private relationshipService: RelationshipService) { }

  ngOnInit() {
    // get marketplace
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {

        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;

          Promise.all([
            this.classDefinitionService.getAllClassDefinitions(this.marketplace).toPromise().then((configurableClasses: ClassDefintion[]) => {
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
