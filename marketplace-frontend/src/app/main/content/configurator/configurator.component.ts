import { Component, OnInit } from '@angular/core';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';

import { ClassDefinition } from '../_model/meta/Class';
import { Participant, ParticipantRole } from '../_model/participant';
import { Relationship } from '../_model/meta/Relationship';
import { CoreFlexProdService } from '../_service/core-flexprod.service';

@Component({
  selector: 'app-configurator',
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss'],
})
export class ConfiguratorComponent implements OnInit {

  marketplace: Marketplace;
  configurableClasses: ClassDefinition[];
  relationships: Relationship[];


  isLoaded = false;

  constructor(
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService,
  ) { }

  ngOnInit() {
    let service: CoreHelpSeekerService | CoreFlexProdService;
    // get marketplace

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role === 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role === 'HELP_SEEKER') {
          service = this.helpSeekerService;
        }

        service.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
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
