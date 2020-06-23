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
import { Helpseeker } from "../../../../../_model/helpseeker";

@Component({
  selector: 'target-attribute-rule-configurator',
  templateUrl: './target-attribute-rule-configurator.component.html',
  styleUrls: ['./target-attribute-rule-configurator.component.scss']
})
export class TargetAttributeRuleConfiguratorComponent implements OnInit {
  @Input("attributeTarget")
  attributeTarget: AttributeCondition;
  @Output("attributeTarget")
  attributeTargetChange: EventEmitter<
    AttributeCondition
  > = new EventEmitter<AttributeCondition>();

  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleTargetAttributeForm: FormGroup;
  classProperties: ClassProperty<any>[] = [];

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
    this.ruleTargetAttributeForm = formBuilder.group({
      classPropertyId: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.ruleTargetAttributeForm.setValue({
      classPropertyId:
        (this.attributeTarget.classProperty
          ? this.attributeTarget.classProperty.id
          : "") || "",
      value: this.attributeTarget.value || "",
    });

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
            this.loadClassProperties(null);
            console.log(this.classProperties);
          });
      });
      console.log("class properties loaded " + this.classProperties);
  }

  onPropertyChange($event) {
    if (!this.attributeTarget.classProperty) {
      this.attributeTarget.classProperty = new ClassProperty();
    }
    this.attributeTarget.classProperty.id = $event.source.value;
    this.ruleTargetAttributeForm.value.classPropertyId = $event.source.value;
    this.onChange($event);
  }

  private loadClassProperties($event) {
    console.log("loading properties for " + this.attributeTarget.classDefinition.name);
    if (
      this.attributeTarget &&
      this.attributeTarget.classDefinition 
    ) {
      console.log(" ------ attributeTarget: " + this.attributeTarget.classDefinition);
      this.classPropertyService
        .getAllClassPropertiesFromClass(
          this.marketplace,
          this.attributeTarget.classDefinition.id,
          this.helpseeker.tenantId
        )
        .toPromise()
        .then((props: ClassProperty<any>[]) => {
          console.log(" loading ......" + props);
          this.classProperties = props;
          this.enumValues = [];
         // this.onChange($event);
        });
    }
  }

  findEnumValues() {
    if (
      this.attributeTarget.classProperty.type === "ENUM" &&
      this.enumValues.length == 0
    ) {
      this.classDefinitionService
        .getEnumValuesFromEnumHeadClassDefinition(
          this.marketplace,
          this.attributeTarget.classProperty.allowedValues[0]
            .enumClassId,
          this.helpseeker.tenantId
        )
        .toPromise()
        .then((list: any[]) => {
          this.enumValues = list.map((e) => e.value);
        });
    }
    return this.enumValues;
  }

  onChange($event) {
    console.log("change values in attribute, yay!");
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.attributeTargetChange.emit(this.attributeTarget);
      console.log(this.attributeTarget.value);
      console.log(this.attributeTarget.classProperty.name);
      console.log("test, value of target attribute --> " + this.attributeTarget.value);
    }
  }



  onChangeValue($event) {
    console.log("change values in attribute, yay!");
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.attributeTarget.value = this.ruleTargetAttributeForm.value.value;
      this.attributeTargetChange.emit(this.attributeTarget);
      console.log("test, value of target attribute --> " + this.attributeTarget.value);
    }
  }

}
