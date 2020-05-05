import { Component, OnInit } from '@angular/core';
import { Task } from 'app/main/content/_model/task';
import { Participant, ParticipantRole } from 'app/main/content/_model/participant';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { WorkflowStep } from 'app/main/content/_model/workflow-step';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from 'app/main/content/_service/task.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { WorkflowService } from 'app/main/content/_service/workflow.service';
import { MessageService } from 'app/main/content/_service/message.service';
import { isNullOrUndefined } from 'util';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class FuseTaskDetailComponent implements OnInit {

  task: Task;
  participant: Participant;
  marketplace: Marketplace;
  workflowStepsDefault: WorkflowStep[] = [];
  workflowStepsSpecial: WorkflowStep[] = [];
  workflowStepsAssignment: WorkflowStep[] = [];
  workflowProcessId: string;
  role: ParticipantRole;

  constructor(private route: ActivatedRoute,
    private taskService: TaskService,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private workflowService: WorkflowService,
    private messageService: MessageService) {
  }

  ngOnInit() {

    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    ]).then(() => {
      this.route.params.subscribe(params => this.loadTask(params['marketplaceId'], params['taskId']));
    });
  }

  private loadTask(marketplaceId: string, taskId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => {
        this.task = task;
        this.workflowService.getProcessId(marketplace, task.id).toPromise().then((workflowProcessId: string) => {
          this.workflowProcessId = workflowProcessId;
          if (isNullOrUndefined(workflowProcessId)) {
            this.workflowStepsDefault = [];
            this.workflowStepsSpecial = [];
            return;
          }
          this.workflowService.getWorkflowSteps(marketplace, task.workflowKey, workflowProcessId, this.participant.id)
            .toPromise().then((workflowSteps: WorkflowStep[]) => {
              this.workflowStepsDefault = workflowSteps.filter((step: WorkflowStep) => step.workflowStepType === 'DEFAULT');
              this.workflowStepsSpecial = workflowSteps.filter((step: WorkflowStep) => step.workflowStepType === 'SPECIAL');
              this.handleSpecialWorkflowSteps();
            });
        });
      });
    });
  }

  private handleSpecialWorkflowSteps() {
    this.workflowStepsAssignment = this.workflowStepsSpecial
      .filter((step: WorkflowStep) => step.label.toLowerCase() === 'assign' || step.label.toLowerCase() === 'unassign');
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep) {
    this.workflowService.completeWorkflowStep(this.marketplace, this.task.workflowKey, this.workflowProcessId, workflowStep, this.participant.id).toPromise().then(() => {
      this.loadTask(this.marketplace.id, this.task.id);
      this.messageService.broadcast('taskHistoryChanged', {});
    });
  }

  showEdit() {
    return !isNullOrUndefined(this.role) && this.role === 'HELP_SEEKER' && !isNullOrUndefined(this.task);
  }

  backClicked() {
    window.history.back();
  }
}
