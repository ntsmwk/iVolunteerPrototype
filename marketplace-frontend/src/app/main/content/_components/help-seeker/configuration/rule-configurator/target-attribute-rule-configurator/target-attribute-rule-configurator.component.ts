import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { LoginService } from "../../../../../_service/login.service";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  ControlContainer,
  FormGroupDirective,
  Validators,
  FormArray,
} from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { AttributeCondition } from "app/main/content/_model/derivation-rule";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import {
  ClassProperty,
  PropertyDefinition,
} from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { User, UserRole } from "app/main/content/_model/user";
import { DerivationRuleValidators } from "app/main/content/_validator/derivation-rule.validators";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "target-attribute-rule-configurator",
  templateUrl: "./target-attribute-rule-configurator.component.html",
  styleUrls: ["./target-attribute-rule-configurator.component.scss"],
  viewProviders: [
    { provide: ControlContainer, useExisting: FormGroupDirective },
  ],
})
export class TargetAttributeRuleConfiguratorComponent implements OnInit {
  // @Input("parentFormArray") parentFormArray: FormArray;
  @Input("attributeTarget")
  attributeTarget: AttributeCondition;
  @Output("attributeTargetChange")
  attributeTargetChange: EventEmitter<AttributeCondition> = new EventEmitter<
    AttributeCondition
  >();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  tenants: Tenant[];
  ruleTargetAttributeForm: FormGroup;
  classProperties: ClassProperty<any>[] = [];

  attributeForms: FormArray;

  enumValues = [];

  propertyDefinition: PropertyDefinition<any>;

  classDefinitionCache: ClassDefinition[] = [];

  targetValidationMessages = DerivationRuleValidators.ruleValidationMessages;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private parent: FormGroupDirective
  ) {}

  async ngOnInit() {
    this.attributeForms = <FormArray>(
      this.parent.form.controls["targetAttributes"]
    );

    this.ruleTargetAttributeForm = this.formBuilder.group({
      classPropertyId: new FormControl(undefined, [Validators.required]),
      value: new FormControl(undefined, [Validators.required]),
    });

    this.attributeForms.push(this.ruleTargetAttributeForm);

    this.ruleTargetAttributeForm.setValue({
      classPropertyId:
        (this.attributeTarget.classProperty
          ? this.attributeTarget.classProperty.id
          : "") || "",
      value: this.attributeTarget.value || "",
    });

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;
    this.tenants = globalInfo.tenants;

    this.loadClassProperties(null);
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
    if (this.attributeTarget && this.attributeTarget.classDefinition) {
      this.classPropertyService
        .getAllClassPropertiesFromClass(
          this.marketplace,
          this.attributeTarget.classDefinition.id
        )
        .toPromise()
        .then((props: ClassProperty<any>[]) => {
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
          this.attributeTarget.classProperty.allowedValues[0].enumClassId,
          this.tenants[0].id
        )
        .toPromise()
        .then((list: any[]) => {
          this.enumValues = list.map((e) => e.value);
        });
    }
    return this.enumValues;
  }

  onChange($event) {
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.attributeTargetChange.emit(this.attributeTarget);
    }
  }

  onChangeValue($event) {
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.attributeTarget.value = this.ruleTargetAttributeForm.value.value;
      this.attributeTargetChange.emit(this.attributeTarget);
    }
  }
}
