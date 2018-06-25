import {Component, OnInit} from '@angular/core';
import { List } from 'lodash'; 
import { Competence } from "../_model/competence";
import { CompetenceService } from '../_service/competence.service';
import { fuseAnimations } from '@fuse/animations';
import { ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { VolunteerService } from '../_service/volunteer.service';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  providers: [CompetenceService],
  animations: fuseAnimations
})
export class FuseCompetenceListComponent implements OnInit {

  private competencies: List<Competence> = [];
  private pageType: any;

  constructor(
    private competenceService: CompetenceService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {

    this.route.paramMap.subscribe(
      params => this.pageType = params.get('pageType')
    )

    console.error(this.pageType);
    switch(this.pageType){
      case 'all':
        this.competenceService.findAll().toPromise().then((comp: List<Competence>) => this.competencies = comp);
        break;
      // case 'my':
      //   this.vol
    }

    
  }

}
