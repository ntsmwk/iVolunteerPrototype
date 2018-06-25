import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Task} from '../_model/task';


@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']

})
export class FuseTaskListComponent implements OnInit {

  participant;
  participantRole;

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'status', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  status: { value: string; viewValue: string }[];
  selectedValue: string;

  pageType;

  constructor(private route: ActivatedRoute,
              private loginService: LoginService,
              private taskService: TaskService,
              private router: Router) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(
      map => {
        const pageType = map.get('pageType');
        console.log(pageType);
        switch (this.pageType) {
          case 'available': {
            this.loadAvailableTasks();
            break;
          }
          case 'upcomming': {
            this.loadUpcommingTasks();
            break;
          }
          case 'running': {
            this.loadRunningTasks();
            break;
          }
          case 'finished': {
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

  }

  loadRunningTasks() {

  }

  loadFinishedTasks() {

  }

  onRowSelect(task: Task) {
    this.router.navigate(['/main/task/' + task.id]);
  }


}
