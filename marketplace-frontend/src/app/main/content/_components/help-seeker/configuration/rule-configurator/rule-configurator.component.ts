import { Component, OnInit } from "@angular/core";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ParticipantRole } from "app/main/content/_model/participant";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import {
  DerivationRule,
  GeneralCondition,
  ClassCondition,
  ComparisonOperatorType,
  AggregationOperatorType,
  AttributeCondition,
  ClassAction
} from "app/main/content/_model/derivation-rule";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ActivatedRoute, Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty, PropertyDefinition } from "app/main/content/_model/meta/property";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";

@Component({
  templateUrl: "./rule-configurator.component.html",
  styleUrls: ["./rule-configurator.component.scss"],
  providers: [],
})
export class FuseRuleConfiguratorComponent implements OnInit {
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;

  fahrtenspangeImg = null;

  classDefinitions: ClassDefinition[] = [];

  tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService,
    private tenantService: TenantService
  ) {
    this.ruleForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined),
      generalCondition: new FormControl(undefined),
      classCondition: new FormControl(undefined),
      target: new FormControl(undefined),
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
            name: this.derivationRule.name,
            generalCondition: this.derivationRule.generalConditions,
            classCondition: this.derivationRule.conditions,
            //target: this.derivationRule.target,
          });
        });
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.generalConditions = new Array();
      this.derivationRule.classActions = new Array();
      /**[
        <GeneralCondition>{
          propertyDefinition: new PropertyDefinition(),
          comparisonOperatorType: null, // ComparisonOperatorType.EQ,
          value: "",
        },
      ] */;
      this.derivationRule.conditions = new Array();
      /**[
        <ClassCondition>{
          classDefinition: new ClassDefinition(),
          attributes: [],
          aggregationOperatorType: null, /** AggregationOperatorType.COUNT, 
          value: "",
        },
      ]*/;
    }
  }

  save() {
    console.log("Form Submitted!");
    console.log("xxxx" + this.ruleForm.value.name + "yyyy");
    console.log(this.derivationRule.generalConditions);
    console.log(this.derivationRule.conditions);
    console.log(this.derivationRule.classActions);
    if (this.ruleForm.value.name){
      this.derivationRule.name = this.ruleForm.value.name;
     // this.derivationRule.target = this.ruleForm.value.target;
      this.derivationRule.tenantId = this.helpseeker.tenantId;
      this.derivationRule.container = "Test-Frontend-Claudia";

      console.log(this.derivationRule.tenantId);
    console.log(this.derivationRule.name);
    console.log(this.derivationRule.generalConditions);
    console.log(this.derivationRule.conditions);
    this.derivationRuleService
      .save(this.marketplace, this.derivationRule)
      .toPromise()
      .then(() =>
        this.loadDerivationRule(this.marketplace, this.derivationRule.id)
      );
    }
  }

  navigateBack() {
    window.history.back();
  }

  onTargetChange(cd, $event){
    if ($event.isUserInput) {
      console.log("FWPE gewechselt");
     // this.derivationRule.classAction = new ClassAction(cd);
    //  this.derivationRule.target = cd.name;
      console.log("class Action for " + this.derivationRule.classActions);
    }
  }

  /*
  onChange($event) {
    console.log("on change!!!!");
    if (this.classDefinitions.length > 0) {
      this.derivationRule.target = this.classDefinitions.find(
        (cd) => cd.id === this.ruleForm.value.target
      ).name;
    }
    console.log(this.derivationRule.target);
  }*/

  addNewTarget() {
    console.log("add new target");
    console.log(this.derivationRule.classActions);
    this.derivationRule.classActions.push(
      new ClassAction(null)
    );
    console.log(this.derivationRule.classActions);
  }
/**
  addTargetAttributeCondition(){
    this.derivationRule.classAction.attributeConditions.push(
      new AttributeCondition(this.derivationRule.classAction.classDefinition)
    );
  }*/

  addGeneralCondition() {
    //this.derivationRule.generalConditions = null;
      /**[
        <GeneralCondition>{
          propertyDefinition: new PropertyDefinition(),
          comparisonOperatorType: null, // ComparisonOperatorType.EQ,
          value: "",
        },
      ] */;
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
