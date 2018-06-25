import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {filter} from 'rxjs/internal/operators';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Participant} from '../_model/participant';
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
        this.pageType = map.get('pageType');
      }
    );

    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.participantRole = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
        this.participant = participant;
      })
    ]).then(() => {

      switch (this.pageType) {
        case 'available': {
          console.log(this.pageType);
          this.loadAvailableTasks();
          break;
        }
        case 'upcomming': {
          console.log(this.pageType);
          this.loadUpcommingTasks();
          break;
        }
        case 'running': {
          console.log(this.pageType);
          this.loadRunningTasks();
          break;
        }
        case 'finished': {
          console.log(this.pageType);
          this.loadFinishedTasks();
          break;
        }
      }
    });
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

  loadTasks() {
    if (this.participantRole === 'EMPLOYEE') {
      this.status = [
        {value: '', viewValue: 'ALL'},
        {value: 'CREATED', viewValue: 'CREATED'},
        {value: 'PUBLISHED', viewValue: 'PUBLISHED'},
        {value: 'RUNNING', viewValue: 'RUNNING'},
        {value: 'SUSPENDED', viewValue: 'SUSPENDED'},
        {value: 'FINISHED', viewValue: 'FINISHED'},
        {value: 'ABORTED', viewValue: 'ABORTED'}
      ];
      this.selectedValue = this.status[0].value;

      this.taskService.findAll().toPromise().then((tasks: Task[]) => this.dataSource.data = tasks);
    } else {
      this.status = [
        {value: '', viewValue: 'ALL'},
        {value: 'PUBLISHED', viewValue: 'PUBLISHED'},
        {value: 'RUNNING', viewValue: 'RUNNING'},
        {value: 'SUSPENDED', viewValue: 'SUSPENDED'},
        {value: 'FINISHED', viewValue: 'FINISHED'},
        {value: 'ABORTED', viewValue: 'ABORTED'}
      ];
      this.selectedValue = this.status[0].value;
      this.taskService.findAllByParticipant(this.participant.id).toPromise().then((tasks: Task[]) => this.dataSource.data = tasks);

    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  onRowSelect(task: Task) {
    // this.router.navigate(['/task/' + task.id + '/detail']);
  }



}
