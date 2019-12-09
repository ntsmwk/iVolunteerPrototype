import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { Marketplace } from '../../_model/marketplace';

import { isNullOrUndefined } from 'util';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { MessageService } from '../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

@Component({
  templateUrl: './rule-configurator.component.html',
  styleUrls: ['./rule-configurator.component.scss']
})
export class FuseRuleConfiguratorComponent implements OnInit {

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;


  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private formBuilder: FormBuilder,
    private messageService: MessageService) {
      this.ruleForm = formBuilder.group({
        'id': new FormControl(undefined),
        'name': new FormControl(undefined),
        'source': new FormControl(undefined),
        'target': new FormControl(undefined),
      });
  
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
