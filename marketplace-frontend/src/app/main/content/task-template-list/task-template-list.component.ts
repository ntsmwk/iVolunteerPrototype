import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskTemplate} from '../_model/task-template';
import {TaskTemplateService} from '../_service/task-template.service';
import {LoginService} from '../_service/login.service';
import {CoreHelpSeekerService} from '../_service/core-helpseeker.service';
import {Participant} from '../_model/participant';
import {Marketplace} from '../_model/marketplace';
import { Router } from '@angular/router';

@Component({
  selector: 'fuse-task-template-list',
  templateUrl: './task-template-list.component.html',
  styleUrls: ['./task-template-list.component.scss']
})
export class FuseTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<TaskTemplate>();
  displayedColumns = ['name', 'description', 'requiredCompetences', 'acquirableCompetences', 'actions'];

  constructor(private taskTemplateService: TaskTemplateService,
              private loginService: LoginService,
              private coreHelpSeekerService: CoreHelpSeekerService,
              private router: Router) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        this.taskTemplateService.findAll(marketplace)
          .toPromise()
          .then((taskTemplates: TaskTemplate[]) => this.dataSource.data = taskTemplates);
      });
    });
  }

  addTaskTemplate(){
    this.router.navigate(['/main/task-template-form']);
  }
}
