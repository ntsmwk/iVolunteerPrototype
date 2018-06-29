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


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit {
  volunteer: Volunteer;
  header;
  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'marketplace', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private route: ActivatedRoute,
              private loginService: LoginService,
              private taskService: TaskService,
              private router: Router) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) =>
      this.volunteer = volunteer);


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
    this.taskService.findAllPublished()
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  loadUpcommingTasks() {
    this.taskService.findByParticipantAndState(this.volunteer.id, 'upcomming')
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  loadRunningTasks() {
    this.taskService.findByParticipantAndState(this.volunteer.id, 'running')
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  loadFinishedTasks() {
    this.taskService.findByParticipantAndState(this.volunteer.id, 'finished')
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.id]);
  }


}
