import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material';
import {Task} from '../_model/task';
import {Participant} from '../_model/participant';
import {Marketplace} from '../_model/marketplace';

import {CoreEmployeeService} from '../_service/core-employee.service';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit {
  marketplaces: Marketplace[];
  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'project', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private router: Router,
              private loginService: LoginService,
              private employeeService: CoreEmployeeService,
              private taskService: TaskService) {
  }

  ngOnInit() {
    this.loadAllTasks();
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.marketplaceId + '/' + task.id]);
  }

  private loadAllTasks() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.employeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplaces = [].concat(marketplace);
        this.taskService.findAll(marketplace).toPromise().then((tasks: Task[]) => this.dataSource.data = tasks);
      });
    });
  }


  getMarketplaceName(id: string) {
    return this.marketplaces.filter(marketplace => marketplace.id === id)[0].name;
  }
}
