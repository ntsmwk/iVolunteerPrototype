import { Component, OnInit, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Participant } from '../../../../_model/participant';
import { Task } from '../../../../_model/task';
import { VolunteerService } from '../../../../_service/volunteer.service';
import { WorkflowStep } from '../../../../_model/workflow-step';
import { WorkflowService } from '../../../../_service/workflow.service';
import { Volunteer } from '../../../../_model/volunteer';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from '../../../../_service/message.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Marketplace } from '../../../../_model/marketplace';

@Component({
  selector: 'app-task-assign',
  templateUrl: './task-assign.component.html',
  styleUrls: ['./task-assign.component.css']
})
export class FuseTaskAssignComponent implements OnInit {

  @Input() workflowStepsAssignment: WorkflowStep[];
  @Input() participant: Participant;
  @Input() task: Task;
  @Input() workflowProcessId: string;
  @Input() marketplace: Marketplace;

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

    this.workflowStepsAssignment.forEach((step: WorkflowStep) => {
      this.volunteerService.findById(this.marketplace, step.assignee).toPromise().then((volunteer: Volunteer) => {
        const assignedVolunteer = new AssignmentVolunteer(volunteer.username, step);
        if (this.isNextStepAssign(step)) {
          this.selection.deselect(assignedVolunteer)
        } else {
          this.selection.select(assignedVolunteer);
        }
        assignmentVolunteers.push(assignedVolunteer);
        this.dataSource.data = assignmentVolunteers;
      })
    });
  }

  private isNextStepAssign(workflowStep: WorkflowStep) {
    return workflowStep.label.toLowerCase() == "assign";
  }

  handleChange(volunteer: AssignmentVolunteer) {
    this.selection.toggle(volunteer);
    this.workflowService.completeWorkflowStep(this.marketplace, this.task.workflowKey, this.workflowProcessId, volunteer.workflowStep, this.participant.id)
      .toPromise()
      .then(() => this.messageService.broadcast('historyChanged', {}))
      .catch((error: HttpErrorResponse) => {
        if (error.status == 412) {
          alert("Volunteer is not reserved yet.");
        }
        this.selection.deselect(volunteer);
      });
  }
}

export class AssignmentVolunteer {
  ;
  username: string;
  workflowStep: WorkflowStep;

  constructor(username: string, workflowStep: WorkflowStep) {
    this.username = username;
    this.workflowStep = workflowStep;
  }
}