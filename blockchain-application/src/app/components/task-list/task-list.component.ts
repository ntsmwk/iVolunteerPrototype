import {MatTableDataSource} from '@angular/material';
import {Component, Input} from '@angular/core';
import {Task} from 'app/model/at.jku.cis';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html'
})
export class TaskListComponent {

  @Input()
  titleKey: string;

  @Input()
  dataSource: MatTableDataSource<Task>;
  displayedColumns = ['taskId', 'description', 'taskStatus'];

}
