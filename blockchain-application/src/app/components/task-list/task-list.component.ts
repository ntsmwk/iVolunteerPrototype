import {MatTableDataSource} from '@angular/material';
import {Component, EventEmitter, Input, Output} from '@angular/core';
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

  @Input()
  displayedColumns = ['taskId', 'description', 'taskStatus', 'creator'];

  @Output()
  onReserve = new EventEmitter<string>();

  constructor() {

  }

  reserve(taskId: string) {
    this.onReserve.emit(taskId);
  }

  assign(taskId: string) {
    console.log(taskId);
  }

}
