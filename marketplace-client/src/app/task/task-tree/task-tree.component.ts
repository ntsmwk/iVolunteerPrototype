import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../_service/task.service';
import {Router} from '@angular/router';
import {LoginService} from '../../_service/login.service';

@Component({
  selector: 'app-hierarchy-tree',
  templateUrl: './task-tree.component.html',
  styleUrls: ['./task-tree.component.css']
})
export class TaskTreeComponent implements OnInit {

  // TODO
  // https://material.angular.io/components/tree/examples
  // example "Tree with dynamic data"


  constructor(private router: Router,
              private loginService: LoginService,
              private taskService: TaskService) { }

  ngOnInit() {
     }

}
