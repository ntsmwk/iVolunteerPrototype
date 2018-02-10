///<reference path="../../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {FinishTask, ReserveTask, Task, Volunteer} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';
import {VolunteerService} from '../../providers/volunteer.service';
import {ReserveTaskService} from '../../providers/reserve-task.service';
import {FinishTaskService} from '../../providers/finish-task.service';

@Component({
  selector: 'app-volunteer',
  templateUrl: './volunteer.component.html'
})
export class VolunteerComponent implements AfterViewInit {

  @Input('personId')
  private volunteerId: string | null;

  createdDataSource = new MatTableDataSource<Task>();
  reservedDataSource = new MatTableDataSource<Task>();
  assignedDataSource = new MatTableDataSource<Task>();
  finishedDataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService,
              private volunteerService: VolunteerService,
              private reserveTaskService: ReserveTaskService,
              private finishTaskService: FinishTaskService) {
  }

  ngAfterViewInit() {
    this.taskService.getAllCreated().subscribe((data: Task[]) => this.createdDataSource.data = data);
    this.taskService.getAllReservedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.reservedDataSource.data = data);
    this.taskService.getAllAssignedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.assignedDataSource.data = data);
    this.taskService.getAllFinishedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.finishedDataSource.data = data);
  }

  reserve(taskId: string) {
    const reserveTask = new ReserveTask();
    reserveTask.task = taskId;
    reserveTask.volunteer = this.volunteerId;
    this.reserveTaskService.addAsset(reserveTask).subscribe(() => {
      this.taskService.getAllCreated().subscribe((data: Task[]) => this.createdDataSource.data = data);
      this.taskService.getAllReservedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.reservedDataSource.data = data);
    });
  }

  finish(taskId: string) {
    const finishTask = <FinishTask> {task: taskId};
    this.finishTaskService.addAsset(finishTask).subscribe(() => {
      this.taskService.getAllAssignedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.assignedDataSource.data = data);
      this.taskService.getAllFinishedByVolunteer(this.volunteerId).subscribe((data: Task[]) => this.finishedDataSource.data = data);
    });

  }

}
