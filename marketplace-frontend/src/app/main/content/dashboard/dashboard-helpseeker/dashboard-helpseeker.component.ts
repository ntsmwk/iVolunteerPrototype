import { OnInit, Component } from "@angular/core";
import { ArrayService } from "../../_service/array.service";
import { LoginService } from "../../_service/login.service";
import { CoreMarketplaceService } from "../../_service/core-marketplace.service";
import { CoreVolunteerService } from "../../_service/core-volunteer.service";
import { CoreHelpSeekerService } from "../../_service/core-helpseeker.service";
import { Subscription } from "rxjs";
import { Participant } from "../../_model/participant";
import { Marketplace } from "../../_model/marketplace";
import { fuseAnimations } from "@fuse/animations";


@Component({
    selector: 'fuse-dashboard-helpseeker',
    templateUrl: './dashboard-helpseeker.component.html',
    styleUrls: ['./dashboard-helpseeker.scss'],
    animations: fuseAnimations
})
export class FuseHelpSeekerDashboardComponent implements OnInit {
   
    private helpSeeker: Participant;
    public marketplaces = new Array<Marketplace>();


    constructor(private arrayService: ArrayService,
        private loginService: LoginService,
        private marketplaceService: CoreMarketplaceService,
        private helpSeekerService: CoreHelpSeekerService) {
    }

    ngOnInit() {
        this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
            this.helpSeeker = participant;
            this.loadSuggestedMarketplaces();
        });
    }

    private loadSuggestedMarketplaces() {
        Promise.all([
            this.marketplaceService.findAll().toPromise(),
            this.helpSeekerService.findRegisteredMarketplaces(this.helpSeeker.id).toPromise()
        ]).then((values: any[]) => {
            this.marketplaces = values[0];
            if (values[1]) {
                this.marketplaces = this.arrayService.removeAll(values[0], [values[1]]);
            }
        });
    }

    registerMarketplace(marketplace) {
        this.helpSeekerService.registerMarketplace(this.helpSeeker.id, marketplace.id).toPromise().then(() => {
            this.loadSuggestedMarketplaces();
        });
    }
}
