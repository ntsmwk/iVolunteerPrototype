import {Component, OnInit, Input} from '@angular/core';
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
import { WorkflowStep } from '../../_model/workflow-step';
import { WorkflowService } from '../../_service/workflow.service';
import { LoginService } from '../../_service/login.service';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class TaskAssignComponent implements OnInit {

  @Input() workflowStepsAssignment: WorkflowStep[];
  @Input() participant: Participant;
  @Input() task: Task;
  @Input() workflowProcessId: string;

  source: Source;
  dataSource = new MatTableDataSource<AssignmentVolunteer>();
  displayedColumns = ['isAssigned', 'username'];

  constructor(private route: ActivatedRoute,
              private sourceService: SourceService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadData());
    this.sourceService.find().toPromise().then((source: Source) => this.source = source);
  }

  private loadData() {

    const assignmentVolunteers: AssignmentVolunteer[] = [];

    this.workflowStepsAssignment
      .filter((step:WorkflowStep) => step.label.toLowerCase() == "assign")
      .forEach((step:WorkflowStep) => assignmentVolunteers.push(new AssignmentVolunteer(step.assignee, true, step)));

    this.workflowStepsAssignment
      .filter((step:WorkflowStep) => step.label.toLowerCase() == "unassign")
      .forEach((step:WorkflowStep) => assignmentVolunteers.push(new AssignmentVolunteer(step.assignee, false, step)));

    console.log(assignmentVolunteers);
    this.dataSource.data = assignmentVolunteers;
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep) {
    console.log(workflowStep);
    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, workflowStep, this.participant.username)
    .toPromise();
  }

  handleChange(volunteer: AssignmentVolunteer) {
    console.log(volunteer);
    this.executeNextWorkflowStep(volunteer.workflowStep);
  }
}

export class AssignmentVolunteer {;
  username: string;
  isAssigned: boolean;
  workflowStep: WorkflowStep;

  constructor(username: string, isAssigned: boolean, workflowStep: WorkflowStep) {
    this.username = username;
    this.isAssigned = isAssigned;
    this.workflowStep = workflowStep;
  }
}