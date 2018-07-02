import {Component, OnInit} from '@angular/core';
import { Competence } from "../_model/competence";
import { CompetenceService } from '../_service/competence.service';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { VolunteerService } from '../_service/volunteer.service';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { VolunteerProfileService} from '../_service/volunteer-profile.service';
import { CoreVolunteerService } from '../_service/core.volunteer.service';
import { Marketplace } from '../_model/marketplace';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  providers: [LoginService, CompetenceService, VolunteerProfileService],
  animations: fuseAnimations
})
export class FuseCompetenceListComponent implements OnInit {

  private competencies: Competence[] = [];
  private pageType: any;

  constructor(
    private competenceService: CompetenceService,
    private volunteerProfileService: VolunteerProfileService,
    private coreVolunteerService: CoreVolunteerService,
    private loginService: LoginService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {

    this.route.paramMap.subscribe(
      params => this.pageType = params.get('pageType')
    )
    switch(this.pageType){
      case 'all':
        this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
          console.error("volunteer: " + JSON.stringify(volunteer));
          this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[])=> {
            console.error("marketplaces: " + marketplaces);
            marketplaces.forEach(marketplace => {
              console.error("mp: " + marketplace);
              this.volunteerProfileService.findCompetencesByVolunteer(volunteer, marketplace.url).toPromise()
                .then((comp: Competence[]) => this.competencies.concat(comp));
            });
          });
        });  
        break;
      case 'my':

      //TODO change to my competences... currently all...
        this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
          console.error("volunteer: " + JSON.stringify(volunteer));
          this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[])=> {
            console.error("marketplaces: " + marketplaces);
            marketplaces.forEach(marketplace => {
              console.error("mp: " + marketplace);
              this.volunteerProfileService.findCompetencesByVolunteer(volunteer, marketplace.url).toPromise()
                .then((comp: Competence[]) => this.competencies.concat(comp));
            });
          });
        });
        break;
    }
  }

}
