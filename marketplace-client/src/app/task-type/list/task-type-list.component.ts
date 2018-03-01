import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskType} from '../task-type';
import {TaskTypeService} from '../task-type.service';

@Component({
  selector: 'app-task-type-list',
  templateUrl: './task-type-list.component.html'
})
export class TaskTypeListComponent implements OnInit {
  dataSource = new MatTableDataSource<TaskType>();

  displayedColumns = ['name', 'description'];

  constructor(private taskTypeService: TaskTypeService) {
  }

  ngOnInit() {
    this.taskTypeService.findAll()
      .toPromise()
      .then((taskTypes: TaskType[]) => this.dataSource.data = taskTypes);
  }

}
