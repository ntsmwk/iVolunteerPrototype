import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";
import { User, UserRole } from "../../../../../_model/user";
import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  ComparisonOperatorType,
  ClassCondition,
  AggregationOperatorType,
  AttributeCondition,
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import {
  ClassProperty,
  PropertyDefinition,
} from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { PropertyDefinitionService } from "../../../../../_service/meta/core/property/property-definition.service";

@Component({
  selector: "attribute-rule-precondition",
  templateUrl: "./attribute-rule-configurator-precondition.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class FuseAttributeRulePreconditionConfiguratorComponent
  implements OnInit {
  @Input("attributeCondition")
  attributeCondition: AttributeCondition;
  @Output("attributeConditionChange")
  attributeConditionChange: EventEmitter<AttributeCondition> = new EventEmitter<
    AttributeCondition
  >();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  rulePreconditionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  comparisonOperators: any;

  enumValues = [];

  propertyDefinition: PropertyDefinition<any>;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private propertyDefinitionService: PropertyDefinitionService,
    private helpSeekerService: CoreHelpSeekerService
  ) {
    this.rulePreconditionForm = formBuilder.group({
      classPropertyId: new FormControl(undefined),
      comparisonOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      classPropertyId:
        (this.attributeCondition.classProperty
          ? this.attributeCondition.classProperty.id
          : "") || "",
      comparisonOperatorType:
        this.attributeCondition.comparisonOperatorType ||
        ComparisonOperatorType.EQ,
      value: this.attributeCondition.value || "",
    });

    this.comparisonOperators = Object.keys(ComparisonOperatorType);

    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: User) => {
        this.helpseeker = helpseeker;
        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;
            this.classDefinitionService
              .getAllClassDefinitionsWithoutHeadAndEnums(
                marketplace,
                this.helpseeker.subscribedTenants.find(
                  (t) => t.role === UserRole.HELP_SEEKER
                ).tenant.id
              )
              .toPromise()
              .then((definitions: ClassDefinition[]) => {
                this.classDefinitions = definitions;
                this.loadClassProperties(null);
              });
          });
      });
  }

  onPropertyChange(classProperty: ClassProperty<any>, $event) {
    if (
      $event.isUserInput &&
      (!this.attributeCondition.classProperty ||
        this.attributeCondition.classProperty.id != classProperty.id)
    ) {
      this.initAttributeCondition();
      this.attributeCondition.classProperty = classProperty;
      this.attributeConditionChange.emit(this.attributeCondition);
    }
    // this.attributeCondition.classProperty.id = $event.source.value;
    // this.rulePreconditionForm.value.classPropertyId = $event.source.value;
  }

  private initAttributeCondition() {
    this.attributeCondition.classProperty = new ClassProperty();
    this.attributeCondition.comparisonOperatorType = ComparisonOperatorType.EQ;
    this.attributeCondition.value = undefined;
    this.rulePreconditionForm.reset();
  }

  private loadClassProperties($event) {
    if (this.attributeCondition && this.attributeCondition.classDefinition) {
      this.classPropertyService
        .getAllClassPropertiesFromClass(
          this.marketplace,
          this.attributeCondition.classDefinition.id
        )
        .toPromise()
        .then((props: ClassProperty<any>[]) => {
          this.classProperties = props;
          this.enumValues = [];
          this.onChange($event);
        });
    }
  }

  findEnumValues() {
    if (
      this.attributeCondition.classProperty.type === "ENUM" &&
      this.enumValues.length == 0
    ) {
      this.classDefinitionService
        .getEnumValuesFromEnumHeadClassDefinition(
          this.marketplace,
          this.attributeCondition.classProperty.allowedValues[0].enumClassId,
          this.helpseeker.subscribedTenants.find(
            (t) => t.role === UserRole.HELP_SEEKER
          ).tenant.id
        )
        .toPromise()
        .then((list: any[]) => {
          this.enumValues = list.map((e) => e.value);
        });
    }
    return this.enumValues;
  }

  onOperatorChange(op, $event) {
    console.log("on operator change begin ....");
    if ($event.isUserInput) {
      // ignore on deselection of the previous option
      console.log("Selection changed to " + op);
      console.log("operator changed to " + op);
      this.attributeCondition.comparisonOperatorType = op;
      console.log("op neu: " + this.attributeCondition.comparisonOperatorType);
    }
    console.log("on operator change end ....");
  }

  onChange($event) {
    console.log("change values in attribute, yay!");
    if (this.classProperties.length > 0) {
      this.attributeCondition.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.rulePreconditionForm.value.classPropertyId
        ) || new ClassProperty();
      this.attributeCondition.comparisonOperatorType = this.rulePreconditionForm.value.comparisonOperatorType;
      this.attributeCondition.value = this.rulePreconditionForm.value.value;
      this.attributeConditionChange.emit(this.attributeCondition);
      console.log(this.attributeCondition.value);
      console.log(this.attributeCondition.classProperty.name);
      console.log(this.attributeCondition.comparisonOperatorType);
    }
  }

  private retrieveComparisonOperatorValueOf(op) {
    let x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }
}
