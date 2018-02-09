///<reference path="../../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {FinishTask, ReserveTask, Task, Volunteer} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';
import {VolunteerService} from '../../providers/volunteer.service';
import {ReserveTaskService} from '../../providers/reserve-task.service';
import {FinishTaskService} from '../../providers/finish-task.service';

@Component({
  templateUrl: './volunteer.component.html'
})
export class VolunteerComponent implements OnInit, AfterViewInit {

  volunteer: Volunteer;

  createdDataSource = new MatTableDataSource<Task>();
  reservedDataSource = new MatTableDataSource<Task>();
  assignedDataSource = new MatTableDataSource<Task>();
  finishedDataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService,
              private volunteerService: VolunteerService,
              private reserveTaskService: ReserveTaskService,
              private finishTaskService: FinishTaskService) {
  }

  ngOnInit() {
    const volunteerId = localStorage.getItem('person.id');
    this.volunteerService.getAsset(volunteerId).subscribe((volunteer: Volunteer) => this.volunteer = volunteer);
  }

  ngAfterViewInit() {
    const volunteerId = localStorage.getItem('person.id');
    this.taskService.getAllCreated().subscribe((data: Task[]) => this.createdDataSource.data = data);
    this.taskService.getAllReservedByVolunteer(volunteerId).subscribe((data: Task[]) => this.reservedDataSource.data = data);
    this.taskService.getAllAssignedByVolunteer(volunteerId).subscribe((data: Task[]) => this.assignedDataSource.data = data);
    this.taskService.getAllFinishedByVolunteer(volunteerId).subscribe((data: Task[]) => this.finishedDataSource.data = data);
  }

  reserve(taskId: string) {
    const volunteerId = localStorage.getItem('person.id');
    const reserveTask = new ReserveTask();
    reserveTask.task = taskId;
    reserveTask.volunteer = volunteerId;
    this.reserveTaskService.addAsset(reserveTask).subscribe(() => {
      this.taskService.getAllCreated().subscribe((data: Task[]) => this.createdDataSource.data = data);
      this.taskService.getAllReservedByVolunteer(volunteerId).subscribe((data: Task[]) => this.reservedDataSource.data = data);
    });
  }

  finish(taskId: string) {
    const volunteerId = localStorage.getItem('person.id');
    const finishTask = <FinishTask> {task: taskId};
    this.finishTaskService.addAsset(finishTask).subscribe(() => {
      this.taskService.getAllAssignedByVolunteer(volunteerId).subscribe((data: Task[]) => this.assignedDataSource.data = data);
      this.taskService.getAllFinishedByVolunteer(volunteerId).subscribe((data: Task[]) => this.finishedDataSource.data = data);
    });

  }

}
