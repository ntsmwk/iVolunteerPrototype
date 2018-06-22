import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './task-available.component.html',
  styleUrls: ['./task-available.component.css']
})
export class TaskAvailableComponent implements OnInit {

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'startDate', 'endDate', 'requiredCompetences', 'acquirableCompetences'];

  constructor(private router: Router,
              private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.findAllPublished()
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/task/' + task.id + '/detail']);
  }

}
