import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';

import {LoginService} from '../../_service/login.service';
import {MessageService} from '../../_service/message.service';
import {Participant} from '../../_model/participant';
import {WorkflowStep} from '../../_model/workflow-step';
import {WorkflowService} from '../../_service/workflow.service';
import {isNullOrUndefined} from 'util';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {

  role;
  task: Task;
  participant: Participant;
  workflowSteps: WorkflowStep[];
  workflowProcessId: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private taskService: TaskService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['id']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadTask(id: string) {
    this.taskService.getTree(id).toPromise().then();

    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant).then(() => {
        this.workflowService.getProcessId(id).toPromise().then((workflowProcessId: string) => {
          this.workflowProcessId = workflowProcessId;
          if (isNullOrUndefined(workflowProcessId)) {
            return;
          }
          this.workflowService.getWorkflowSteps(task.workflowKey, workflowProcessId, this.participant.username).toPromise().then((workflowSteps: WorkflowStep[]) => {
            this.workflowSteps = workflowSteps;
          });
        });
      });
    });
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep) {
    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, workflowStep, this.participant.username).toPromise().then(() => {
      this.loadTask(this.task.id);
      this.messageService.broadcast('historyChanged', {});
    });
  }
}
