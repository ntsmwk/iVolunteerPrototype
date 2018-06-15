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
import { Volunteer } from '../../_model/volunteer';

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
              private volunteerService: VolunteerService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    this.dataSource.data = [];
    console.error(this.workflowStepsAssignment);
    this.workflowStepsAssignment
      .forEach((step:WorkflowStep) => {
        this.volunteerService.findById(step.assignee).toPromise().then((volunteer:Volunteer) => {
          this.dataSource.data.push(new AssignmentVolunteer(volunteer.username, this.isAssigned(step) , step));
          console.error(this.dataSource.data);
        })
      });
  }

  private isAssigned(workflowStep: WorkflowStep){
    return workflowStep.label.toLowerCase() == "unassign";
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep) {
    console.error(workflowStep);
    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, workflowStep, this.participant.id)
    .toPromise();
  }

  handleChange(volunteer: AssignmentVolunteer) {
    console.error(volunteer);
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