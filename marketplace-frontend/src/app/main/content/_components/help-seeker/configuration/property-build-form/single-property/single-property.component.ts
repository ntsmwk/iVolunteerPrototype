import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { Router } from '@angular/router';
import { PropertyDefinition, PropertyType } from 'app/main/content/_model/meta/property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { propertyNameUniqueValidator } from 'app/main/content/_validator/property-name-unique.validator';
import { listNotEmptyValidator } from 'app/main/content/_validator/list-not-empty.validator';
import { PropertyConstraint } from 'app/main/content/_model/meta/constraint';
import { Helpseeker } from 'app/main/content/_model/helpseeker';

export interface PropertyTypeOption {
  type: PropertyType;
  label: string;
  display: boolean;
}

@Component({
  selector: 'app-single-property',
  templateUrl: './single-property.component.html',
  styleUrls: ['./single-property.component.scss']
})
export class SinglePropertyComponent implements OnInit {

  @Input() marketplace: Marketplace;
  @Input() helpseeker: Helpseeker;

  model: PropertyDefinition<any>;
  allPropertyDefinitions: PropertyDefinition<any>[];

  loaded: boolean; dropdownToggled: boolean;

  propertyTypeOptions: PropertyTypeOption[];

  form: FormGroup;

  allowedValues: FormArray;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private propertyDefinitionService: PropertyDefinitionService) { }

  ngOnInit() {
    this.preparePropertyTypeOptions();

    this.clearForm();

    this.dropdownToggled = false;

    this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace, this.helpseeker.tenantId).toPromise().then((ret: PropertyDefinition<any>[]) => {
      this.allPropertyDefinitions = ret;
      this.loaded = true;
    });
  }

  // ----------------------------------------------------

  private preparePropertyTypeOptions() {
    this.propertyTypeOptions = [];
    this.propertyTypeOptions.push({ type: PropertyType.TEXT, label: PropertyType.getLabelForPropertyType(PropertyType.TEXT), display: true });
    this.propertyTypeOptions.push({ type: PropertyType.LONG_TEXT, label: PropertyType.getLabelForPropertyType(PropertyType.LONG_TEXT), display: true });
    this.propertyTypeOptions.push({ type: PropertyType.WHOLE_NUMBER, label: PropertyType.getLabelForPropertyType(PropertyType.WHOLE_NUMBER), display: true });
    this.propertyTypeOptions.push({ type: PropertyType.FLOAT_NUMBER, label: PropertyType.getLabelForPropertyType(PropertyType.FLOAT_NUMBER), display: true });
    this.propertyTypeOptions.push({ type: PropertyType.BOOL, label: PropertyType.getLabelForPropertyType(PropertyType.BOOL), display: true });
    this.propertyTypeOptions.push({ type: PropertyType.DATE, label: PropertyType.getLabelForPropertyType(PropertyType.DATE), display: true });
  }

  clearForm() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required, propertyNameUniqueValidator(this.allPropertyDefinitions, this.model)]),
      type: this.formBuilder.control('', Validators.required),
      allowedValues: this.formBuilder.array([]),
    });
  }

  // ----------------------------------------------------
  // -----------------Allowed Values-----------------------
  // ----------------------------------------------------

  createAllowedValue(): FormGroup {
    return this.formBuilder.group({
      value: [undefined, Validators.required]
    });
  }

  addAllowedValue() {
    this.allowedValues = this.form.get('allowedValues') as FormArray;
    this.allowedValues.push(this.createAllowedValue());
  }

  markAllowedValuesAsTouched() {
    if (!isNullOrUndefined(this.form.get('allowedValues'))) {
      Object.keys((this.form.get('allowedValues') as FormArray).controls).forEach(key => {
        (this.form.get('allowedValues').get(key) as FormGroup).controls.value.markAsTouched();
      });
    }
  }

  removeAllowedValue(i: number) {
    this.allowedValues.removeAt(i);
  }

  clearAllowedValues() {
    this.form.removeControl('allowedValues');
    this.form.addControl('allowedValues', this.formBuilder.array([], listNotEmptyValidator()));
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


  onSubmit(valid: boolean, f: any) {
    if (valid) {

      const property = this.createPropertyFromForm();

      // // TODO call service to send to server (and save in db)
      // this.propertyDefinitionService.createNewPropertyDefinition(this.marketplace, [property]).toPromise().then(() => {

      //   this.router.navigate([`/main/configurator`], { queryParams: { open: 'haubenofen' } });

      // });

      console.log('VALID');
      const ret = JSON.stringify(property, null, 2);
      console.log(ret);
    } else {
      console.log('INVALID');
      console.log(JSON.stringify(this.createPropertyFromForm(), null, 2));

      console.log('Valid: ' + valid);

      this.markAllowedValuesAsTouched();
    }
  }

  createPropertyFromForm(): PropertyDefinition<any> {
    const property: PropertyDefinition<any> = new PropertyDefinition<any>();

    if (isNullOrUndefined(this.model)) {
      property.id = null;
    } else {
      property.id = this.model.id;
    }

    property.name = this.form.get('name').value;

    property.allowedValues = [];
    if (!isNullOrUndefined(this.form.get('allowedValues'))) {
      for (const value of (this.form.get('allowedValues') as FormArray).value) {
        property.allowedValues.push(value.value);
      }
    }

    property.propertyConstraints = [];
    if (!isNullOrUndefined(this.form.get('rules'))) {
      for (const value of (this.form.get('rules') as FormArray).controls) {

        const constraint = new PropertyConstraint();
        constraint.id = null;
        constraint.constraintType = value.get('type').value;

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
    property.type = this.form.get('type').value;

    return property;
  }

  navigateBack() {
    window.history.back();
  }



}
