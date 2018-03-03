import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './available.component.html',
  styleUrls: ['./available.component.css']
})
export class TaskAvailableComponent implements OnInit {

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'type.name', 'startDate', 'endDate'];

  constructor(private router: Router,
              private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.findCreated()
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/task/' + task.id + '/details']);
  }

}
