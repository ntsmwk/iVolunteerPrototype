import {AfterViewInit, Component} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {Task} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './volunteer.component.html'
})
export class VolunteerComponent implements AfterViewInit {
  createdDataSource = new MatTableDataSource<Task>();
  reservedDataSource = new MatTableDataSource<Task>();
  assignedDataSource = new MatTableDataSource<Task>();
  finishedDataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService) {
  }

  ngAfterViewInit() {
    const volunteerId = localStorage.getItem('person.id');
    this.taskService.getAllCreated().subscribe((data: Task[]) => this.createdDataSource.data = data);
    this.taskService.getAllReservedByVolunteer(volunteerId).subscribe((data: Task[]) => this.reservedDataSource.data = data);
    this.taskService.getAllAssignedByVolunteer(volunteerId).subscribe((data: Task[]) => this.assignedDataSource.data = data);
    this.taskService.getAllFinishedByVolunteer(volunteerId).subscribe((data: Task[]) => this.finishedDataSource.data = data);
  }

}
