import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {MatTableDataSource} from '@angular/material';
import {LoginService} from '../../login/login.service';
import {Participant} from '../../participant/participant';

@Component({
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'status', 'startDate', 'endDate'];
  participantRole;
  participant;

  status = [
    {value: '', ViewValue: 'ALL'},
    {value: 'CREATED', ViewValue: 'CREATED'},
    {value: 'RUNNING', ViewValue: 'RUNNING'},
    {value: 'SUSPENDED', ViewValue: 'SUSPENDED'},
    {value: 'FINISHED', ViewValue: 'FINISHED'},
    {value: 'ABORTED', ViewValue: 'ABORTED'}
  ];

  selectedValue: string = this.status[0].value;

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
      this.taskService.findAll()
        .toPromise()
        .then((tasks: Task[]) => this.dataSource.data = tasks);
    } else {
      this.taskService.findAllByParticipant(this.participant.id)
        .toPromise()
        .then((tasks: Task[]) => this.dataSource.data = tasks);
    }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/task/' + task.id + '/detail']);
  }

}
