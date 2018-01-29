import {AfterViewInit, Component} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {Task} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './volunteer.component.html'
})
export class VolunteerComponent implements AfterViewInit {
  dataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService) {
  }

  ngAfterViewInit() {
    const organisationId = localStorage.getItem('person.id');
    this.taskService.getAll().subscribe((data: Task[]) => this.dataSource.data = data);
  }

}
