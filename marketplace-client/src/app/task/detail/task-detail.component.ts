import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';

import {LoginService} from '../../_service/login.service';
import {TaskInteractionService} from '../../_service/task-interaction.service';
import {MessageService} from '../../_service/message.service';
import {Participant} from '../../_model/participant';
import {SourceService} from '../../_service/source.service';
import {ContractorService} from '../../_service/contractor.service';
import {Source} from '../../_model/source';
import { WorkflowStep } from '../../_model/workflow-step';
import { WorkflowService } from '../../_service/workflow.service';

@Component({
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {

  source: Source;
  task: Task;
  participant: Participant;
  workflowSteps: WorkflowStep[];
  workflowProcessId: string;
  role;
  isAlreadyReserved = false;
  isAlreadyAssigned = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private taskService: TaskService,
              private taskInteractionService: TaskInteractionService,
              private workflowService: WorkflowService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadTask(params['id']));
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.role = role);
  }

  private loadTask(id: string) {
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.task = task;
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant).then(() => {
        this.taskInteractionService.getLatestTaskOperation(this.task, (this.participant)).toPromise().then((taskOperation) => {
          if (taskOperation === 'ASSIGNED') {
            this.isAlreadyReserved = false;
            this.isAlreadyAssigned = true;
          } else if (taskOperation === 'RESERVED' || taskOperation === 'UNASSIGNED') {
            this.isAlreadyReserved = true;
            this.isAlreadyAssigned = false;
          } else {
            this.isAlreadyReserved = false;
            this.isAlreadyAssigned = false;
          }
        });
      });
      this.workflowService.getProcessId(id).toPromise().then((processId: string) => {
        this.workflowProcessId = processId;
        this.workflowService.getWorkflowSteps(task.workflowKey, processId).toPromise().then((nextWorkflowSteps: WorkflowStep[]) => {
          console.log(nextWorkflowSteps);
          this.workflowSteps = nextWorkflowSteps;
        })
      });
    });
  }

  executeNextWorkflowStep(workflowStep: WorkflowStep){
    this.workflowService.completeWorkflowStep(this.task.workflowKey, this.workflowProcessId, workflowStep).toPromise().then(
      () => {
        this.loadTask(this.task.id);
        this.messageService.broadcast('historyChanged', {});
      });
  }
}
