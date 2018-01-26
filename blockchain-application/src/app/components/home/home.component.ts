import {AfterViewInit, Component} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {Task} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements AfterViewInit {
  displayedColumns = ['taskId', 'description', 'taskStatus'];

  dataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService) {
  }

  ngAfterViewInit() {
    this.taskService.getAll().subscribe((data: Task[]) => this.dataSource.data = data);
  }

}
