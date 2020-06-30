import { Component, OnInit } from "@angular/core";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import {
  DerivationRule,
  GeneralCondition,
  ClassCondition,
  ClassAction
} from "app/main/content/_model/derivation-rule";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ActivatedRoute, Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { RuleExecution } from 'app/main/content/_model/derivation-rule-execution';

@Component({
  templateUrl: "./rule-configurator.component.html",
  styleUrls: ["./rule-configurator.component.scss"],
  providers: [],
})
export class FuseRuleConfiguratorComponent implements OnInit {
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;

  testConditions = false;
  ruleExecutions: RuleExecution[];

  classDefinitions: ClassDefinition[] = [];

  tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService,
    private tenantService: TenantService
  ) {
    this.ruleForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined)
    });
  }

  async ngOnInit() {
    this.helpseeker = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.marketplace = <Marketplace>(
      await this.helpSeekerService
        .findRegisteredMarketplaces(this.helpseeker.id)
        .toPromise()
    );

    this.route.params.subscribe((params) =>
      this.loadDerivationRule(this.marketplace, params["ruleId"])
    );
    this.classDefinitions = <ClassDefinition[]>(
      await this.classDefinitionService
        .getAllClassDefinitionsWithoutHeadAndEnums(
          this.marketplace,
          this.helpseeker.tenantId
        )
        .toPromise()
    );

    this.tenant = <Tenant>(
      await this.tenantService.findById(this.helpseeker.tenantId).toPromise()
    );
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
            name: this.derivationRule.name
          });
        });
    } else { // init derivation rule
      this.derivationRule = new DerivationRule();
      this.derivationRule.generalConditions = new Array();
      this.derivationRule.classActions = new Array();
      this.derivationRule.classActions.push(
        new ClassAction(null)
      );
      this.derivationRule.conditions = new Array();
    }
  }

  test(){
    this.testConditions = true;
    this.derivationRule.name = this.ruleForm.value.name;
    this.derivationRule.tenantId = this.helpseeker.tenantId;
    this.derivationRule.container = "simulate execution " + this.derivationRule.name;
  }

  save() {
    if (this.ruleForm.value.name &&
      this.derivationRule.classActions[0].classDefinition){
      this.derivationRule.name = this.ruleForm.value.name;
      this.derivationRule.tenantId = this.helpseeker.tenantId;
      this.derivationRule.container = "Test-Frontend";

      this.derivationRuleService
        .save(this.marketplace, this.derivationRule)
        .toPromise()
        .then(() =>
          this.loadDerivationRule(this.marketplace, this.derivationRule.id)
        );
    }
    this.ruleForm.reset;
    this.ruleForm.value.name = "";
    this.testConditions = false;
  }

  navigateBack() {
    window.history.back();
  }

  onChange($event){
   this.testConditions = false; 
  }

  onChangeName() {
      this.derivationRule.name = this.ruleForm.value.name;
  }

  addGeneralCondition() {
    this.derivationRule.generalConditions.push(
      new GeneralCondition()
    );
  }

  addClassCondition() {
    this.derivationRule.conditions.push(
      new ClassCondition()
      );
  }
}
