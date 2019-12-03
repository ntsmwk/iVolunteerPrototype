import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Task } from '../_model/task';
import { TaskService } from '../_service/task.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Marketplace } from '../_model/marketplace';

import { WorkflowStep } from '../_model/workflow-step';
import { WorkflowService } from '../_service/workflow.service';
import { isNullOrUndefined } from 'util';
import { LoginService } from '../_service/login.service';
import { Participant, ParticipantRole } from '../_model/participant';
import { MessageService } from '../_service/message.service';

@Component({
  templateUrl: './rule-configurator.component.html',
  styleUrls: ['./rule-configurator.component.scss']
})
export class FuseRuleConfiguratorComponent implements OnInit {

  participant: Participant;
  marketplace: Marketplace;
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
      // this.route.params.subscribe(params => this.loadTask(params['marketplaceId'], params['taskId']));
    });
  }

  
}
