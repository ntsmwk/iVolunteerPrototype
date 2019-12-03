import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import { fuseAnimations } from '../../../../../@fuse/animations';
import { Project } from '../../_model/project';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  animations: fuseAnimations

})
export class CompetenciesComponent implements OnInit, AfterViewInit {
  public participant: Participant;



  constructor(private loginService: LoginService) {

  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
    });
  }

  ngAfterViewInit() {

  }

}
