import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { TaskTemplate } from '../_model/task-template';
import { TaskTemplateService } from '../_service/task-template.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';
import { Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { isNullOrUndefined } from 'util';

import { MinimalTaskTemplate } from "../_model/minimal-task-template";

@Component({
  selector: 'fuse-task-template-list',
  templateUrl: './task-template-list.component.html',
  styleUrls: ['./task-template-list.component.scss'],
  animations: fuseAnimations
})
export class FuseTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<TaskTemplate>();
  displayedColumns = ['name', 'description', 'requiredCompetences', 'acquirableCompetences', 'actions'];


  //TEST
  dataSourceMinimal = new MatTableDataSource<MinimalTaskTemplate>();
  displayedColumnsMinimal = ['id', 'name', 'description'];
  

  constructor(private taskTemplateService: TaskTemplateService,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private router: Router) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.taskTemplateService.findAll(marketplace)
            .toPromise()
            .then((taskTemplates: TaskTemplate[]) => this.dataSource.data = taskTemplates);
          
          //TESTING
          this.taskTemplateService.findAllMinimal(marketplace)
            .toPromise()
            .then((minimalTaskTemplates: MinimalTaskTemplate[]) => this.dataSourceMinimal.data = minimalTaskTemplates);
            //TESTING_END
        }
      });
    });
  }

  addTaskTemplate() {
    this.router.navigate(['/main/task-template-form']);
  }
}
