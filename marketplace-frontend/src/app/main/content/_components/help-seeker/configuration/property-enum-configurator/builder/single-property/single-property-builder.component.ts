import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  FormArray,
  FormBuilder,
} from "@angular/forms";
import { isNullOrUndefined } from "util";
import { Router } from "@angular/router";
import {
  PropertyDefinition,
  PropertyType,
} from "app/main/content/_model/meta/property";
import { Marketplace } from "app/main/content/_model/marketplace";
import { PropertyDefinitionService } from "app/main/content/_service/meta/core/property/property-definition.service";
import { propertyNameUniqueValidator } from "app/main/content/_validator/property-name-unique.validator";
import { listNotEmptyValidator } from "app/main/content/_validator/list-not-empty.validator";
import { PropertyConstraint } from "app/main/content/_model/meta/constraint";
import { User, UserRole } from "app/main/content/_model/user";
import { LoginService } from "app/main/content/_service/login.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

export interface PropertyTypeOption {
  type: PropertyType;
  label: string;
  display: boolean;
}

@Component({
  selector: "app-single-property-builder",
  templateUrl: "./single-property-builder.component.html",
  styleUrls: ["./single-property-builder.component.scss"],
})
export class SinglePropertyBuilderComponent implements OnInit {
  @Input() marketplace: Marketplace;
  @Input() helpseeker: User;
  @Input() entryId: string;
  @Input() sourceString: string;
  @Output() result: EventEmitter<{
    builderType: string;
    value: PropertyDefinition<any>;
  }> = new EventEmitter();

  loaded: boolean;
  dropdownToggled: boolean;

  propertyTypeOptions: PropertyTypeOption[];

  form: FormGroup;

  allowedValues: FormArray;

