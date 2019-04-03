import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { PropertyKind, Rule, RuleKind, PropertyListItem, Property } from '../../_model/properties/Property';

export class PropertyKindOption {
  kind: string;
  label: string;
  display: boolean;
}

export class RuleKindOption {
  kind: string;
  label: string;
  display: boolean;
  disabled: boolean;

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
  @Input() propertyListItems: PropertyListItem[];

  //Form Layout Controls
  isLoaded: boolean;
  isDropdown: boolean;
  hasRules: boolean;
  canAddNewRule: boolean;
  currentRuleValid: boolean;


  currentRuleIndex: number;
  propertyKindOptions: PropertyKindOption[] = [];
  ruleKindOptions: RuleKindOption[] = [];
  propertyLegalValues: any[] = [];

  //SingleProperty
  model: Property<any> = new Property();
  propertyLegalValuesCurrent: any[] = [];
  propertyRulesCurrent: Rule[] = [];

  // propertyChangedValue: any;
  // propertyName: string;
  // propertyKind: PropertyKind;
  // propertyDefaultValue: any;

  
  rulesForm: FormGroup = new FormGroup({});
  
  propertyAddedValue: any;
  

  // propertyRules: Rule[] = [];
  
  
  constructor() { }

  ngOnInit() {


    this.model.rules = [];

    this.preparePropertyKindOptions();
    this.prepareRuleKindOptions();


    for (let i = 0; i < this.propertyLegalValues.length; i++) {
      this.propertyLegalValuesCurrent[i] = this.propertyLegalValues[i];
    }

    this.isDropdown = undefined;
    this.hasRules = false;
    this.isLoaded = true;
    this.canAddNewRule = true;
    this.currentRuleIndex = -1;

  }

  //----------------------------------------------------

  private preparePropertyKindOptions() {
    for (let kind in PropertyKind) {
      this.propertyKindOptions.push({kind: kind, label: kind, display: true});
    }
  }

  private prepareRuleKindOptions() {
    for (let kind in RuleKind) {

      switch(kind) {
        case RuleKind.REQUIRED: case RuleKind.REQUIRED_TRUE: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, disabled: false, hasDataField: false, hasValueField: false});
          break;
        }

