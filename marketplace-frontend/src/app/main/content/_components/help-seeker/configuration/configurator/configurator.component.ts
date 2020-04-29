import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../../../../_model/marketplace';
import { ClassDefinition } from '../../../../_model/meta/class';
import { ParticipantRole } from '../../../../_model/participant';
import { Relationship } from '../../../../_model/meta/relationship';
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

  isLoaded = false;

  constructor(
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService,
  ) { }

  ngOnInit() {
    let service: CoreHelpSeekerService | CoreFlexProdService;
    // get marketplace
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role === 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role === 'HELP_SEEKER') {
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
