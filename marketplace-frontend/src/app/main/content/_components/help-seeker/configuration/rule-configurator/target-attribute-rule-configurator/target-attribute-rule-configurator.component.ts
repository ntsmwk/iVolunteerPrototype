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
  AbstractControl,
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
import { isNullOrUndefined } from 'util';
import { DynamicFormItemService } from 'app/main/content/_service/dynamic-form-item.service';
import { DynamicFormItemControlService } from 'app/main/content/_service/dynamic-form-item-control.service';
import { Tenant } from "app/main/content/_model/tenant";
import { DynamicFormItemBase } from 'app/main/content/_model/dynamic-forms/item';

@Component({
  selector: "target-attribute-rule-configurator",
  templateUrl: "./target-attribute-rule-configurator.component.html",
  styleUrls: ["./target-attribute-rule-configurator.component.scss"],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  providers: [DynamicFormItemService, DynamicFormItemControlService]
})
export class TargetAttributeRuleConfiguratorComponent implements OnInit {
  @Input("attributeTarget")
  attributeTarget: AttributeCondition;
  @Output("attributeTargetChange")
  attributeTargetChange: EventEmitter<AttributeCondition> = new EventEmitter<
    AttributeCondition
  >();

  tenantAdmin: User;
  marketplace: Marketplace;
  role: UserRole;
  tenants: Tenant[];
  ruleTargetAttributeForm: FormGroup;
  ruleQuestionForm: FormControl;
  formItems: DynamicFormItemBase<any>[] = [];
  formItem: DynamicFormItemBase<any>;
  classProperties: ClassProperty<any>[] = [];

  attributeForms: FormArray;

  propertyDefinition: PropertyDefinition<any>;

  classDefinitionCache: ClassDefinition[] = [];

  targetValidationMessages = DerivationRuleValidators.ruleValidationMessages;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private dynamicFormItemService: DynamicFormItemService,
    private dynamicFormItemControlService: DynamicFormItemControlService,
    private parent: FormGroupDirective
  ) { }

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
    this.tenantAdmin = globalInfo.user;
    this.tenants = globalInfo.tenants;

    this.loadClassProperties(null);
    if (!isNullOrUndefined(this.attributeTarget.classProperty)) {
      this.addQuestionAndFormGroup(this.attributeTarget.classProperty);
    }
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
        });
    }
  }

  private addQuestionAndFormGroup(classProperty: ClassProperty<any>) {
    console.log(" .... add question and form group .... ");
    let myArr: ClassProperty<any>[] = new Array();
    myArr.push(classProperty);
    this.formItems = this.dynamicFormItemService.getFormItemsFromProperties(myArr);

    // AK war vorher hier ---> 143
    // this.formItem = this.formItems[0];


    // set question value in case of existing rule
    if (this.attributeTarget.value) {
      this.formItem.value = this.attributeTarget.value;
    }

    // AK QuestionForm zeicgt jetzt auf dieselbe control wie vorher - muss für 1:N Beziehungen möglichwerweise angepasst werden
    this.ruleQuestionForm = (this.dynamicFormItemControlService.toFormGroup(this.formItems).controls['entries'] as FormArray).controls[0] as FormControl;

    this.ruleTargetAttributeForm.addControl('questionForm', (this.ruleQuestionForm));
    console.log(this.ruleTargetAttributeForm);
    console.log(this.ruleQuestionForm);

    this.formItem = this.formItems[0];

    // console.log("DISPLAYING FORMGROUP: ");
    // console.log("RAW: ");
    // console.log(this.ruleQuestionForm);

    // console.log("CONTROLS: ");
    // console.log(this.ruleQuestionForm.controls);

    // console.log("VALUES");
    // console.log(this.ruleQuestionForm.value);
    // console.log(this.ruleTargetAttributeForm);
    // const formArray = this.ruleQuestionForm.controls['entries'] as FormArray;
    // console.log(formArray.controls[0].get(this.formItem.key));

    // detect change in question form
    // AK hier hab ich auch was verändert - weiß aber nicht mehr was...
    this.ruleTargetAttributeForm.get('questionForm').valueChanges.subscribe((change) => {
      this.ruleTargetAttributeForm.patchValue({
        value: this.ruleQuestionForm.get(this.formItem.key).value
      });
      // value: this.ruleTargetAttributeForm.get('questionForm').get(this.formItem.key).value
      // });

      this.attributeTarget.value = this.ruleTargetAttributeForm.get('questionForm').get(this.formItem.key).value;
      //this.attributeTarget.value = formArray.controls[0].get(this.formItem.key).value;
    });
  }

  private initAttributeTarget() {
    this.attributeTarget.classProperty = new ClassProperty();
    this.attributeTarget.value = "";
    this.ruleTargetAttributeForm.reset();
  }

  onPropertyChange(classProperty: ClassProperty<any>, $event) {
    if ($event.isUserInput &&
      (!this.attributeTarget.classProperty ||
        this.attributeTarget.classProperty.id != classProperty.id)
    ) {
      this.initAttributeTarget();
      this.attributeTarget.classProperty = classProperty;
      console.log(" property changed!!!!!");
      // create new form for value
      this.addQuestionAndFormGroup(classProperty);

      this.attributeTargetChange.emit(this.attributeTarget);
    }
  }


  onChange($event) {
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.addQuestionAndFormGroup(this.attributeTarget.classProperty);
      this.attributeTargetChange.emit(this.attributeTarget);
    }
  }

  onChangeValue($event) {
    if (this.classProperties.length > 0) {
      this.attributeTarget.classProperty =
        this.classProperties.find(
          (cp) => cp.id === this.ruleTargetAttributeForm.value.classPropertyId
        ) || new ClassProperty();
      this.addQuestionAndFormGroup(this.attributeTarget.classProperty);
      this.attributeTarget.value = this.ruleTargetAttributeForm.value.value;
      this.attributeTargetChange.emit(this.attributeTarget);
    }
  }
}
