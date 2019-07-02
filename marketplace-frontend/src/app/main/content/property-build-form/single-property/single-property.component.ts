import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { PropertyKind, Rule, RuleKind, PropertyListItem, Property } from '../../_model/properties/Property';
import { PropertyService } from '../../_service/property.service';
import { Marketplace } from '../../_model/marketplace';
import { propertyNameUniqueValidator } from '../../_validator/property-name-unique.validator';
import { listNotEmptyValidator } from "../../_validator/list-not-empty.validator";

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

  @Input() propertyListItems: PropertyListItem[];
  @Input() marketplace: Marketplace;
  @Input() currentProperty: Property<any>;

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
    private propertyService: PropertyService) { }

  ngOnInit() {
    this.preparePropertyKindOptions();
    this.prepareRuleKindOptions();

    this.clearForm();

    if (!isNullOrUndefined(this.currentProperty)) {
      this.prefillForm();   
    } else {
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
    for (let kind in PropertyKind) {
      if (kind == PropertyKind.GRAPH || kind == PropertyKind.MULTIPLE || kind == PropertyKind.MAP) {
        // this.propertyKindOptions.push({kind: kind, label: kind, display: false});
      } else {
        this.propertyKindOptions.push({kind: kind, label: kind, display: true});
      }
    }
  }

  private prepareRuleKindOptions() {
    for (let kind in RuleKind) {
      switch(kind) {
        case RuleKind.REQUIRED: case RuleKind.REQUIRED_TRUE: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, hasDataField: false, hasValueField: false});
          break;
        }
        case RuleKind.MAX: case RuleKind.MAX_LENGTH: case RuleKind.MIN: case RuleKind.MIN_LENGTH: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, hasDataField: false, hasValueField: true})
          break;
        }
        case RuleKind.REGEX_PATTERN: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, hasDataField: true, hasValueField: false})
        }
      } 
    }
  }

  clearForm() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required, propertyNameUniqueValidator(this.propertyListItems, this.currentProperty)]),
      kind: this.formBuilder.control('', Validators.required),
      defaultValue: this.formBuilder.control('')
    });
  }

  prefillForm() {
    this.form.get('name').setValue(this.currentProperty.name);
    this.form.get('kind').setValue(this.currentProperty.kind);

    if (!isNullOrUndefined(this.currentProperty.defaultValues)) {
      this.form.get('defaultValue').setValue(this.currentProperty.defaultValues[0].value);
    }
    
    if (!isNullOrUndefined(this.currentProperty.legalValues) && this.currentProperty.legalValues.length > 0) {
      this.isDropdown = true;
      this.clearLegalValues();
      let legalValues = this.form.get('legalValues') as FormArray;

      for (let value of this.currentProperty.legalValues) {
        legalValues.push(this.createLegalValue());
        let pushed = legalValues.at(legalValues.length-1);

        pushed.get('value').setValue(value.value);
      }
    } else {
      this.isDropdown = false;
    }

    if (!isNullOrUndefined(this.currentProperty.rules) && this.currentProperty.rules.length > 0) {
      this.hasRules = true;
      this.clearRules();
      let rules = this.form.get('rules') as FormArray;

      for (let rule of this.currentProperty.rules) {
        rules.push(this.createRule());
        let pushed = rules.at(rules.length-1);

        pushed.get('kind').setValue(rule.kind);
        pushed.get('message').setValue(rule.message);

        this.ruleKindUpdatedEvent(rule.kind, pushed as FormGroup, null);

        let control = pushed.get('value');
        if (!isNullOrUndefined(control)) {
          control.setValue(rule.value);
        }

        control = pushed.get('data');
        if (!isNullOrUndefined(control)) {
          control.setValue(rule.data);
        }

        this.setDisabledRule(pushed as FormGroup);
      }
    } else {
      this.hasRules = false;
    }

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
    this.legalValues = this.form.get('legalValues') as FormArray;
    this.legalValues.push(this.createLegalValue());
  }

  markLegalValuesAsTouched() {
    if (!isNullOrUndefined(this.form.get('legalValues'))) {
      Object.keys((this.form.get('legalValues') as FormArray).controls).forEach( key => {
        (this.form.get('legalValues').get(key) as FormGroup).controls.value.markAsTouched();
      });
    }
  }

  removeLegalValue(i: number) {
    this.legalValues.removeAt(i);
  }

  clearLegalAndDefaultValues() {
    this.clearDefaultValues();
    this.clearLegalValues();  
  }

  clearDefaultValues() {
    this.form.get('defaultValue').reset();
  }

  clearLegalValues() {
    if (!isNullOrUndefined(this.form.get('legalValues')) && !this.isDropdown) {
      this.form.removeControl('legalValues');
    } else {
      this.form.addControl('legalValues', this.formBuilder.array([], listNotEmptyValidator()));
    }

    if (this.form.get('kind').value == 'LIST') {this.isDropdown = false}
  }

  //----------------------------------------------------
  //-----------------------Rules------------------------
  //----------------------------------------------------

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
      value: isNullOrUndefined(rule.get('value')) ? undefined: rule.get('value').value,
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
    let rule = this.form.get('rules').get(""+i) as FormGroup;

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
      rule.addControl('data',new FormControl(undefined, Validators.required));
    }

    if (!this.hasValueField(kind)) {
      rule.removeControl('value');
    } else {
      rule.addControl('value',new FormControl(undefined, Validators.required));
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
    
    for (let i=0; i < array.length; i++) {
      if (this.form.get('rules').get(""+i).get('kind').value == kind) {
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

    if (valid && !this.ruleEditActive) {

      let property = this.createPropertyFromForm();

      // call service to send to server (and save in db)
      this.propertyService.addSingleProperty(this.marketplace, property).toPromise().then(() => {
        this.navigateBack();
      });

    } else {
      this.markLegalValuesAsTouched();
    }
  }

  createPropertyFromForm(): Property<any> {
    let property: Property<any> = new Property<any>();

    if (isNullOrUndefined(this.currentProperty)) {
      property.id = null;
    } else {
      property.id = this.currentProperty.id;
    }

    property.name = this.form.get('name').value;
  
    property.defaultValues = [];
    property.defaultValues.push({id: null, value: this.form.get('defaultValue').value});
   
    property.legalValues = [];
   
    if (!isNullOrUndefined(this.form.get('legalValues'))) {
      for (let value of (this.form.get('legalValues') as FormArray).value) {
        property.legalValues.push({id: null, value: value.value});
      }
    }

    property.rules = [];
    if (!isNullOrUndefined(this.form.get('rules'))) {
      for (let value of (this.form.get('rules') as FormArray).controls) {

        let rule = new Rule();
        rule.id = null;
        rule.kind = value.get('kind').value;  
        
        if (!isNullOrUndefined(value.get('value'))) {
          rule.value = value.get('value').value;
        }
        
        if (!isNullOrUndefined(value.get('data'))) {
          rule.data = value.get('data').value;
        }
        rule.message = value.get('message').value;

        property.rules.push(rule);
      }
    }
    property.kind = this.form.get('kind').value;

    return property;
  }

  navigateBack() {
    window.history.back();
  }



}
