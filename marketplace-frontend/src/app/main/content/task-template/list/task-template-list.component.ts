import {Component, OnInit} from '@angular/core';
import { TaskTemplateService } from '../../_service/task-template.service';
import { MatTableDataSource } from '@angular/material';
import { TaskTemplate } from '../../_model/task-template';
import { Marketplace } from '../../_model/marketplace';
import { Participant } from '../../_model/participant';
import { LoginService } from '../../_service/login.service';
import { CoreEmployeeService } from '../../_service/core-employee.service';

@Component({
  selector: 'fuse-task-template-list',
  templateUrl: './task-template-list.component.html',
  styleUrls: ['./task-template-list.component.scss'],
  providers: [TaskTemplateService, LoginService, CoreEmployeeService]
})
export class FuseTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<TaskTemplate>();
  displayedColumns = ['name', 'description', 'requiredCompetences', 'acquirableCompetences', 'actions'];

  constructor(private taskTemplateService: TaskTemplateService,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService
            )
  {}

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((employee: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(employee.id).toPromise().then((marketplace: Marketplace) => {
        this.taskTemplateService.findAll(marketplace)
        .toPromise()
        .then((taskTemplates: TaskTemplate[]) => this.dataSource.data = taskTemplates);
      });
    });
  }
}
