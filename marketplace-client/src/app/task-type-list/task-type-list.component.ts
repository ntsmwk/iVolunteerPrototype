import { Component, OnInit } from '@angular/core';
import {TaskType} from '../task-type/task-type';
import {TaskTypeService} from '../task-type/task-type.service';

@Component({
  selector: 'app-task-type-list',
  templateUrl: './task-type-list.component.html',
  styleUrls: ['./task-type-list.component.css']
})
export class TaskTypeListComponent implements OnInit {
  taskTypes: TaskType[];
  columnsToDisplay = ['name', 'description'];


  constructor(private taskTypeService: TaskTypeService) {
  }

  ngOnInit() {
    this.taskTypeService.findAll().subscribe((taskTypes: TaskType[]) => this.taskTypes = taskTypes);
  }

}
