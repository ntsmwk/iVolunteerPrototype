import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {Task} from '../task';
import {Participant} from '../../participant/participant';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../task.service';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class TaskAssignComponent implements OnInit {

  dataSource = new MatTableDataSource<Participant>();
  displayedColumns = ['name'];
  task: Task;

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.loadData(params['id']);
    });
  }

  private loadData(id: string) {
    this.taskService.findById(id)
      .toPromise()
      .then((task: Task) => {
        this.task = task;
        this.taskInteractionService.findReservedVolunteersByTaskId(this.task)
          .toPromise()
          .then((volunteers: Participant[]) => this.dataSource.data = volunteers);
      });
  }


}
