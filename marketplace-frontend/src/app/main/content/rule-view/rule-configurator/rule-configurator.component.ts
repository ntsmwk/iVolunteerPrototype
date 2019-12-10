import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { Marketplace } from '../../_model/marketplace';

import { isNullOrUndefined } from 'util';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { MessageService } from '../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { FuseRulePreconditionConfiguratorComponent } from './rule-configurator-precondition/rule-configurator-precondition.component';
import { DerivationRule, SourceRuleEntry } from '../../_model/derivation-rule';
import { DerivationRuleService } from '../../_service/derivation-rule.service';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';

@Component({
  templateUrl: './rule-configurator.component.html',
  styleUrls: ['./rule-configurator.component.scss'],
  providers: [],
})
export class FuseRuleConfiguratorComponent implements OnInit {


  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;


  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private messageService: MessageService) {
    this.ruleForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'sources': new FormControl(undefined),
      'targets': new FormControl(undefined),
    });
  }

  ngOnInit() {

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.route.params.subscribe(params => this.loadDerivationRule(marketplace, params['ruleId']));
      });
    });
  }

  private loadDerivationRule(marketplace: Marketplace, ruleId: string) {
    console.log(ruleId);
    if (ruleId) {
      this.derivationRuleService.findById(marketplace, ruleId).toPromise().then(
        (rule: DerivationRule) => {
          this.derivationRule = rule;
          this.ruleForm.setValue({
            id: this.derivationRule.id,
            name: this.derivationRule.name,
            sources: this.derivationRule.sources,
            targets: this.derivationRule.targets
          });

        }
      );
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.sources = [];
      this.derivationRule.targets = [];
    }
  }


}
