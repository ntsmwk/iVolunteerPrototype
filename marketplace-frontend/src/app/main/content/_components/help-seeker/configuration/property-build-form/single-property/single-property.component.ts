import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { isNullOrUndefined, isNull } from 'util';
import { PropertyType, Rule, RuleKind, PropertyDefinition } from '../../../../../_model/meta/Property';
import { Marketplace } from '../../../../../_model/marketplace';
import { propertyNameUniqueValidator } from '../../../../../_validator/property-name-unique.validator';
import { listNotEmptyValidator } from "../../../../../_validator/list-not-empty.validator";
import { PropertyDefinitionService } from '../../../../../_service/meta/core/property/property-definition.service';
import { PropertyConstraint } from '../../../../../_model/meta/Constraint';

export class PropertyKindOption {
  kind: string;
  label: string;
  display: boolean;
}

export class RuleKindOption {
  kind: string;
  label: string;
  display: boolean;

  hasValueField: boolean;
  hasDataField: boolean;
}

@Component({
  selector: 'app-single-property',
  templateUrl: './single-property.component.html',
  styleUrls: ['./single-property.component.scss']
})
export class SinglePropertyComponent implements OnInit {

  // @Input() form: FormGroup;
  @Input() propertyListItems: PropertyDefinition<any>[];
  @Input() marketplace: Marketplace;
  @Input() currentProperty: PropertyDefinition<any>;

  //Form Layout Controls
  isLoaded: boolean; isDropdown: boolean; hasRules: boolean;

  ruleEditActive: boolean; isNewRule: boolean; submitPressed: boolean;

  propertyKindOptions: PropertyKindOption[] = [];
  ruleKindOptions: RuleKindOption[] = [];

  propertyLegalValues: any[] = [];

  //SingleProperty
  form: FormGroup;

  rules: FormArray; legalValues: FormArray;

  oldRule: {
    kind: string;
    value: number;
    data: string;
    message: string;
  };

  constructor(private formBuilder: FormBuilder,
    private propertyDefinitionService: PropertyDefinitionService) { }

  ngOnInit() {

    console.log("init")
    this.preparePropertyKindOptions();
    this.prepareRuleKindOptions();

    this.clearForm();

    if (!isNullOrUndefined(this.currentProperty)) {
      console.log("prefiling form");
      this.prefillForm();
    } else {
      console.log("empty form");
      this.isDropdown = false;
      this.hasRules = false;
    }

    this.ruleEditActive = false;
    this.isNewRule = false;
    this.submitPressed = false;
    this.isLoaded = true;

  }

  //----------------------------------------------------

  private preparePropertyKindOptions() {
    for (let kind in PropertyType) {

      if (kind != PropertyType.GRAPH && kind != PropertyType.MAP && kind != PropertyType.MULTI && kind != PropertyType.COMPETENCE) {
        this.propertyKindOptions.push({ kind: kind, label: kind, display: true });
      }
    }
  }

  private prepareRuleKindOptions() {
    for (let kind in RuleKind) {

      switch (kind) {
        case RuleKind.REQUIRED: case RuleKind.REQUIRED_TRUE: {
          this.ruleKindOptions.push({ kind: kind, label: kind, display: true, hasDataField: false, hasValueField: false });
          break;
        }
        case RuleKind.MAX: case RuleKind.MAX_LENGTH: case RuleKind.MIN: case RuleKind.MIN_LENGTH: {
          this.ruleKindOptions.push({ kind: kind, label: kind, display: true, hasDataField: false, hasValueField: true })
          break;
        }
        case RuleKind.REGEX_PATTERN: {
          this.ruleKindOptions.push({ kind: kind, label: kind, display: true, hasDataField: true, hasValueField: false })
        }
      }
    }
  }

