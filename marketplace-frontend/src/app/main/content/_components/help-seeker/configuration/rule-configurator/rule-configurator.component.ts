import { Component, OnInit } from '@angular/core';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ParticipantRole } from 'app/main/content/_model/participant';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { DerivationRule, AttributeSourceRuleEntry, MappingOperatorType, ClassSourceRuleEntry } from 'app/main/content/_model/derivation-rule';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { DerivationRuleService } from 'app/main/content/_service/derivation-rule.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassProperty } from 'app/main/content/_model/meta/property';


@Component({
  templateUrl: './rule-configurator.component.html',
  styleUrls: ['./rule-configurator.component.scss'],
  providers: []
})
export class FuseRuleConfiguratorComponent implements OnInit {
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;

  fahrtenspangeImg = null;

  classDefinitions: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService
  ) {
    this.ruleForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined),
      attributeSources: new FormControl(undefined),
      classSources: new FormControl(undefined),
      target: new FormControl(undefined)
    });
  }

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: Helpseeker) => {
        this.helpseeker = helpseeker;

        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;

            this.route.params.subscribe(params =>
              this.loadDerivationRule(marketplace, params['ruleId'])
            );
            this.classDefinitionService
              .getAllClassDefinitionsWithoutHeadAndEnums(
                marketplace,
                this.helpseeker.tenantId
              )
              .toPromise()
              .then(
                (definitions: ClassDefinition[]) =>
                  (this.classDefinitions = definitions)
              );
          });
      });
  }

  private loadDerivationRule(marketplace: Marketplace, ruleId: string) {
    if (ruleId) {
      this.derivationRuleService
        .findByIdAndTenantId(marketplace, ruleId, this.helpseeker.tenantId)
        .toPromise()
        .then((rule: DerivationRule) => {
          this.derivationRule = rule;
          this.ruleForm.setValue({
            id: this.derivationRule.id,
            name: this.derivationRule.name,
            attributeSources: this.derivationRule.attributeSourceRules,
            classSources: this.derivationRule.classSourceRules,
            target: this.derivationRule.target
          });
        });
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.attributeSourceRules = [
        <AttributeSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          classProperty: new ClassProperty(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: ''
        }
      ];
      this.derivationRule.classSourceRules = [
        <ClassSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: ''
        }
      ];
    }
  }

  save() {
    this.derivationRule.name = this.ruleForm.value.name;
    this.derivationRule.target = this.ruleForm.value.target;
    this.derivationRule.tenantId = this.helpseeker.tenantId;
    this.derivationRuleService
      .save(this.marketplace, this.derivationRule)
      .toPromise()
      .then(() =>
        this.loadDerivationRule(this.marketplace, this.derivationRule.id)
      );
  }

  navigateBack() {
    window.history.back();
  }

  addAttributeRule() {
    this.derivationRule.attributeSourceRules.push(
      new AttributeSourceRuleEntry()
    );
  }

  addClassRule() {
    this.derivationRule.classSourceRules.push(new ClassSourceRuleEntry());
  }
}
