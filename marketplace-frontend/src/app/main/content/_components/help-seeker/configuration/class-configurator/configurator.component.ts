import { Component, OnInit } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { CoreFlexProdService } from 'app/main/content/_service/core-flexprod.service';
import { ParticipantRole } from 'app/main/content/_model/participant';
import { isNullOrUndefined } from 'util';
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
    ) { }

    ngOnInit() {
        // get marketplace
        this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
            this.helpseeker = helpseeker;
            this.helpSeekerService.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
                if (!isNullOrUndefined(marketplace)) {
                    this.marketplace = marketplace;
                    this.isLoaded = true;
                }
            });
        });
    }

    navigateBack() {
        window.history.back();
    }

}