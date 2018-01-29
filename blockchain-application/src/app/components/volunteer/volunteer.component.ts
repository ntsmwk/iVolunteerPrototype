import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {Task, Volunteer} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';
import {VolunteerService} from '../../providers/volunteer.service';

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
              private volunteerService: VolunteerService) {
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

}
