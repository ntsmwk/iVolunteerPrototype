import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  dataSource = new MatTableDataSource<Task>();
  displayedColumns = ['name', 'type.name', 'status', 'startDate', 'endDate'];

  status = [
    {value: '', ViewValue: 'ALL'},
    {value: 'CREATED', ViewValue: 'CREATED'},
    {value: 'STARTED', ViewValue: 'STARTED'},
    {value: 'FINISHED', ViewValue: 'FINISHED'},
    {value: 'CANCELED', ViewValue: 'CANCELED'}
  ];
  selectedValue: string = this.status[0].value;

  constructor(private router: Router,
              private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.findAll()
      .toPromise()
      .then((tasks: Task[]) => this.dataSource.data = tasks);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  onRowSelect(task: Task) {
    this.router.navigate(['/task/' + task.id + '/details']);
  }

}
