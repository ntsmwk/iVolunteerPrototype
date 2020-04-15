import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../../../../_model/marketplace';

import { ClassDefinitionService } from '../../../../_service/meta/core/class/class-definition.service';
import { ClassDefinition } from '../../../../_model/meta/Class';
import { Participant, ParticipantRole } from '../../../../_model/participant';
import { RelationshipService } from '../../../../_service/meta/core/relationship/relationship.service';
import { Relationship } from '../../../../_model/meta/Relationship';
import { CoreFlexProdService } from '../../../../_service/core-flexprod.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';

@Component({
  selector: 'app-configurator',
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss'],
})
export class ConfiguratorComponent implements OnInit {

  marketplace: Marketplace;
  configurableClasses: ClassDefinition[];
  relationships: Relationship[];
  helpseeker: Helpseeker;

  isLoaded: boolean = false;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService,
    private classDefinitionService: ClassDefinitionService,
    private relationshipService: RelationshipService) { }

  ngOnInit() {
    let service: CoreHelpSeekerService | CoreFlexProdService;
    // get marketplace
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role == "FLEXPROD") {
          service = this.flexProdService;
        } else if (role == "HELP_SEEKER") {
          service = this.helpSeekerService;
        }

        service.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
            this.helpseeker = helpseeker;

            this.isLoaded = true;


          }
        });
      });
    });
  }

  navigateBack() {
    window.history.back();
  }

}
