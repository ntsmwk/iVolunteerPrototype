import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

}
