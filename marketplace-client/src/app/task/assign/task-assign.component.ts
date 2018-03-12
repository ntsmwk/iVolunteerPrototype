import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskInteractionService} from '../../task-interaction/task-interaction.service';
import {Task} from '../task';
import {Participant} from '../../participant/participant';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../task.service';
import {AssignmentVolunteer} from '../../participant/assignmentVolunteer';
import {ParticipantService} from '../../participant/participant.service';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class TaskAssignComponent implements OnInit {

  reservedVolunteers: Participant[];
  assignedVolunteers: Participant[];
  dataSource = new MatTableDataSource<AssignmentVolunteer>();
  displayedColumns = ['isAssigned', 'username'];
  task: Task;

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService,
              private participantService: ParticipantService) {
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
              this.setDataSource();
            });
        });
      });
  }


  handleChange(volunteer: AssignmentVolunteer, x) {
    this.participantService.findVolunteerById(volunteer.id).toPromise().then((participant: Participant) => {
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

  setDataSource() {
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
