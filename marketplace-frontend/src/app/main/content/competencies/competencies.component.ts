import {Component, OnInit} from '@angular/core';
import { List } from 'lodash'; 
import { Competence } from "../_model/competence";
import { CompetenceService } from '../_service/competence.service';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { VolunteerService } from '../_service/volunteer.service';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { VolunteerProfileService} from '../_service/volunteer-profile.service';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  providers: [LoginService, CompetenceService, VolunteerProfileService],
  animations: fuseAnimations
})
export class FuseCompetenceListComponent implements OnInit {

  private competencies: List<Competence> = [];
  private pageType: any;

  constructor(
    private competenceService: CompetenceService,
    private volunteerProfileService: VolunteerProfileService,
    private loginService: LoginService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {

    this.route.paramMap.subscribe(
      params => this.pageType = params.get('pageType')
    )
    switch(this.pageType){
      case 'all':
        this.competenceService.findAll().toPromise().then((comp: List<Competence>) => this.competencies = comp);
        break;
      case 'my':
        console.error("....")
        this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
          this.volunteerProfileService.findCompetencesByVolunteer(volunteer).toPromise().then((comp: List<Competence>) => this.competencies = comp);
        });
        break;
    }
  }

}