        case RuleKind.MAX: case RuleKind.MAX_LENGTH: case RuleKind.MIN: case RuleKind.MIN_LENGTH: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, disabled: false, hasDataField: false, hasValueField: true})
          break;
        }

        case RuleKind.REGEX_PATTERN: {
          this.ruleKindOptions.push({kind: kind, label: kind, display: true, disabled: false, hasDataField: true, hasValueField: false})
        }
      }
      
    }
  }



  //----------------------------------------------------
  //-----------------Legal Values-----------------------
  //----------------------------------------------------



  addPropertyLegalValue() {
    console.log("adding legal value");

    this.model.legalValues.push(this.propertyAddedValue);

    // this.propertyLegalValues.push(this.propertyAddedValue);
    this.propertyLegalValuesCurrent.push(this.propertyAddedValue);
    this.propertyAddedValue = "";
  }

  updatePropertyLegalValue(i: number) {
    console.log("change " + this.model.legalValues[i] + " into " + this.propertyLegalValuesCurrent[i]);
    this.model.legalValues[i] = this.propertyLegalValuesCurrent[i];
  }

  removePropertyLegalValue(i: number) {
    this.model.legalValues.splice(i,1);
    this.propertyLegalValuesCurrent.splice(i,1);
  }

  revertPropertyLegalValue(i: number) {
    console.log("change " + this.propertyLegalValuesCurrent[i] + " into " + this.model.legalValues[i]);
    this.propertyLegalValuesCurrent[i] = this.model.legalValues[i];
  }

  clearPropertyLegalAndDefaultValues() {
    console.log("clearing");
    this.clearPropertyDefaultValues();
    this.clearPropertyLegalValues();
  }

  clearPropertyDefaultValues() {
    this.model.defaultValue = '';
  }

  clearPropertyLegalValues() {
    this.model.legalValues = [];
    this.propertyLegalValuesCurrent = [];    
  }

  //----------------------------------------------------
  //-----------------------Rules------------------------
  //----------------------------------------------------



  addNewRule() {
    

    this.setActiveRule(this.model.rules.length);

    this.rulesForm.addControl('kind'+this.currentRuleIndex, new FormControl(''));
    this.rulesForm.addControl('message'+this.currentRuleIndex, new FormControl(''));
    this.rulesForm.addControl('value'+this.currentRuleIndex, new FormControl(''));
    this.rulesForm.addControl('data'+this.currentRuleIndex, new FormControl(''));

    let rule: Rule = new Rule();
    rule.id = null;
    this.model.rules.push(rule);
    rule = new Rule();
    rule.id = null;
    this.propertyRulesCurrent.push(rule);
    this.canAddNewRule = false;
    this.currentRuleValid = true;


    console.log(this.rulesForm);
    console.log(this.model.rules);
    console.log(this.propertyRulesCurrent);

  }

  setActiveRule(i: number) {
    this.currentRuleIndex = i;
  }

  setActiveNoneRule() {
    this.currentRuleIndex = -1;
  }

  getRuleFieldDisabled(i: number) {
    return i != this.currentRuleIndex;
  }

  updateRule(i: number) {
    console.log("update rule: " + this.model.rules[i].kind);
    console.log("change ");
    console.log(this.model.rules[i]);
    console.log(" into " );
    console.log(this.propertyRulesCurrent[i]);

    let rule = new Rule();
    rule.copyRule(this.propertyRulesCurrent[i]);


    this.currentRuleValid = this.checkIfRuleValid(rule);
    
    if (this.currentRuleValid) {

      let oldOption = this.ruleKindOptions.find((oldOption: RuleKindOption) => {
        return oldOption.kind == this.model.rules[i].kind;
      })

      if (isNullOrUndefined(rule.kind)) {
        oldOption.disabled = false;
      }
      

      this.model.rules[i] = rule;

      let option = this.ruleKindOptions.find((option: RuleKindOption) => {
        return option.kind == rule.kind;
      });

      option.disabled = true;
      this.canAddNewRule = true;
      this.setActiveNoneRule();
    }
  
  }

  checkIfRuleValid(rule: Rule): boolean {
    if (isNullOrUndefined(rule.kind)) {
      return false;
    } else {
      let option = this.ruleKindOptions.find((option: RuleKindOption) => {
        return option.kind == rule.kind;
      })

      let dataPart: boolean;
      if (option.hasDataField) {
        dataPart = !isNullOrUndefined(rule.data);
      } else {
        dataPart = true;
      }

      let valuePart: boolean;
      if (option.hasValueField) {
        valuePart = !isNullOrUndefined(rule.value);
      } else {
        valuePart = true;
      }

      return dataPart && valuePart;
    }
  }

  removeRule(i: number) {
    console.log("remove rule: " + this.propertyRulesCurrent[i].kind);
    let rule = this.propertyRulesCurrent[i];

    let option = this.ruleKindOptions.find((option: RuleKindOption) => {
      return option.kind == rule.kind;
    });
    if (!isNullOrUndefined(option)) {
      option.disabled = false;
    }

    this.propertyRulesCurrent.splice(i,1);
    this.model.rules.splice(i,1);
    this.canAddNewRule = true;
  }

  hasValueField(rule: Rule): boolean {
    // console.log(rule);

    if (isNullOrUndefined(rule) || isNullOrUndefined(rule.kind)) {
      return false;
    }

    return this.ruleKindOptions.find((option: RuleKindOption) => {
      return option.kind == rule.kind;
    }).hasValueField;

    
    
  }

  hasDataField(rule: Rule): boolean {
    // console.log(rule);

    if (isNullOrUndefined(rule) || isNullOrUndefined(rule.kind)) {
      return false;
    }

    return this.ruleKindOptions.find((option: RuleKindOption) => {
      return option.kind == rule.kind;
    }).hasDataField;

  }

  ruleKindUpdatedEvent(event: any, rule: Rule, i: number, option: RuleKindOption) {
    console.log("Event");
    console.log(event);
    console.log(rule);
    console.log(this.propertyRulesCurrent[i]);
    console.log(this.model.rules[i]);
    console.log(option);
    this.propertyRulesCurrent[i].kind = RuleKind[option.kind];

    //TODO make events for the rest as well
  }

  trackByFn(index: any, item: any) {
    return index;
  }


}
