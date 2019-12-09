import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


import { isNullOrUndefined } from 'util';
import { LoginService } from '../../../_service/login.service';
import { Participant, ParticipantRole } from '../../../_model/participant';
import { MessageService } from '../../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';

@Component({
  selector: 'rule-precondition',
  templateUrl: './rule-configurator-precondition.component.html',
  styleUrls: ['../rule-configurator.component.scss']
})
export class FuseRulePreconditionConfiguratorComponent implements OnInit {

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;


  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private formBuilder: FormBuilder,
    private messageService: MessageService) {
      this.rulePreconditionForm = formBuilder.group({
        'id': new FormControl(undefined),
        'name': new FormControl(undefined),
        'attribute': new FormControl(undefined),
        'operation': new FormControl(undefined),
        'value': new FormControl(undefined),
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
