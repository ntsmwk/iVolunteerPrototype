import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


import { isNullOrUndefined } from 'util';
import { LoginService } from '../../../_service/login.service';
import { Participant, ParticipantRole } from '../../../_model/participant';
import { MessageService } from '../../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { SourceRuleEntry, MappingOperatorType } from 'app/main/content/_model/derivation-rule';

@Component({
  selector: 'rule-precondition',
  templateUrl: './rule-configurator-precondition.component.html',
  styleUrls: ['../rule-configurator.component.scss']
})
export class FuseRulePreconditionConfiguratorComponent implements OnInit {

  @Input('sourceRuleEntry') sourceRuleEntry: SourceRuleEntry;

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;

  operations: any;



  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private formBuilder: FormBuilder,
    private messageService: MessageService) {
    this.rulePreconditionForm = formBuilder.group({
      'classDefinition': new FormControl(undefined),
      'propertyDefinition': new FormControl(undefined),
      'mappingOperatorType': new FormControl(undefined),
      'value': new FormControl(undefined),
    });

  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      classDefinition: this.sourceRuleEntry.classDefinition,
      propertyDefinition: this.sourceRuleEntry.propertyDefinition,
      mappingOperatorType: this.sourceRuleEntry.mappingOperatorType,
      value: this.sourceRuleEntry.value
    });
    this.operations = Object.keys(MappingOperatorType);

    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    ]).then(() => {
      // this.route.params.subscribe(params => this.loadTask(params['marketplaceId'], params['taskId']));
    });
  }



  private retrieveValueOf(op) {
    console.log(op);
    var x: MappingOperatorType = MappingOperatorType[op as keyof typeof MappingOperatorType];
    console.log(x);
    return x;
  }

}
