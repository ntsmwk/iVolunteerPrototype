import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../../_service/login.service';
import {Participant} from '../../_model/participant';

@Component({
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  participant;
  participantRole;

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'status', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  status: { value: string; viewValue: string }[];
  selectedValue: string;

  constructor(private router: Router,
              private loginService: LoginService,
              private taskService: TaskService) {
  }

  ngOnInit() {
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.participantRole = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
        this.participant = participant;
      })
    ]).then(() => this.loadTasks());
  }

  loadTasks() {
    if (this.participantRole === 'EMPLOYEE') {
      this.status = [
        {value: '', viewValue: 'ALL'},
        {value: 'CREATED', viewValue: 'CREATED'},
        {value: 'PUBLISED', viewValue: 'PUBLISHED'},
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
    this.router.navigate(['/task/' + task.id + '/detail']);
  }

}
