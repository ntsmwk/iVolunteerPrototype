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
  @Output()
  onAssign = new EventEmitter<string>();
  @Output()
  onFinish = new EventEmitter<string>();

  reserve(taskId: string) {
    this.onReserve.emit(taskId);
  }

  assign(taskId: string) {
    this.onAssign.emit(taskId);
  }

  finish(taskId: string) {
    this.onFinish.emit(taskId);
  }

}
