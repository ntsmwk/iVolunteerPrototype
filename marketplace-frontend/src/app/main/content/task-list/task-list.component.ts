import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';

import { Task } from '../_model/task';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { LoginService } from '../_service/login.service';
import { TaskService } from '../_service/task.service';
import { fuseAnimations } from '@fuse/animations';
import { isNullOrUndefined } from 'util';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss'],
  animations: fuseAnimations

})
export class FuseTaskListComponent implements OnInit {
  marketplaces: Marketplace[];
  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'project', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
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
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplaces = [].concat(marketplace);
          this.taskService.findAll(marketplace).toPromise().then((tasks: Task[]) => this.dataSource.data = tasks);
        }
      });
    });
  }


  getMarketplaceName(id: string) {
    return this.marketplaces.filter((marketplace:Marketplace) => {return marketplace.id === id})[0].name;
  }

  addTask() {
    this.router.navigate(['/main/task-form']);
  }
}
