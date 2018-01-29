import {AfterViewInit, Component} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {Task} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';

@Component({
  templateUrl: './organisation.component.html'
})
export class OrganisationComponent implements AfterViewInit {
  organisationId: string | null;

  dataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService) {
    this.organisationId = localStorage.getItem('person.id');
  }

  appendTask(task: Task) {
    this.dataSource.data = this.dataSource.data.concat([task]);
  }

  ngAfterViewInit() {
    console.log(this.organisationId);
    this.taskService.getAllByOrganisation(this.organisationId).subscribe((data: Task[]) => this.dataSource.data = data);
  }
}
