import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Task} from '../_model/task';
import {Participant} from '../_model/participant';
import {CoreVolunteerService} from '../_service/core.volunteer.service';
import {Marketplace} from '../_model/marketplace';
import {Subscription} from 'rxjs';
import {MessageService} from '../_service/message.service';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit, OnDestroy {
  header;
  dataSource = new MatTableDataSource<Task>();
  marketplaceChangeSubscription: Subscription;
  displayedColumns = ['name', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private route: ActivatedRoute,
              private loginService: LoginService,
              private taskService: TaskService,
              private coreVolunteerService: CoreVolunteerService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit() {
    this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadTasks.bind(this));

    this.dataSource.data = [];
    this.loadTasks();
  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

  loadTasks() {
    this.route.paramMap.subscribe(
      params => {
        const pageType = params.get('pageType');
        switch (pageType) {
          case 'available': {
            this.header = 'Available Tasks';
            this.loadAvailableTasks();
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
          default:
            throw new Error('Page type not supported');
        }
      }
    );
  }

  loadAvailableTasks() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        marketplaces.forEach(marketplace => {
          this.taskService.findAllPublished(marketplace.url)
            .toPromise().then((tasks: Task[]) => {

            // TODO: test
            const data = this.dataSource.data;
            tasks.forEach(task => {
              data.push(task);
            });
            this.dataSource.data = data;
          });
        });
      });
    });
  }

  loadStatusTasks(state: string) {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        marketplaces.forEach(marketplace => {
          this.taskService.findByParticipantAndState(volunteer.id, state, marketplace.url)
            .toPromise().then((tasks: Task[]) => {

            // TODO: test
            const data = this.dataSource.data;
            tasks.forEach(task => {
              data.push(task);
            });
            this.dataSource.data = data;
          });
        });
      });
    });
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.id]);
  }


}
