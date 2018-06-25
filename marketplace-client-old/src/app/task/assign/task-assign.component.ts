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
import { SelectionModel } from '@angular/cdk/collections';

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
  selection: SelectionModel<AssignmentVolunteer>;

  constructor(private volunteerService: VolunteerService,
              private messageService: MessageService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    const assignmentVolunteers: AssignmentVolunteer[] = [];
    this.selection = new SelectionModel<AssignmentVolunteer>(true, []);

    this.workflowStepsAssignment.forEach((step:WorkflowStep) => {
        this.volunteerService.findById(step.assignee).toPromise().then((volunteer:Volunteer) => {
          const assignedVolunteer = new AssignmentVolunteer(volunteer.username, step);
          if (this.isNextStepAssign(step)){
            this.selection.deselect(assignedVolunteer)
          }else{
            this.selection.select(assignedVolunteer);
          }
          assignmentVolunteers.push(assignedVolunteer);
          this.dataSource.data =  assignmentVolunteers;  
        })
      });
  }

  private isNextStepAssign(workflowStep: WorkflowStep){
    return workflowStep.label.toLowerCase() == "assign";
  }

  handleChange(volunteer: AssignmentVolunteer) {
    this.selection.toggle(volunteer);
    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, volunteer.workflowStep, this.participant.id)
    .toPromise()
    .then(() =>       this.messageService.broadcast('historyChanged', {}))
    .catch((error:HttpErrorResponse) => {
      if(error.status == 412){
        alert("precondition failed!");
      }
      this.selection.deselect(volunteer);
      console.error(this.selection.isSelected(volunteer));
    });
  }
}

export class AssignmentVolunteer {;
  username: string;
  workflowStep: WorkflowStep;

  constructor(username: string, workflowStep: WorkflowStep) {
    this.username = username;
    this.workflowStep = workflowStep;
  }
}