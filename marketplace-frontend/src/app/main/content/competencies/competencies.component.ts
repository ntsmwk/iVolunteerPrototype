import {Component} from '@angular/core';
import { List } from 'lodash'; 
import { Competence } from "../_model/competence";
import { CompetenceService } from '../_service/competence.service';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  providers: [CompetenceService],
  animations: fuseAnimations
})
export class FuseCompetenceListComponent {

  private competencies: List<Competence> = [];

  constructor(
    private competenceService: CompetenceService
  ) {}

  ngOnInit() {
    this.competenceService.findAll().toPromise().then((comp: List<Competence>) => this.competencies = comp);
  }

}
