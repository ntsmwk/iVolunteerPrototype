import { OnInit, Component } from "@angular/core";
import { ArrayService } from "../../_service/array.service";
import { LoginService } from "../../_service/login.service";
import { CoreMarketplaceService } from "../../_service/core-marketplace.service";
import { CoreVolunteerService } from "../../_service/core-volunteer.service";
import { CoreFlexProdService } from "../../_service/core-flexprod.service";
import { Subscription } from "rxjs";
import { Participant } from "../../_model/participant";
import { Marketplace } from "../../_model/marketplace";
import { fuseAnimations } from "@fuse/animations";


@Component({
    selector: 'fuse-dashboard-flexprod',
    templateUrl: './dashboard-flexprod.component.html',
    styleUrls: ['./dashboard-flexprod.scss'],
    animations: fuseAnimations
})
export class FuseFlexProdDashboardComponent implements OnInit {
   
    private flexProdUser: Participant;
    public marketplaces = new Array<Marketplace>();


    constructor(private arrayService: ArrayService,
        private loginService: LoginService,
        private marketplaceService: CoreMarketplaceService,
        private flexProdService: CoreFlexProdService) {
    }

    ngOnInit() {
        this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
            this.flexProdUser = participant;
            this.loadSuggestedMarketplaces();
        });
    }

    private loadSuggestedMarketplaces() {
        Promise.all([
            this.marketplaceService.findAll().toPromise(),
            this.flexProdService.findRegisteredMarketplaces(this.flexProdUser.id).toPromise()
        ]).then((values: any[]) => {
            this.marketplaces = values[0];
            if (values[1]) {
                this.marketplaces = this.arrayService.removeAll(values[0], [values[1]]);
            }
        });
    }

    registerMarketplace(marketplace) {
        this.flexProdService.registerMarketplace(this.flexProdUser.id, marketplace.id).toPromise().then(() => {
            this.loadSuggestedMarketplaces();
        });
    }
}
