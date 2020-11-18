import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { propertyNameUniqueValidator } from 'app/main/content/_validator/property-name-unique.validator';
import { PropertyType, FlatPropertyDefinition } from 'app/main/content/_model/configurator/property/property';
import { ConstraintType, PropertyConstraint } from 'app/main/content/_model/configurator/constraint';
import { ResponseService } from 'app/main/content/_service/response.service';
import { stringsUnique } from 'app/main/content/_validator/strings-unique.validator';

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
  @Input() entryId: string;
  @Input() sourceString: string;
  @Input() tenantId: string;
  @Input() redirectUrl: string;
  @Output() result: EventEmitter<{
    builderType: string;
    value: FlatPropertyDefinition<any>;
  }> = new EventEmitter();

  loaded: boolean;
  dropdownToggled: boolean;
  constraintsToggled: boolean;

  propertyTypeOptions: PropertyTypeOption[];
  currentConstraintOptions: ConstraintOption[];

  form: FormGroup;

  allowedValues: FormArray;
  constraints: FormArray;

  allPropertyDefinitions: FlatPropertyDefinition<any>[];
  propertyDefinition: FlatPropertyDefinition<any>;

  constructor(
    private formBuilder: FormBuilder,
    private propertyDefinitionService: FlatPropertyDefinitionService,
    private responseService: ResponseService,
  ) { }

  async ngOnInit() {
    this.preparePropertyTypeOptions();
    this.clearForm();

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
      .getAllPropertyDefinitons()
      .toPromise()
      .then((ret: FlatPropertyDefinition<any>[]) => {
        this.allPropertyDefinitions = ret;
      });
  }

  private getCurrentPropertyDefinition() {
    return this.propertyDefinitionService
      .getPropertyDefinitionById(this.entryId)
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

    const constraintsForType = availableConstraints.find(c => c.type === propertyType);

    for (const constraint of constraintsForType.constraints) {
      this.currentConstraintOptions.push({
        type: constraint,
        label: ConstraintType.getLabelForConstraintType(constraint),
        display: true
      });
    }
  }

  handleTypeSelectionChange() {
    this.clearAllowedValues();
    this.clearConstraints();
    this.prepareConstraintTypeOptions(this.form.controls['type'].value);
  }

  handleComputedToggled() {
    this.dropdownToggled = false;
    this.clearFormArrays();
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
      computed: this.formBuilder.control(false),


      allowedValues: this.formBuilder.array([]),

      description: this.formBuilder.control(''),

      required: this.formBuilder.control(''),
      requiredMessage: this.formBuilder.control(''),
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
    this.form.get('computed').setValue(this.propertyDefinition.computed);

    if (!isNullOrUndefined(this.propertyDefinition.allowedValues) && this.propertyDefinition.allowedValues.length > 0) {
      this.dropdownToggled = true;
      let i = 0;
      for (const value of this.propertyDefinition.allowedValues) {
        this.addAllowedValue();
        this.form.get('allowedValues').get('' + i).get('value').setValue(value);
        i++;
      }
    }

    this.constraintsToggled = this.propertyDefinition.required || this.propertyDefinition.propertyConstraints.length > 0;

    this.form.get('required').setValue(this.propertyDefinition.required);
    this.form.get('requiredMessage').setValue(this.propertyDefinition.requiredMessage);

    if (!isNullOrUndefined(this.propertyDefinition.propertyConstraints) && this.propertyDefinition.propertyConstraints.length > 0) {
      let i = 0;
      this.prepareConstraintTypeOptions(this.propertyDefinition.type);
      for (const constraint of this.propertyDefinition.propertyConstraints) {
        this.addConstraint();
        this.form.get('constraints').get('' + i).get('type').setValue(constraint.constraintType);
        this.form.get('constraints').get('' + i).get('value').setValue(constraint.value);
        this.form.get('constraints').get('' + i).get('message').setValue(constraint.message);
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
  }

  createConstraintValue(): FormGroup {
    return this.formBuilder.group({
      type: ['', Validators.required],
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

  checkConstraintTypeVisiblity(type: ConstraintType) {

    for (const control of (this.form.get('constraints') as FormArray).controls) {
      if (control.get('type').value === type) { return true; }
    }
    return false;
  }

  markConstraintsAsTouched() {
    if (!isNullOrUndefined(this.form.get('constraints'))) {
      Object.keys(
        (this.form.get('constraints') as FormArray).controls
      ).forEach((key) => {
        Object.keys((this.form
          .get('constraints')
          .get(key) as FormGroup).controls).forEach(innerKey => {
            this.form.get('constraints').get(key).get(innerKey).markAsTouched();
          });
      });
    }
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

  // ---

  trackByFn(index: any, item: any) {
    return index;
  }

  onSubmit() {
    if (this.form.valid) {

      this.form.disable();
      const property = this.createPropertyFromForm();
      property.tenantId = this.tenantId;

      this.propertyDefinitionService.createNewPropertyDefinition(property).toPromise().then((ret: FlatPropertyDefinition<any>) => {
        if (!isNullOrUndefined(ret)) {
          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, [ret.id], undefined, 'save').toPromise().then(() => {
            this.result.emit({ builderType: 'property', value: ret });
          });
        } else {
          this.result.emit(undefined);
        }
      }).catch(error => {
        this.form.enable();
        const str = '' + this.form.value.name;
        this.form.controls['name'].setValidators([Validators.required, stringsUnique(str, this.form.value.name)]);
        this.form.controls['name'].updateValueAndValidity();
      });
    } else {
      this.markAllowedValuesAsTouched();
      this.markConstraintsAsTouched();
    }
  }


  handleNameKeyUp() {
    if (this.form.controls['name'].hasError('stringsUnique')) {
      this.form.controls['name'].setValidators([Validators.required]);
      this.form.controls['name'].updateValueAndValidity();
    }
  }

  handleCancelClick() {
    this.result.emit(undefined);
  }

  createPropertyFromForm(): FlatPropertyDefinition<any> {
    const property: FlatPropertyDefinition<any> = new FlatPropertyDefinition<any>();
    property.custom = true;
    property.computed = this.form.value.computed;

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
    property.type = this.form.get('type').value;
    property.description = this.form.get('description').value;

    property.propertyConstraints = [];

    property.required = this.form.get('required').value;
    if (property.required) {
      property.requiredMessage = this.form.get('requiredMessage').value;
    }

    if (!isNullOrUndefined(this.form.get('constraints'))) {
      for (const control of (this.form.get('constraints') as FormArray).controls) {
        const propertyConstraint = new PropertyConstraint<any>();
        propertyConstraint.constraintType = control.get('type').value;
        propertyConstraint.value = control.get('value').value;
        if (!isNullOrUndefined(control.get('message').value) && control.get('message').value.length > 0) {
          propertyConstraint.message = control.get('message').value;
        }
        propertyConstraint.propertyType = property.type;

        property.propertyConstraints.push(propertyConstraint);
      }
    }

    return property;
  }
}