  allPropertyDefinitions: PropertyDefinition<any>[];
  propertyDefinition: PropertyDefinition<any>;
  tenant: Tenant;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private propertyDefinitionService: PropertyDefinitionService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];
    console.error(this.tenant);

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

  getAllPropertyDefinitions() {
    return this.propertyDefinitionService
      .getAllPropertyDefinitons(
        this.marketplace,
        this.tenant.id
        // this.helpseeker.subscribedTenants.find(
        //   (t) =>
        //     t.role === UserRole.HELP_SEEKER || t.role === UserRole.TENANT_ADMIN
        // ).tenantId
      )
      .toPromise()
      .then((ret: PropertyDefinition<any>[]) => {
        this.allPropertyDefinitions = ret;
      });
  }

  getCurrentPropertyDefinition() {
    return this.propertyDefinitionService
      .getPropertyDefinitionById(
        this.marketplace,
        this.entryId,
        this.tenant.id
        // this.helpseeker.subscribedTenants.find(
        //   (t) =>
        //     t.role === UserRole.HELP_SEEKER || t.role === UserRole.TENANT_ADMIN
        // ).tenantId
      )
      .toPromise()
      .then((ret: PropertyDefinition<any>) => {
        this.propertyDefinition = ret;
      });
  }

  // ----------------------------------------------------

  private preparePropertyTypeOptions() {
    this.propertyTypeOptions = [];
    this.propertyTypeOptions.push({
      type: PropertyType.TEXT,
      label: PropertyType.getLabelForPropertyType(PropertyType.TEXT),
      display: true,
    });
    this.propertyTypeOptions.push({
      type: PropertyType.LONG_TEXT,
      label: PropertyType.getLabelForPropertyType(PropertyType.LONG_TEXT),
      display: true,
    });
    this.propertyTypeOptions.push({
      type: PropertyType.WHOLE_NUMBER,
      label: PropertyType.getLabelForPropertyType(PropertyType.WHOLE_NUMBER),
      display: true,
    });
    this.propertyTypeOptions.push({
      type: PropertyType.FLOAT_NUMBER,
      label: PropertyType.getLabelForPropertyType(PropertyType.FLOAT_NUMBER),
      display: true,
    });
    this.propertyTypeOptions.push({
      type: PropertyType.BOOL,
      label: PropertyType.getLabelForPropertyType(PropertyType.BOOL),
      display: true,
    });
    this.propertyTypeOptions.push({
      type: PropertyType.DATE,
      label: PropertyType.getLabelForPropertyType(PropertyType.DATE),
      display: true,
    });
  }

  clearForm() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control("", [
        Validators.required,
        propertyNameUniqueValidator(
          this.allPropertyDefinitions,
          this.propertyDefinition
        ),
      ]),
      type: this.formBuilder.control("", Validators.required),
      allowedValues: this.formBuilder.array([]),
      description: this.formBuilder.control(""),
    });

    if (!isNullOrUndefined(this.propertyDefinition)) {
      this.populateForm();
    }
  }

  populateForm() {
    this.form.get("name").setValue(this.propertyDefinition.name);
    this.form.get("type").setValue(this.propertyDefinition.type);
    this.form.get("description").setValue(this.propertyDefinition.description);

    if (
      !isNullOrUndefined(this.propertyDefinition.allowedValues) &&
      this.propertyDefinition.allowedValues.length > 0
    ) {
      this.dropdownToggled = true;
      let i = 0;
      for (const value of this.propertyDefinition.allowedValues) {
        this.addAllowedValue();
        this.form
          .get("allowedValues")
          .get("" + i)
          .get("value")
          .setValue(value);
        i++;
      }
    }
  }

  // ----------------------------------------------------
  // -----------------Allowed Values-----------------------
  // ----------------------------------------------------

  createAllowedValue(): FormGroup {
    return this.formBuilder.group({
      value: [undefined, Validators.required],
    });
  }

  addAllowedValue() {
    this.allowedValues = this.form.get("allowedValues") as FormArray;
    this.allowedValues.push(this.createAllowedValue());
  }

  markAllowedValuesAsTouched() {
    if (!isNullOrUndefined(this.form.get("allowedValues"))) {
      Object.keys(
        (this.form.get("allowedValues") as FormArray).controls
      ).forEach((key) => {
        (this.form
          .get("allowedValues")
          .get(key) as FormGroup).controls.value.markAsTouched();
      });
    }
  }

  removeAllowedValue(i: number) {
    this.allowedValues.removeAt(i);
  }

  clearAllowedValues() {
    this.form.removeControl("allowedValues");
    this.form.addControl("allowedValues", this.formBuilder.array([]));
  }

  // --Validity Checks and Queries

  isFieldInvalid(value: FormControl) {
    if (isNullOrUndefined(value)) {
      return false;
    }
    return value.invalid;
  }

  isDropdownListDisplayed() {
    return this.dropdownToggled && this.form.get("type").value !== "";
  }

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
        .then((ret: PropertyDefinition<any>[]) => {
          if (!isNullOrUndefined(ret) && ret.length > 0) {
            this.result.emit({ builderType: "property", value: ret[0] });
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

  createPropertyFromForm(): PropertyDefinition<any> {
    const property: PropertyDefinition<any> = new PropertyDefinition<any>();
    property.tenantId = this.tenant.id;
    // this.helpseeker.subscribedTenants.find(
    //   (t) => t.role === UserRole.HELP_SEEKER || t.role === UserRole.TENANT_ADMIN
    // ).tenantId;
    property.custom = true;

    if (isNullOrUndefined(this.propertyDefinition)) {
      property.id = null;
    } else {
      property.id = this.propertyDefinition.id;
    }

    property.name = this.form.get("name").value;
    property.allowedValues = [];
    if (!isNullOrUndefined(this.form.get("allowedValues"))) {
      for (const value of (this.form.get("allowedValues") as FormArray).value) {
        property.allowedValues.push(value.value);
      }
    }
    property.propertyConstraints = [];
    property.type = this.form.get("type").value;
    property.description = this.form.get("description").value;

    return property;
  }
}
