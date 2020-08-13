import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  FormGroup, FormControl, Validators, FormArray, FormBuilder,
} from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';
import {
  FlatPropertyDefinition, PropertyType,
} from 'app/main/content/_model/meta/property/property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { propertyNameUniqueValidator } from 'app/main/content/_validator/property-name-unique.validator';
import { User } from 'app/main/content/_model/user';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { Tenant } from 'app/main/content/_model/tenant';
import { ConstraintType } from 'app/main/content/_model/meta/constraint';

export interface PropertyTypeOption {
  type: PropertyType;
  label: string;
  display: boolean;
}

export interface ConstraintOption {
  type: ConstraintType;
  label: string;
  display: boolean;
}

const availablePropertyTypes = [PropertyType.TEXT, PropertyType.LONG_TEXT, PropertyType.WHOLE_NUMBER, PropertyType.FLOAT_NUMBER, PropertyType.BOOL, PropertyType.DATE];
const availableConstraints = [
  { type: PropertyType.TEXT, constraints: [ConstraintType.MAX_LENGTH, ConstraintType.MIN_LENGTH, ConstraintType.PATTERN] },
  { type: PropertyType.LONG_TEXT, constraints: [ConstraintType.MAX_LENGTH, ConstraintType.MIN_LENGTH, ConstraintType.PATTERN] },
  { type: PropertyType.WHOLE_NUMBER, constraints: [ConstraintType.MAX, ConstraintType.MIN, ConstraintType.MAX_LENGTH, ConstraintType.MIN_LENGTH] },
  { type: PropertyType.FLOAT_NUMBER, constraints: [ConstraintType.MAX, ConstraintType.MIN] },
  { type: PropertyType.BOOL, constraints: [] },
  { type: PropertyType.DATE, constraints: [ConstraintType.MAX, ConstraintType.MIN] }
];


@Component({
  selector: "app-flat-property-builder",
  templateUrl: './flat-property-builder.component.html',
  styleUrls: ['./flat-property-builder.component.scss'],
})
export class FlatPropertyBuilderComponent implements OnInit {
  @Input() marketplace: Marketplace;
  @Input() tenantAdmin: User;
  @Input() entryId: string;
  @Input() sourceString: string;
  @Output() result: EventEmitter<{
    builderType: string;
    value: FlatPropertyDefinition<any>;
  }> = new EventEmitter();

  loaded: boolean;
  dropdownToggled: boolean;

  propertyTypeOptions: PropertyTypeOption[];
  currentConstraintOptions: ConstraintOption[];

  form: FormGroup;

  allowedValues: FormArray;
  constraints: FormArray;

