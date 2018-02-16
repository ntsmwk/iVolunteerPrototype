import {AfterViewInit, Component, Input} from '@angular/core';
import {TaskService} from '../../providers/task.service';
import {AssignTask, Task} from 'app/model/at.jku.cis';
import {MatTableDataSource} from '@angular/material';
import {AssignTaskService} from '../../providers/assign-task.service';

@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html'
})
export class OrganisationComponent implements AfterViewInit {

  @Input('personId')
  private organisationId: string | null;

  createdDataSource = new MatTableDataSource<Task>();
  reservedDataSource = new MatTableDataSource<Task>();
  assignedDataSource = new MatTableDataSource<Task>();
  finishedDataSource = new MatTableDataSource<Task>();

  constructor(private taskService: TaskService,
              private assignTaskService: AssignTaskService) {
  }


  ngAfterViewInit() {
    this.taskService.getAllByOrganisation(this.organisationId).subscribe((tasks: Task[]) => {
      this.createdDataSource.data = tasks.filter((task: Task) => 'CREATED' === task.taskStatus);
      this.reservedDataSource.data = tasks.filter((task: Task) => 'RESERVED' === task.taskStatus);
      this.assignedDataSource.data = tasks.filter((task: Task) => 'ASSIGNED' === task.taskStatus);
      this.finishedDataSource.data = tasks.filter((task: Task) => 'FINISHED' === task.taskStatus);
    });
  }

  appendTask(task: Task) {
    this.createdDataSource.data = this.createdDataSource.data.concat([task]);
  }

  assignTask(taskId: string) {
    this.taskService.getAsset(taskId).subscribe((task: Task) => {
        const assignTask = <AssignTask> {'task': task.taskId, 'taskPerformer': []};
        task.reservedVolunteers.forEach((resource: string) => {
          assignTask.taskPerformer = assignTask.taskPerformer.concat([resource.split('#')[1]]);
        });
        this.assignTaskService.addAsset(assignTask).subscribe(() => this.ngAfterViewInit());
      }
    );
  }
}
