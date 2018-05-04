import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {Subscription} from 'rxjs/Subscription';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {MessageService} from '../../_service/message.service';
import {Task} from '../../_model/task';
import {TaskInteraction} from '../../_model/task-interaction';

@Component({
  selector: 'app-task-interaction-history',
  templateUrl: './task-interaction-history.component.html'
})
export class TaskInteractionHistoryComponent implements OnInit, OnDestroy {
  @Input() id: string;
  dataSource = new MatTableDataSource<TaskInteraction>();
  displayedColumns = ['operation', 'timestamp', 'participant', 'comment'];

  private changeSubscription: Subscription;


  constructor(private taskInteractionService: TaskInteractionService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.taskInteractionService.findByTask(<Task>{id: this.id})
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => this.dataSource.data = taskInteractions);

    this.changeSubscription = this.messageService.subscribe('historyChanged', this.historyChanged.bind(this));

  }

  ngOnDestroy() {
    this.changeSubscription.unsubscribe();
  }

  private historyChanged() {
    this.taskInteractionService.findByTask(<Task>{id: this.id})
      .toPromise()
      .then((taskInteractions: TaskInteraction[]) => {
        this.dataSource.data = taskInteractions;
      });

  }


}
