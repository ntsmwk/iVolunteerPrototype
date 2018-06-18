import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskTemplate} from '../../_model/task-template';
import {TaskTemplateService} from '../../_service/task-template.service';

@Component({
  templateUrl: './task-template-list.component.html'
})
export class TaskTemplateListComponent implements OnInit {
  dataSource = new MatTableDataSource<TaskTemplate>();

  displayedColumns = ['name', 'description', 'requiredCompetences', 'acquirableCompetences', 'actions'];

  constructor(private taskTemplateService: TaskTemplateService) {
  }

  ngOnInit() {
    this.taskTemplateService.findAll()
      .toPromise()
      .then((taskTemplates: TaskTemplate[]) => this.dataSource.data = taskTemplates);
  }

}
