import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";

import { LoginService } from "../../../../../_service/login.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  ComparisonOperatorType,
  GeneralCondition,
} from "app/main/content/_model/derivation-rule";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import {
  PropertyDefinition,
} from "app/main/content/_model/meta/property";

import { User, UserRole } from "../../../../../_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

var output = console.log;

@Component({
  selector: "general-precondition",
  templateUrl: "./general-precondition-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class GeneralPreconditionConfiguratorComponent implements OnInit {
  @Input("generalCondition")
  generalCondition: GeneralCondition;
  @Output("generalConditionChange")
  generalConditionChange: EventEmitter<GeneralCondition> = new EventEmitter<
    GeneralCondition
  >();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  rulePreconditionForm: FormGroup;
  comparisonOperators: any;
  generalAttributes: PropertyDefinition<any>[];

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
  ) {
    this.rulePreconditionForm = formBuilder.group({
      propertyDefinitionId: new FormControl(undefined),
      comparisonOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  async ngOnInit() {
    this.rulePreconditionForm.setValue({
      propertyDefinitionId:
        (this.generalCondition.propertyDefinition
          ? this.generalCondition.propertyDefinition.id
          : "") || "",
      comparisonOperatorType:
        this.generalCondition.comparisonOperatorType ||
        ComparisonOperatorType.GE,
      value: this.generalCondition.value || "",
    });

    this.comparisonOperators = Object.keys(ComparisonOperatorType);

    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;

    this.derivationRuleService
      .getGeneralProperties(
        this.marketplace,
        this.helpseeker.subscribedTenants.find(
          (t) => t.role === UserRole.HELP_SEEKER
        ).tenantId
      )
      .toPromise()
      .then((genProperties: PropertyDefinition<any>[]) => {
        this.generalAttributes = genProperties;
      });
  }

  onPropertyChange($event) {
    if (!this.generalCondition.propertyDefinition) {
      this.generalCondition.propertyDefinition = new PropertyDefinition();
    }
    this.generalCondition.propertyDefinition.id = $event.source.value;
    this.rulePreconditionForm.value.propertyDefinitionId = $event.source.value;
    this.generalConditionChange.emit();
  }

  onOperatorChange(comparisonOperatorType, $event) {
    if ($event.isUserInput) {
      // ignore on deselection of the previous option
      this.generalCondition.comparisonOperatorType = comparisonOperatorType;
    }
    this.generalConditionChange.emit(comparisonOperatorType);
  }

  onChange($event) {
    if (this.generalCondition) {
      this.generalCondition.propertyDefinition = this.generalAttributes.find(
        (pd) => pd.id === this.rulePreconditionForm.value.propertyDefinitionId
      );
      this.generalCondition.comparisonOperatorType = this.rulePreconditionForm.value.comparisonOperatorType;
      this.generalCondition.value = this.rulePreconditionForm.value.value;
      this.generalConditionChange.emit(this.generalCondition);
    }
  }

  private retrieveComparisonOperatorValueOf(op) {
    let x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }
}
