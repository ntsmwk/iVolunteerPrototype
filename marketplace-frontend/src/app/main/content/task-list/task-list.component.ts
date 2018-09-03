import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { LoginService } from '../_service/login.service';
import { TaskService } from '../_service/task.service';
import { Task } from '../_model/task';
import { Subscription } from 'rxjs';
import { MessageService } from '../_service/message.service';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';
import { CoreEmployeeService } from '../_service/core-employee.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Project } from '../_model/project';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit, OnDestroy {
  header: string;
  dataSource = new MatTableDataSource<Task>();
  marketplaceChangeSubscription: Subscription;
  displayedColumns = ['name', 'project', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];
  allMarketplaces: Marketplace[];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private loginService: LoginService,
    private coreEmployeeService: CoreEmployeeService,
    private coreMarketplaceService: CoreMarketplaceService,
    private taskService: TaskService) {
  }

  ngOnInit() {
    this.coreMarketplaceService.findAll()
      .toPromise()
      .then((marketplaces: Marketplace[]) => {
        this.allMarketplaces = marketplaces;
      });

    this.loadTasks();
    this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadTasks.bind(this));
  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

  loadTasks() {
    this.route.paramMap.subscribe(params => {
      this.dataSource.data = [];
      switch (params.get('pageType').toLowerCase()) {
        case 'available': {
          this.header = 'Available Tasks';
          this.loadStatusTasks('available');
          break;
        }
        case 'upcomming': {
          this.header = 'Upcomming Tasks';
          this.loadStatusTasks('upcomming');
          break;
        }
        case 'running': {
          this.header = 'Running Tasks';
          this.loadStatusTasks('running');
          break;
        }
        case 'finished': {
          this.header = 'Finished Tasks';
          this.loadStatusTasks('finished');
          break;
        }
        case 'all': {
          this.header = 'All Tasks';
          this.loadAllTasks();
          break;
        }
        default: {
          throw new Error('Page type not supported');
        }
      }
    });
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.marketplaceId + '/' + task.id]);
  }

  private loadStatusTasks(state: string) {
    this.loginService.getLoggedIn()
      .toPromise()
      .then((participant: Participant) => {
        JSON.parse(localStorage.getItem('marketplaces')).forEach(marketplace => {
          this.taskService.findByParticipantAndState(marketplace, participant.id, state)
            .toPromise()
            .then((tasks: Task[]) => {
              // TODO: test
              const data = this.dataSource.data;
              tasks.forEach(task => {
                data.push(task);
              });
              this.dataSource.data = data;
            });
        });
      });
  }

  private loadAllTasks() {
    this.loginService.getLoggedIn()
      .toPromise()
      .then((participant: Participant) => {
        this.coreEmployeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
          this.taskService.findAll(marketplace)
            .toPromise()
            .then((tasks: Task[]) => this.dataSource.data = tasks);
        });
      });
  }


  getMarketplaceName(id: string) {
    return this.allMarketplaces.filter(marketplace => marketplace.id === id)[0].name;
  }
}