  clearForm() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required, propertyNameUniqueValidator(this.propertyListItems, this.currentProperty)]),
      kind: this.formBuilder.control('', Validators.required),
      defaultValues: this.formBuilder.control(''),
      legalValues: this.formBuilder.array([])
    });
  }

  prefillForm() {
    this.form.get('name').setValue(this.currentProperty.name);
    this.form.get('kind').setValue(this.currentProperty.type);

    //TODO remove
    this.form.get('defaultValues').setValue("");


    if (!isNullOrUndefined(this.currentProperty.allowedValues) && this.currentProperty.allowedValues.length > 0) {
      this.isDropdown = true;
      this.clearLegalValues();
      let formAllowedValues: FormArray = this.form.get('legalValues') as FormArray;

      for (let value of this.currentProperty.allowedValues) {
        formAllowedValues.push(this.createLegalValue());
        let pushed = formAllowedValues.at(formAllowedValues.length - 1);

        pushed.get('value').setValue(value);
      }
    } else {
      this.isDropdown = false;
    }

    if (!isNullOrUndefined(this.currentProperty.propertyConstraints) && this.currentProperty.propertyConstraints.length > 0) {
      // this.hasRules = true;
      // this.clearRules();
      // let constraints = this.form.get('rules') as FormArray;

      // for (let constraint of this.currentProperty.propertyConstraints) {
      //   constraints.push(this.createRule());
      //   let pushed = constraints.at(constraints.length - 1);

      //   pushed.get('kind').setValue(constraint.constraintType);
      //   pushed.get('message').setValue(constraint.message);

      //   this.ruleKindUpdatedEvent(constraint.constraintType, pushed as FormGroup, null);

      //   let control = pushed.get('value');
      //   if (!isNullOrUndefined(control)) {
      //     control.setValue(constraint.value);
      //   }

      //   control = pushed.get('data');
      //   if (!isNullOrUndefined(control)) {
      //     control.setValue(constraint.value);
      //   }

      //   this.setDisabledRule(pushed as FormGroup);
      // }
    } else {
      this.hasRules = false;
    }

    console.log(this.form.controls);
    console.log(this.currentProperty);

  }

  //----------------------------------------------------
  //-----------------Legal Values-----------------------
  //----------------------------------------------------

  createLegalValue(): FormGroup {
    return this.formBuilder.group({
      value: [undefined, Validators.required]
    });
  }

  addLegalValue() {
    console.log("adding legal value");
    // this.legalValues = this.legalValuesForm.get('legalValues') as FormArray;
    this.legalValues = this.form.get('legalValues') as FormArray;

    this.legalValues.push(this.createLegalValue());
  }

  markLegalValuesAsTouched() {
    // let legalValues = (this.form.get('legalValues') as FormGroup).controls
    if (!isNullOrUndefined(this.form.get('legalValues'))) {

      Object.keys((this.form.get('legalValues') as FormArray).controls).forEach(key => {
        (this.form.get('legalValues').get(key) as FormGroup).controls.value.markAsTouched();
      });
    }
  }

  removeLegalValue(i: number) {
    this.legalValues.removeAt(i);
  }

  clearLegalAndDefaultValues() {
    console.log("clearing");
    this.clearDefaultValues();
    this.clearLegalValues();
  }

  clearDefaultValues() {
    this.form.get('defaultValues').reset();
  }

  clearLegalValues() {

    if (!isNullOrUndefined(this.form.get('legalValues')) && !this.isDropdown) {
      console.log("removing");
      this.form.removeControl('legalValues');
    } else {
      console.log("adding");
      this.form.addControl('legalValues', this.formBuilder.array([], listNotEmptyValidator()));
    }

    if (this.form.get('kind').value == 'LIST') { this.isDropdown = false }


    console.log("cleared result")
    console.log(this.form);
  }


  //----------------------------------------------------
  //-----------------------Rules------------------------
  //----------------------------------------------------

  //----Only Rules which are fitting for the Type should be displayed
  prepareValidRules(event: any, source: string) {

    this.hasRules = false;
    this.clearRules();

    console.log("prepare Rules " + event);
    console.log(event);

    if (source == 'dropdown-select') {


      if (this.isDropdown) {
        for (let option of this.ruleKindOptions) {
          if (option.kind == RuleKind.REQUIRED) {
            option.display = true;
          } else {
            option.display = false;
          }
        }
      } else {
        this.prepareValidRules({ value: this.form.get('kind').value }, 'type-select');
      }
    }

    else if (source == 'type-select') {
      switch (event.value) {

        //Texts: no ranges, no required_true reserved for boolean
        // allowed are amount ranges, required and regex
        case PropertyType.TEXT: case PropertyType.LONG_TEXT: {
          console.log("text")
          for (let option of this.ruleKindOptions) {
            if (option.kind == RuleKind.MIN_LENGTH || option.kind == RuleKind.MAX_LENGTH || option.kind == RuleKind.REQUIRED || option.kind == RuleKind.REGEX_PATTERN) {
              option.display = true;
              console.log(option.kind + ": true")
            } else {
              option.display = false;
              console.log(option.kind + ": false")

            }
          }
          break;

        }

        //Boolean: only required_true
        case PropertyType.BOOL: {
          console.log("bool")
          for (let option of this.ruleKindOptions) {
            if (option.kind == RuleKind.REQUIRED_TRUE) {
              option.display = true;
              console.log(option.kind + ": true")

            } else {
              option.display = false;
              console.log(option.kind + ": false")

            }
          }

          break;
        }

        //Date: no max/min amount of characters, no required true, no regex
        case PropertyType.DATE: {
          for (let option of this.ruleKindOptions) {
            if (option.kind == RuleKind.REQUIRED || option.kind == RuleKind.MIN || option.kind == RuleKind.MIN) {
              option.display = true;
            } else {
              option.display = false;
            }
          }

          break;
        }

        //Numbers ranges and amounts allowed, required allowed, regex maybe allowed, no required_true, 
        case PropertyType.FLOAT_NUMBER: case PropertyType.WHOLE_NUMBER: {
          for (let option of this.ruleKindOptions) {
            if (option.kind == RuleKind.REQUIRED || option.kind == RuleKind.MAX || option.kind == RuleKind.MIN || option.kind == RuleKind.MAX_LENGTH || option.kind == RuleKind.MIN_LENGTH || option.kind == RuleKind.REGEX_PATTERN) {
              option.display = true;
            } else {
              option.display = false;
            }
          }

          break;
        }

        case PropertyType.LIST: {
          for (let option of this.ruleKindOptions) {
            if (option.kind == RuleKind.REQUIRED) {
              option.display = true;
            } else {
              option.display = false;
            }
          }
        }

      }

    } else {
      console.error('should not happen source must either be "type-select" or "dropdown-select"')
    }


  }

  //----Rule Form manipulation

  createRule(): FormGroup {
    return this.formBuilder.group({
      kind: [undefined, Validators.required],
      message: undefined
    });
  }

  addRule(): void {
    this.rules = this.form.get('rules') as FormArray
    this.rules.push(this.createRule());

    this.ruleEditActive = true;
    this.isNewRule = true;
    // this.rules.disable();
  }

  clearRules() {
    if (!this.hasRules) {
      this.form.removeControl('rules');
    } else {
      this.form.addControl('rules', this.formBuilder.array([], listNotEmptyValidator()));
    }

    this.ruleEditActive = false;
  }

  setEnabledRule(rule: FormControl) {
    this.ruleEditActive = true;
    rule.enable();

    this.oldRule = {
      kind: rule.get('kind').value,
      value: isNullOrUndefined(rule.get('value')) ? undefined : rule.get('value').value,
      data: isNullOrUndefined(rule.get('data')) ? undefined : rule.get('data').value,
      message: rule.get('message').value
    }
  }

  setDisabledRule(rule: FormGroup) {
    if (rule.valid) {
      this.ruleEditActive = false;
      rule.disable();
      this.oldRule = undefined;
      this.isNewRule = false;

    } else {
      rule.markAsTouched();
      Object.keys(rule.controls).forEach(key => {
        rule.get(key).markAsTouched();
      });
    }
  }

  removeRule(i: number) {
    let rules = this.form.get('rules') as FormArray;
    rules.removeAt(i);

    if (rules.length <= 0) {
      this.form.removeControl('rules');
      this.form.addControl('rules', this.formBuilder.array([], listNotEmptyValidator()));
    }
  }

  revertRule(i: number) {
    let rule = this.form.get('rules').get("" + i) as FormGroup;

    if (this.isNewRule) {
      this.removeRule(i);
      this.isNewRule = false;
      this.ruleEditActive = false;

    } else {
      rule.get('kind').setValue(this.oldRule.kind);
      rule.get('message').setValue(this.oldRule.message);

      rule.removeControl('value');
      rule.removeControl('data');

      if (!isNullOrUndefined(this.oldRule.value)) {
        rule.addControl('value', new FormControl(this.oldRule.value, Validators.required));
      }
      if (!isNullOrUndefined(this.oldRule.data)) {
        rule.addControl('data', new FormControl(this.oldRule.data, Validators.required));
      }
    }
    this.oldRule = undefined;
    this.setDisabledRule(rule);
  }

  ruleKindUpdatedEvent(kind: string, rule: FormGroup, option: RuleKindOption) {
    if (!this.hasDataField(kind)) {
      rule.removeControl('data');
    } else {
      rule.addControl('data', new FormControl(undefined, Validators.required));
    }

    if (!this.hasValueField(kind)) {
      rule.removeControl('value');
    } else {
      rule.addControl('value', new FormControl(undefined, Validators.required));
    }

  }

  //--Validity Checks and Queries

  isFieldInvalid(value: FormControl) {
    if (isNullOrUndefined(value)) {
      return false;
    }
    return value.invalid;
  }

  isRuleKindDisabled(kind: string): boolean {
    let array = this.form.get('rules') as FormArray;

    for (let i = 0; i < array.length; i++) {
      if (this.form.get('rules').get("" + i).get('kind').value == kind) {
        return true;
      }
    }

    return false;

  }

  hasValueField(kind: string): boolean {
    if (isNullOrUndefined(kind)) {
      return false;
    }

    return this.ruleKindOptions.find((option: RuleKindOption) => {
      return option.kind == kind
    }).hasValueField;
  }

  hasDataField(kind: string): boolean {
    if (isNullOrUndefined(kind)) {
      return false;
    }

    return this.ruleKindOptions.find((option: RuleKindOption) => {
      return option.kind == kind;
    }).hasDataField;
  }

  //---

  trackByFn(index: any, item: any) {
    return index;
  }


  onSubmit(valid: boolean, f: any) {
    this.submitPressed = true;

    console.log(this.form);

    if (valid && !this.ruleEditActive) {

      let property = this.createPropertyFromForm();

      console.log(property);

      console.log("call propertyService...");

      // TODO call service to send to server (and save in db)
      this.propertyDefinitionService.createNewPropertyDefinition(this.marketplace, [property]).toPromise().then(() => {
        console.log("PropertyService called, property added");
        console.log(property);
        this.navigateBack();
      });

      console.log("VALID")
      let ret = JSON.stringify(property, null, 2);
      console.log(ret);
    } else {
      console.log("INVALID");
      console.log(JSON.stringify(this.createPropertyFromForm(), null, 2));

      console.log("Valid: " + valid);
      console.log("RuleEdit: " + this.ruleEditActive);

      this.markLegalValuesAsTouched();
    }
  }

  createPropertyFromForm(): PropertyDefinition<any> {
    let property: PropertyDefinition<any> = new PropertyDefinition<any>();

    if (isNullOrUndefined(this.currentProperty)) {
      property.id = null;
    } else {
      property.id = this.currentProperty.id;
    }

    property.name = this.form.get('name').value;
    

    // property.defaultValues = [];
    // console.log(this.form);
    // if (!isNullOrUndefined(this.form.get('defaultValues')) && !isNullOrUndefined(this.form.get('defaultValues').value)) {

    //   let arr = this.form.get('defaultValues');
    //   console.log(arr);

    //   property.defaultValues.push({ id: null, value: arr.value });

    // }


    property.allowedValues = [];
    if (!isNullOrUndefined(this.form.get('legalValues'))) {
      for (let value of (this.form.get('legalValues') as FormArray).value) {
        property.allowedValues.push(value.value);
      }
    }

    property.propertyConstraints = [];
    if (!isNullOrUndefined(this.form.get('rules'))) {
      for (let value of (this.form.get('rules') as FormArray).controls) {

        let constraint = new PropertyConstraint();
        constraint.id = null;
        constraint.constraintType = value.get('kind').value;

        if (!isNullOrUndefined(value.get('value'))) {
          constraint.value = value.get('value').value;
        }

        if (!isNullOrUndefined(value.get('data'))) {
          constraint.value = value.get('data').value;
        }
        constraint.message = value.get('message').value;

        property.propertyConstraints.push(constraint);
      }
    }
    property.type = this.form.get('kind').value;

    return property;

  }

  navigateBack() {
    window.history.back();
  }



}
