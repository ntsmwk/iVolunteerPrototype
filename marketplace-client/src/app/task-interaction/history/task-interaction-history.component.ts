import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {Task} from '../../task/task';
import {TaskInteraction} from '../task-interaction';
import {TaskInteractionService} from '../task-interaction.service';

@Component({
  selector: 'app-task-interaction-history',
  templateUrl: './task-interaction-history.component.html'
})
export class TaskInteractionHistoryComponent implements OnInit {
  @Input() id: string;
  dataSource = new MatTableDataSource<TaskInteraction>();
  displayedColumns = ['operation', 'timestamp', 'participant', 'comment'];

  constructor(private taskInteractionService: TaskInteractionService) {
  }

  ngOnInit() {
    this.taskInteractionService.findByTask(<Task>{id: this.id})
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => this.dataSource.data = taskInteractions);
  }

}
