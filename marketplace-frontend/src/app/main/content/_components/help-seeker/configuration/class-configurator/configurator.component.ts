import { Component, OnInit } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { CoreFlexProdService } from 'app/main/content/_service/core-flexprod.service';
import { Participant, ParticipantRole } from 'app/main/content/_model/participant';
import { isNullOrUndefined } from 'util';


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