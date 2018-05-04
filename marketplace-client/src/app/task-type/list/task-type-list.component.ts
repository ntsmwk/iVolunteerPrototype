import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskType} from '../../_model/task-type';
import {TaskTypeService} from '../../_service/task-type.service';

@Component({
  templateUrl: './task-type-list.component.html'
})
export class TaskTypeListComponent implements OnInit {
  dataSource = new MatTableDataSource<TaskType>();

  displayedColumns = ['name', 'description', 'requiredCompetences', 'acquirableCompetences', 'actions'];

  constructor(private taskTypeService: TaskTypeService) {
  }

  ngOnInit() {
    this.taskTypeService.findAll()
      .toPromise()
      .then((taskTypes: TaskType[]) => this.dataSource.data = taskTypes);
  }

}
