import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Marketplace } from '../_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from '../_service/login.service';
import { Participant, ParticipantRole } from '../_model/participant';
import { MessageService } from '../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { DerivationRule, MappingOperatorType, AttributeSourceRuleEntry, ClassSourceRuleEntry } from '../_model/derivation-rule';
import { DerivationRuleService } from '../_service/derivation-rule.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { ClassDefinitionService } from '../_service/meta/core/class/class-definition.service';
import { ClassDefinition } from '../_model/meta/class';
import { ClassProperty } from '../_model/meta/property';
import { FakeService } from '../_service/fake.service';

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

  fahrtenspangeImg = null;

  classDefinitions: ClassDefinition[] = [];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService,
    private fakeService: FakeService,
    private messageService: MessageService) {
    this.ruleForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'attributeSources': new FormControl(undefined),
      'classSources': new FormControl(undefined),
      'target': new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.route.params.subscribe(params => this.loadDerivationRule(marketplace, params['ruleId']));
        this.classDefinitionService.getAllClassDefinitionsWithoutHeadAndEnums(marketplace, this.participant.username === 'MVS' ? 'MV' : 'FF').toPromise().then(
          (definitions: ClassDefinition[]) => this.classDefinitions = definitions
        );
      });
    });
  }

  private isFF() {
    return this.participant.username == 'FFA';
  }

  private isMV() {
    return this.participant.username === 'MVS';
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }

  private loadDerivationRule(marketplace: Marketplace, ruleId: string) {
    if (ruleId) {
      this.derivationRuleService.findById(marketplace, ruleId).toPromise().then(
        (rule: DerivationRule) => {
          this.derivationRule = rule;
          this.ruleForm.setValue({
            id: this.derivationRule.id,
            name: this.derivationRule.name,
            attributeSources: this.derivationRule.attributeSourceRules,
            classSources: this.derivationRule.classSourceRules,
            target: this.derivationRule.target
          });
        }
      );
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.attributeSourceRules = [<AttributeSourceRuleEntry>{
        classDefinition: new ClassDefinition(),
        classProperty: new ClassProperty(),
        mappingOperatorType: MappingOperatorType.EQ,
        value: ""
      }];
      this.derivationRule.classSourceRules = [<ClassSourceRuleEntry>{
        classDefinition: new ClassDefinition(),
        mappingOperatorType: MappingOperatorType.EQ,
        value: ""
      }];
    }
  }

  save() {
    //  DO NOT DELETE!! ONLY FOR FAKE
    // this.derivationRule.name = this.ruleForm.value.name;
    // this.derivationRule.target = this.ruleForm.value.target;

    // this.derivationRuleService.save(this.marketplace, this.derivationRule).toPromise().then(() => this.loadDerivationRule(this.marketplace, this.derivationRule.id));

    this.fakeService.fahrtenspangeFake(this.marketplace).toPromise().then(() => { this.router.navigate(["/main/helpseeker/asset-inbox"]) });
  }

  navigateBack() {
    window.history.back();
  }

  addAttributeRule() {
    this.derivationRule.attributeSourceRules.push(new AttributeSourceRuleEntry());
  }

  addClassRule() {
    this.derivationRule.classSourceRules.push(new ClassSourceRuleEntry());
  }

  onFahrtenspangeBronzeChanged($event) {
    this.fahrtenspangeImg = 'bronze';
  }


  onFahrtenspangeSilberChanged($event) {
    this.fahrtenspangeImg = 'silber';
  }


  onFahrtenspangeGoldChanged($event) {
    this.fahrtenspangeImg = 'gold';
  }

  onFahrtenspangeNoneChanged($event) {
    this.fahrtenspangeImg = null;
  }
}