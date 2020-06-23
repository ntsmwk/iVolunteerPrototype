import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";
import {
  Participant,
  ParticipantRole,
} from "../../../../../_model/participant";
import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  ComparisonOperatorType,
  GeneralCondition
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import {
  ClassProperty,
  PropertyDefinition,
} from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { PropertyDefinitionService } from "../../../../../_service/meta/core/property/property-definition.service";
import { Helpseeker } from "../../../../../_model/helpseeker";
import { ThrowStmt } from '@angular/compiler';


var output = console.log;

@Component({
  selector: "general-precondition",
  templateUrl: "./general-precondition-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class GeneralPreconditionConfiguratorComponent
  implements OnInit {
  @Input("generalCondition")
  generalCondition: GeneralCondition;
  @Output("GeneralCondition")
  generalConditionChange: EventEmitter<
    GeneralCondition
  > = new EventEmitter<GeneralCondition>();

  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;
  comparisonOperators: any;
  generalAttributes: PropertyDefinition<any>[];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private propertyDefinitionService: PropertyDefinitionService,
    private helpSeekerService: CoreHelpSeekerService,
    private derivationRuleService: DerivationRuleService,
  ) {
    this.rulePreconditionForm = formBuilder.group({
      propertyDefinitionId: new FormControl(undefined),
      comparisonOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      propertyDefinitionId:
        (this.generalCondition.propertyDefinition
          ? this.generalCondition.propertyDefinition.id
          : "") || "",
      comparisonOperatorType: this.generalCondition.comparisonOperatorType ||
       ComparisonOperatorType.GE,
      value: this.generalCondition.value || "",
    });

    this.comparisonOperators = Object.keys(ComparisonOperatorType);

    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: Helpseeker) => {
        this.helpseeker = helpseeker;
        this.helpSeekerService
          .findRegisteredMarketplaces(this.helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;
            this.derivationRuleService
              .getGeneralProperties(marketplace,
                  this.helpseeker.tenantId)
              .toPromise()
              .then((genProperties: PropertyDefinition<any>[]) => {
                this.generalAttributes = genProperties;
              });
          });
      });
  }

  onPropertyChange($event) {
    if (!this.generalCondition.propertyDefinition) {
      this.generalCondition.propertyDefinition = new PropertyDefinition();
    }
    this.generalCondition.propertyDefinition.id = $event.source.value;
    this.rulePreconditionForm.value.propertyDefinitionId = $event.source.value;
    this.onChange($event);
  }

  onOperatorChange(comparisonOperatorType, $event){
    console.log("on operator change begin ....");
    if ($event.isUserInput) {    // ignore on deselection of the previous option
      console.log("Selection changed to " + comparisonOperatorType);
      console.log("operator changed to " + comparisonOperatorType);
      this.generalCondition.comparisonOperatorType = comparisonOperatorType;
      console.log("op neu: " + this.generalCondition.comparisonOperatorType);
    }
    console.log("on operator change end ....");
    
  }

  onChange($event) {
    console.log("on change - before -->" + this.generalCondition.comparisonOperatorType);
    if (this.generalCondition) {
      this.generalCondition.propertyDefinition = this.generalAttributes.find(
        (pd) => pd.id === this.rulePreconditionForm.value.propertyDefinitionId
      );
      this.generalCondition.comparisonOperatorType = this.rulePreconditionForm.value.comparisonOperatorType;
      this.generalCondition.value = this.rulePreconditionForm.value.value;
      this.generalConditionChange.emit(this.generalCondition);
    }
    console.log("after --> " + this.generalCondition.comparisonOperatorType);
  }

  private retrieveComparisonOperatorValueOf(op) {
    let x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }
}
