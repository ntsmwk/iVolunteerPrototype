import {Component, OnInit} from '@angular/core';
import {RepositoryService} from '../../_service/repository.service';
import {Task} from '../../task/task';
import {TaskService} from '../../task/task.service';
import {Participant} from '../../participant/participant';
import {EmployeeService} from '../../employee/employee.service';

@Component({
  templateUrl: './volunteer-repository.component.html'
})
export class VolunteerRepositoryComponent implements OnInit {

  taskEntries = new Array<TaskEntry>();

  constructor(private taskService: TaskService,
              private employeeService: EmployeeService,
              private repositoryService: RepositoryService) {
  }

  ngOnInit() {
    this.repositoryService.findAllTasks()
      .toPromise()
      .then((taskEntries: TaskEntry[]) => taskEntries.forEach(this.addTaskEntry.bind(this)));
  }

  private addTaskEntry(taskEntry: TaskEntry) {
    Promise.all(
      [
        this.taskService.findById(taskEntry.task.id).toPromise(),
        this.employeeService.findById(taskEntry.participant.id).toPromise()
      ]
    ).then((values: Object[]) => {
      taskEntry.task = <Task>values[0];
      taskEntry.participant = <Participant> values[1];
      this.taskEntries.push(taskEntry);
    }).catch(() => this.taskEntries.push(taskEntry));

  }


}

class TaskEntry {
  id: string;
  task: Task;
  participant: Participant;
  timestamp: Date;
}
