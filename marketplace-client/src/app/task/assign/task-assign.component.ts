import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {Task} from '../../_model/task';
import {Participant} from '../../_model/participant';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../../_service/task.service';
import {VolunteerService} from '../../_service/volunteer.service';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class TaskAssignComponent implements OnInit {

  task: Task;
  reservedVolunteers: Participant[];
  assignedVolunteers: Participant[];

  dataSource = new MatTableDataSource<AssignmentVolunteer>();
  displayedColumns = ['isAssigned', 'username'];

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService,
              private volunteerService: VolunteerService) {
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
        this.taskInteractionService.findReservedVolunteersByTaskId(this.task).toPromise().then((resVolunteers: Participant[]) => {
          this.reservedVolunteers = resVolunteers;
          this.taskInteractionService.findAssignedVolunteersByTaskId(this.task).toPromise().then(
            (volunteers: Participant[]) => {
              this.assignedVolunteers = volunteers;
              this.updateDataSource();
            });
        });
      });
  }

  handleChange(volunteer: AssignmentVolunteer) {
    this.volunteerService.findById(volunteer.id).toPromise().then((participant: Participant) => {
      switch (volunteer.isAssigned) {
        case true:
          this.taskInteractionService.unassign(this.task, participant).toPromise().then(() => this.loadData(this.task.id));
          break;
        case false:
          this.taskInteractionService.assign(this.task, participant).toPromise().then(() => this.loadData(this.task.id));
          break;
      }
    });
  }

  updateDataSource() {
    const assignmentVolunteers: AssignmentVolunteer[] = [];
    this.reservedVolunteers.forEach((volunteer: Participant) => {
      assignmentVolunteers.splice(0, 0, new AssignmentVolunteer(volunteer.id, volunteer.username, false));
    });
    this.assignedVolunteers.forEach((volunteer: Participant) => {
      assignmentVolunteers.splice(0, 0, new AssignmentVolunteer(volunteer.id, volunteer.username, true));
    });
    this.dataSource.data = assignmentVolunteers;
  }
}

export class AssignmentVolunteer {
  id: string
  username: string;
  isAssigned: boolean;

  constructor(id: string, username: string, isAssigned: boolean) {
    this.id = id;
    this.username = username;
    this.isAssigned = isAssigned;
  }
}
