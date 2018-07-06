import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Task} from '../_model/task';
import {Participant} from '../_model/participant';
import {Competence} from '../_model/competence';
import {VolunteerProfileService} from '../_service/volunteer-profile.service';
import {Volunteer} from '../_model/volunteer';
import {CoreVolunteerService} from '../_service/core.volunteer.service';
import {Marketplace} from '../_model/marketplace';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit {
  header;
  dataSource = new MatTableDataSource<Task>();
  data: Task[] = [];
  displayedColumns = ['name', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private route: ActivatedRoute,
              private loginService: LoginService,
              private taskService: TaskService,
              private coreVolunteerService: CoreVolunteerService,
              private router: Router) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(
      map => {
        const pageType = map.get('pageType');
        console.log(pageType);

        switch (pageType) {
          case 'available': {
            this.header = 'Available Tasks';
            this.loadAvailableTasks();
            break;
          }
          case 'upcomming': {
            this.header = 'Upcomming Tasks';
            this.loadUpcommingTasks();
            break;
          }
          case 'running': {
            this.header = 'Running Tasks';
            this.loadRunningTasks();
            break;
          }
          case 'finished': {
            this.header = 'Finished Tasks';
            this.loadFinishedTasks();
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
            this.data.concat(tasks);
          });
        });
        // TODO
        this.dataSource.data = this.data;
      });
    });
  }

  loadUpcommingTasks() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        marketplaces.forEach(marketplace => {
          this.taskService.findByParticipantAndState(volunteer.id, 'upcomming', marketplace.url)
            // TODO find proper way to add tasks to matTable dataSource
            .toPromise().then((tasks: Task[]) => {
            this.dataSource.data.push.apply(this.dataSource.data, tasks);
          });
        });
      });
    });
  }

  loadRunningTasks() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        marketplaces.forEach(marketplace => {
          this.taskService.findByParticipantAndState(volunteer.id, 'running', marketplace.url)
          // TODO find proper way to add tasks to matTable dataSource
            .toPromise().then((tasks: Task[]) => this.dataSource.data.push.apply(this.dataSource.data, tasks));
        });
      });
    });
  }

  loadFinishedTasks() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
        marketplaces.forEach(marketplace => {
          this.taskService.findByParticipantAndState(volunteer.id, 'finished', marketplace.url)
          // TODO find proper way to add tasks to matTable dataSource
            .toPromise().then((tasks: Task[]) => this.dataSource.data.push.apply(this.dataSource.data, tasks));
        });
      });
    });
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.id]);
  }


}
