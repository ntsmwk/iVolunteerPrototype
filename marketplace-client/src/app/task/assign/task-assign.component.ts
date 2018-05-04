import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {Participant} from '../../_model/participant';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../../_service/task.service';
import {Task} from '../../_model/task';
import {VolunteerService} from '../../_service/volunteer.service';
import {SourceService} from '../../_service/source.service';
import {ContractorService} from '../../_service/contractor.service';
import {Source} from '../../_model/source';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class TaskAssignComponent implements OnInit {

  source: Source;
  task: Task;

  dataSource = new MatTableDataSource<AssignmentVolunteer>();
  displayedColumns = ['isAssigned', 'username'];

  constructor(private route: ActivatedRoute,
              private sourceService: SourceService,
              private contractorService: ContractorService,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService,
              private volunteerService: VolunteerService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadData(params['id']));
    this.sourceService.find().toPromise().then((source: Source) => this.source = source);
  }

  private loadData(id: string) {
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;
      Promise.all([
        this.taskInteractionService.findReservedVolunteersByTaskId(this.task).toPromise(),
        this.taskInteractionService.findAssignedVolunteersByTaskId(this.task).toPromise()
      ]).then((values: Array<any>) => {
        const assignmentVolunteers: AssignmentVolunteer[] = [];
        (<Participant[]> values[0]).forEach((volunteer: Participant) => {
          assignmentVolunteers.splice(0, 0, new AssignmentVolunteer(volunteer.id, volunteer.username, false));
        });
        (<Participant[]> values[1]).forEach((volunteer: Participant) => {
          assignmentVolunteers.splice(0, 0, new AssignmentVolunteer(volunteer.id, volunteer.username, true));
        });

        this.dataSource.data = assignmentVolunteers;
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
          this.contractorService.assign(this.source, this.task, participant).toPromise().then(() => this.loadData(this.task.id));
          break;
      }
    });
  }
}

export class AssignmentVolunteer {
  id: string;
  username: string;
  isAssigned: boolean;

  constructor(id: string, username: string, isAssigned: boolean) {
    this.id = id;
    this.username = username;
    this.isAssigned = isAssigned;
  }
}
