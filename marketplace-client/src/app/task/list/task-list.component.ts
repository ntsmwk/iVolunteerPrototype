import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './task-list.component.html'
})
export class TaskListComponent implements OnInit {

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'description', 'type.name', 'startDate', 'endDate'];

  constructor(private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.findAll()
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

}
