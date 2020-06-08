import { Component, OnInit } from "@angular/core";
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ParticipantRole } from "app/main/content/_model/participant";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import {
  DerivationRule,
  AttributeSourceRuleEntry,
  MappingOperatorType,
  ClassSourceRuleEntry,
} from "app/main/content/_model/derivation-rule";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ActivatedRoute, Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
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
      attributeSources: new FormControl(undefined),
      classSources: new FormControl(undefined),
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
            attributeSources: this.derivationRule.attributeSourceRules,
            classSources: this.derivationRule.classSourceRules,
            target: this.derivationRule.target,
          });
        });
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.attributeSourceRules = [
        <AttributeSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          classProperty: new ClassProperty(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: "",
        },
      ];
      this.derivationRule.classSourceRules = [
        <ClassSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: "",
        },
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