  allPropertyDefinitions: FlatPropertyDefinition<any>[];
  propertyDefinition: FlatPropertyDefinition<any>;
  tenant: Tenant;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private propertyDefinitionService: FlatPropertyDefinitionService,
    private loginService: LoginService
  ) { }

  async ngOnInit() {
    this.preparePropertyTypeOptions();
    this.clearForm();

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];

    this.dropdownToggled = false;

    if (!isNullOrUndefined(this.entryId)) {
      Promise.all([
        this.getAllPropertyDefinitions(),
        this.getCurrentPropertyDefinition(),
      ]).then(() => {
        this.populateForm();
        this.loaded = true;
      });
    } else {
      this.getAllPropertyDefinitions().then(() => {
        this.loaded = true;
      });
    }
  }

  private getAllPropertyDefinitions() {
    return this.propertyDefinitionService
      .getAllPropertyDefinitons(this.marketplace, this.tenant.id)
      .toPromise()
      .then((ret: FlatPropertyDefinition<any>[]) => {
        this.allPropertyDefinitions = ret;
      });
  }

  private getCurrentPropertyDefinition() {
    return this.propertyDefinitionService
      .getPropertyDefinitionById(this.marketplace, this.entryId, this.tenant.id)
      .toPromise()
      .then((ret: FlatPropertyDefinition<any>) => {
        this.propertyDefinition = ret;
      });
  }

  // ----------------------------------------------------

  private preparePropertyTypeOptions() {
    this.propertyTypeOptions = [];

    for (const propertyType of availablePropertyTypes) {
      this.propertyTypeOptions.push({
        type: propertyType,
        label: PropertyType.getLabelForPropertyType(propertyType),
        display: true
      });
    }
  }

  prepareConstraintTypeOptions(propertyType: PropertyType) {
    this.currentConstraintOptions = [];

    if (isNullOrUndefined(propertyType)) {
      return;
    }
    console.log(propertyType);

    const constraintsForType = availableConstraints.find(c => c.type === propertyType);

    for (const constraint of constraintsForType.constraints) {
      this.currentConstraintOptions.push({
        type: constraint,
        label: ConstraintType.getLabelForConstraintType(constraint),
        display: true
      });
    }

    console.log(this.currentConstraintOptions);

  }

  handleTypeSelectionChange() {
    this.clearAllowedValues();
    this.clearConstraints();
    this.prepareConstraintTypeOptions(this.form.controls['type'].value);
  }

  clearForm() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', [
        Validators.required,
        propertyNameUniqueValidator(
          this.allPropertyDefinitions,
          this.propertyDefinition
        ),
      ]),
      type: this.formBuilder.control('', Validators.required),
      allowedValues: this.formBuilder.array([]),

      description: this.formBuilder.control(''),

      required: this.formBuilder.control(''),
      constraints: this.formBuilder.array([])
    });

    if (!isNullOrUndefined(this.propertyDefinition)) {
      this.populateForm();
    }
  }

  populateForm() {
    this.form.get('name').setValue(this.propertyDefinition.name);
    this.form.get('type').setValue(this.propertyDefinition.type);
    this.form.get('description').setValue(this.propertyDefinition.description);

    if (
      !isNullOrUndefined(this.propertyDefinition.allowedValues) &&
      this.propertyDefinition.allowedValues.length > 0
    ) {
      this.dropdownToggled = true;
      let i = 0;
      for (const value of this.propertyDefinition.allowedValues) {
        this.addAllowedValue();
        this.form
          .get('allowedValues')
          .get('' + i)
          .get('value')
          .setValue(value);
        i++;
      }
    }
  }


  clearFormArrays() {
    this.clearAllowedValues();
    this.clearConstraints();
  }

  // ----------------------------------------------------
  // --------------Allowed Values Array------------------
  // ----------------------------------------------------

  addAllowedValue() {
    this.allowedValues = this.form.get('allowedValues') as FormArray;
    this.allowedValues.push(this.createAllowedValue());
  }


  createAllowedValue(): FormGroup {
    return this.formBuilder.group({
      value: [undefined, Validators.required],
    });
  }

  markAllowedValuesAsTouched() {
    if (!isNullOrUndefined(this.form.get('allowedValues'))) {
      Object.keys(
        (this.form.get('allowedValues') as FormArray).controls
      ).forEach((key) => {
        (this.form
          .get('allowedValues')
          .get(key) as FormGroup).controls.value.markAsTouched();
      });
    }
  }

  removeAllowedValue(i: number) {
    this.allowedValues.removeAt(i);
  }

  clearAllowedValues() {
    this.form.removeControl('allowedValues');
    this.form.addControl('allowedValues', this.formBuilder.array([]));
  }

  // ----------------------------------------------------
  // -----------------Constraint Array-------------------
  // ----------------------------------------------------


  addConstraint() {
    this.constraints = this.form.get('constraints') as FormArray;
    this.constraints.push(this.createConstraintValue());
    console.log(this.form.get('constraints'));
  }

  createConstraintValue(): FormGroup {
    return this.formBuilder.group({
      type: [undefined, Validators.required],
      value: ['', Validators.required],
      message: ['']
    });
  }

  removeConstraint(i: number) {
    this.constraints.removeAt(i);
  }

  clearConstraints() {
    this.form.removeControl('constraints');
    this.form.addControl('constraints', this.formBuilder.array([]));
  }

  // --Validity Checks and Queries

  isFieldInvalid(value: FormControl) {
    if (isNullOrUndefined(value)) {
      return false;
    }
    return value.invalid;
  }

  isDropdownListDisplayed() {
    return this.dropdownToggled && this.form.get('type').value !== '';
  }

  // ----------------------------------------------------
  // ------------------CONSTRAINTS-----------------------
  // ----------------------------------------------------



  // ---

  trackByFn(index: any, item: any) {
    return index;
  }

  onSubmit() {
    if (this.form.valid) {
      const property = this.createPropertyFromForm();

      this.propertyDefinitionService
        .createNewPropertyDefinition(this.marketplace, [property])
        .toPromise()
        .then((ret: FlatPropertyDefinition<any>[]) => {
          if (!isNullOrUndefined(ret) && ret.length > 0) {
            this.result.emit({ builderType: 'property', value: ret[0] });
          } else {
            this.result.emit(undefined);
          }
        });
    } else {
      this.markAllowedValuesAsTouched();
    }
  }

  handleCancelClick() {
    this.result.emit(undefined);
  }

  createPropertyFromForm(): FlatPropertyDefinition<any> {
    const property: FlatPropertyDefinition<any> = new FlatPropertyDefinition<any>();
    property.tenantId = this.tenant.id;
    property.custom = true;

    if (isNullOrUndefined(this.propertyDefinition)) {
      property.id = null;
    } else {
      property.id = this.propertyDefinition.id;
    }

    property.name = this.form.get('name').value;
    property.allowedValues = [];
    if (!isNullOrUndefined(this.form.get('allowedValues'))) {
      for (const value of (this.form.get('allowedValues') as FormArray).value) {
        property.allowedValues.push(value.value);
      }
    }
    property.propertyConstraints = [];
    property.type = this.form.get('type').value;
    property.description = this.form.get('description').value;

    return property;
  }
}
