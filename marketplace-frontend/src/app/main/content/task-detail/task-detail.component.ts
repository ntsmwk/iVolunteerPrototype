import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Task} from '../_model/task';
import {TaskService} from '../_service/task.service';
import {MessageService} from '../_service/message.service';
import {CoreMarketplaceService} from '../_service/core-marketplace.service';
import {Marketplace} from '../_model/marketplace';
import { WorkflowStep } from '../_model/workflow-step';
import { WorkflowType } from '../_model/workflow-type';
import { WorkflowService } from '../_service/workflow.service';
import { isNullOrUndefined } from 'util';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';

@Component({
  selector: 'fuse-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class FuseTaskDetailComponent implements OnInit {

  task: Task;
  participant: Participant;
  marketplace: Marketplace;
  workflowStepsDefault: WorkflowStep[] = [];
  workflowStepsSpecial: WorkflowStep[] = [];
  workflowStepsAssignment: WorkflowStep[] ;
  workflowProcessId: string;

  constructor(private route: ActivatedRoute,
              private taskService: TaskService,
              private loginService: LoginService, 
              private marketplaceService: CoreMarketplaceService,
              private workflowService: WorkflowService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.error(params['shortName']);
      this.loadTask(params['shortName'], params['taskId']);});
  }

  private loadTask(marketplaceId: string, taskId: string) {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      console.error(this.participant);
      this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => {
          this.task = task;
          this.workflowService.getProcessId(marketplace, task.id).toPromise().then((workflowProcessId: string) => {
            this.workflowProcessId = workflowProcessId;
            console.error("workflowPID: " + workflowProcessId);
            if (isNullOrUndefined(workflowProcessId)) {
              return;
            }
            this.workflowService.getWorkflowSteps(marketplace, task.workflowKey, workflowProcessId, participant.id)
              .toPromise().then((workflowSteps: WorkflowStep[]) => {
                this.workflowStepsDefault = workflowSteps.filter((step:WorkflowStep) => step.workflowStepType === "DEFAULT");
                this.workflowStepsSpecial = workflowSteps.filter((step:WorkflowStep) => step.workflowStepType === "SPECIAL");
                console.error(this.workflowStepsDefault);
                this.handleSpecialWorkflowSteps();
              });
          });
        });
      });
    });
  }

  private handleSpecialWorkflowSteps(){
    this.workflowStepsAssignment = this.workflowStepsSpecial
    .filter((step:WorkflowStep) => step.label.toLowerCase() == "assign" || step.label.toLowerCase() == "unassign");
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep) {
    this.workflowService.completeWorkflowStep(this.marketplace, this.task.workflowKey, this.workflowProcessId, workflowStep, this.participant.id).toPromise().then(() => {
      this.loadTask(this.marketplace.id, this.task.id);
      //TODO broadcast for activity-timeline
    });
  }

}
