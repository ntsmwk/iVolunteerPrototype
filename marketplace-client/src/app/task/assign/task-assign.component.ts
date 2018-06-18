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
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from '../../_service/message.service';
import { Alert } from 'selenium-webdriver';

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
              private messageService: MessageService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    const assignmentVolunteers: AssignmentVolunteer[] = [];
    
    console.error(this.workflowStepsAssignment);
    this.workflowStepsAssignment
      .forEach((step:WorkflowStep) => {
        this.volunteerService.findById(step.assignee).toPromise().then((volunteer:Volunteer) => {
          assignmentVolunteers.push(new AssignmentVolunteer(volunteer.username, this.isAssigned(step), step));
          // assigning the dataSource earlier or later does not display the list correctly!
          // therefore it is assigned here...
          this.dataSource.data =  assignmentVolunteers;  
        })
      });
  }

  private isAssigned(workflowStep: WorkflowStep){
    return workflowStep.label.toLowerCase() == "unassign";
  }

  isVolunteerAssigned(assignedVolunteer: AssignmentVolunteer){
    return assignedVolunteer.isAssigned;
  }

  handleChange(volunteer: AssignmentVolunteer) {

    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, volunteer.workflowStep, this.participant.id)
    .toPromise()
    .then(() =>       this.messageService.broadcast('historyChanged', {}))
    .catch((error:HttpErrorResponse) => {
      if(error.status == 412){
        alert("precondition failed!");
      }
      this.dataSource.data
        .filter((assignmentVolunteer: AssignmentVolunteer) => assignmentVolunteer.username == volunteer.username)
        .forEach((assignmentVolunteer: AssignmentVolunteer) => {assignmentVolunteer.isAssigned = !assignmentVolunteer.isAssigned; console.error("changed!!")});
    });
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