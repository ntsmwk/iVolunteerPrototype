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
  displayedColumns = ['name', 'type.name', 'status', 'startDate', 'endDate'];
  participantRole;
  participant;

  statusEmp = [
    {value: '', ViewValue: 'ALL'},
    {value: 'CREATED', ViewValue: 'CREATED'},
    {value: 'STARTED', ViewValue: 'STARTED'},
    {value: 'FINISHED', ViewValue: 'FINISHED'},
    {value: 'CANCELED', ViewValue: 'CANCELED'}
  ];
  statusVol = [
    {value: '', ViewValue: 'ALL'},
    {value: 'STARTED', ViewValue: 'STARTED'},
    {value: 'FINISHED', ViewValue: 'FINISHED'},
    {value: 'CANCELED', ViewValue: 'CANCELED'}
  ];
  selectedValue: string = this.statusEmp[0].value;

  constructor(private router: Router,
              private loginService: LoginService,
              private taskService: TaskService) {
  }

  ngOnInit() {
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.participantRole = role);
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.writeDataSource();
    });
  }

  writeDataSource() {
    if (this.participantRole === 'EMPLOYEE') {
      this.taskService.findAll()
        .toPromise()
        .then((tasks: Task[]) => this.dataSource.data = tasks);
    } else {
      this.taskService.findByVolunteerId(this.participant.id)
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
    this.router.navigate(['/task/' + task.id + '/details']);
  }

}
