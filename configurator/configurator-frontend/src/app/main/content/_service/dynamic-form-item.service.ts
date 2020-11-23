import { Injectable } from '@angular/core';
import { PropertyType, ClassProperty } from '../_model/configurator/property/property';
import { isNullOrUndefined } from 'util';
import { Validators, ValidatorFn } from '@angular/forms';
import { PropertyConstraint, ConstraintType } from '../_model/configurator/constraint';
import {
  TextboxFormItem, DropdownMultipleFormItem, DropdownFormItem, DynamicFormItemBase, NumberBoxFormItem,
  SingleSelectionTreeFormItem, DatepickerFormItem, TupleDropdownFormItem, GenericFormItem,
  MultipleSelectionTreeFormItem, SlideToggleFormItem, TextAreaFormItem, NumberDropdownFormItem, LocationFormItem, ComputedFormItem
} from '../_model/dynamic-forms/item';
import { Location } from '../_model/configurator/property/location';

export interface ValidatorData {
  validators: ValidatorFn[];
  messages: Map<string, string>; // Key - Message
}

export interface SingleValidatorData {
  key: string;
  validator: ValidatorFn;
}

@Injectable({
  providedIn: 'root',
})
export class DynamicFormItemService {
  key = 0;

  public getFormItemsFromProperties(classProperties: ClassProperty<any>[], idPrefix?: string): DynamicFormItemBase<any>[] {
    const formItems: DynamicFormItemBase<any>[] = this.createFormItemsFromProperties(classProperties, idPrefix);

    return formItems.sort((a, b) => a.order - b.order);
  }

  private createFormItemsFromProperties(classProperties: ClassProperty<any>[], idPrefix?: string) {
    const formItems: DynamicFormItemBase<any>[] = [];
    for (const property of classProperties) {

      const formItem = this.createFormItemFromProperty(property);

      if (!isNullOrUndefined(idPrefix)) {
        formItem.key = idPrefix + '.' + property.id;
      } else {
        formItem.key = property.id;
      }
      formItem.label = property.name;
      formItem.order = property.position;

      const validatorData = this.getValidatorData(property.propertyConstraints, property.type);

      if (!isNullOrUndefined(validatorData)) {
        formItem.validators = validatorData.validators;
        formItem.messages = validatorData.messages;
      }
      formItem.required = property.required;

      if (property.required) {
        formItem.validators.push(Validators.required);
      }

      formItems.push(formItem);
    }
    return formItems;
  }

  private createFormItemFromProperty(property: ClassProperty<any>): DynamicFormItemBase<any> {
    let formItem;

    if (property.computed) {
      formItem = new ComputedFormItem({
        value: isNullOrUndefined(ClassProperty.getDefaultValue(property)) ? '' : ClassProperty.getDefaultValue(property),
        returnType: property.type,
      });
    } else {
      if (property.type === PropertyType.TEXT) {
        if (isNullOrUndefined(property.allowedValues) || property.allowedValues.length <= 0) {
          formItem = new TextboxFormItem({
            value: ClassProperty.getDefaultValue(property),
          });
        } else {
          if (property.multiple) {
            formItem = new DropdownMultipleFormItem({
              values: property.defaultValues,
              options: this.setAsListValues(property.allowedValues)
            });
          } else {
            formItem = new DropdownFormItem({
              value: ClassProperty.getDefaultValue(property),
              options: property.allowedValues
            });
          }
        }

      } else if (property.type === PropertyType.WHOLE_NUMBER || property.type === PropertyType.FLOAT_NUMBER) {

        if (isNullOrUndefined(property.allowedValues) || property.allowedValues.length <= 0) {
          formItem = new NumberBoxFormItem({
            value: ClassProperty.getDefaultValue(property),
          });

        } else {
          if (property.multiple) {
            formItem = new DropdownMultipleFormItem({
              values: property.defaultValues,
              options: property.allowedValues

            });
          } else {
            formItem = new NumberDropdownFormItem({
              value: ClassProperty.getDefaultValue(property),
              options: property.allowedValues
            });
          }
        }

      } else if (property.type === PropertyType.LONG_TEXT) {
        formItem = new TextAreaFormItem({
          value: ClassProperty.getDefaultValue(property),
        });

      } else if (property.type === PropertyType.BOOL) {
        formItem = new SlideToggleFormItem({
          value: ClassProperty.getDefaultValue(property),
        });

      } else if (property.type === PropertyType.TREE) {
        if (property.multiple) {
          formItem = new MultipleSelectionTreeFormItem({
            values: property.defaultValues,
            options: property.allowedValues
          });
        } else {
          formItem = new SingleSelectionTreeFormItem({
            values: property.defaultValues,
            value: ClassProperty.getDefaultValue(property),
            options: property.allowedValues
          });
        }
      } else if (property.type === PropertyType.DATE) {
        formItem = new DatepickerFormItem({
          value: this.setDateValue(ClassProperty.getDefaultValue(property)),
        });

      } else if (property.type === PropertyType.TUPLE) {
        if (!isNullOrUndefined(property.allowedValues) && property.allowedValues.length > 0) {
          formItem = new TupleDropdownFormItem({
            options: property.allowedValues,
            value: ClassProperty.getDefaultValue(property)
          });
        }

      } else if (property.type === PropertyType.LOCATION) {
        formItem = new LocationFormItem({
          options: property.allowedValues,
          value: isNullOrUndefined(ClassProperty.getDefaultValue(property)) ? new Location() : ClassProperty.getDefaultValue(property),
        });

      } else {
        console.log('property kind not implemented: ' + property.type);
        formItem = new GenericFormItem({
        });
      }
    }
    return formItem;
  }

  private setAsListValues(values: any[]): { key: any, value: any }[] {
    const ret: any[] = [];
    if (!isNullOrUndefined(values)) {
      for (let i = 0; i < values.length; i++) {
        ret.push({ key: values[i], value: values[i] });
      }
    }
    return ret;
  }

  private setDateValue(value: any) {
    return !isNullOrUndefined ? new Date(value) : undefined;
  }

  private getValidatorData(propertyConstraints: PropertyConstraint<any>[], propertyType: PropertyType): ValidatorData {
    const validators: ValidatorFn[] = [];
    const messages: Map<string, string> = new Map<string, string>();

    if (!isNullOrUndefined(propertyConstraints)) {
      for (const constraint of propertyConstraints) {

        const singleValidatorData = this.convertRuleToValidator(constraint, propertyType);

        if (!isNullOrUndefined(singleValidatorData)) {
          validators.push(singleValidatorData.validator);
          messages.set(singleValidatorData.key, constraint.message);
        }
      }
      return { validators: validators, messages: messages };

    } else {
      return undefined;
    }
  }

  private convertRuleToValidator(propertyConstraint: PropertyConstraint<any>, propertyType: PropertyType): SingleValidatorData {

    let validator: ValidatorFn;
    let key: string;

    switch (ConstraintType[propertyConstraint.constraintType]) {

      case ConstraintType.PATTERN:
        // console.log("adding regex_pattern Validator" + rule.regex);
        validator = Validators.pattern(propertyConstraint.value);
        key = 'pattern';
        break;

      case ConstraintType.MAX_LENGTH:
        // console.log("adding max_length Validator" + rule.value);
        validator = Validators.maxLength(propertyConstraint.value);
        key = 'maxlength';
        break;

      case ConstraintType.MIN_LENGTH:
        // console.log("adding min_length Validator: " + rule.value);
        validator = Validators.minLength(propertyConstraint.value);
        key = 'minlength';
        break;

      case ConstraintType.MAX:
        // console.log("adding max Validator" + rule.value);
        validator = Validators.max(propertyConstraint.value);
        key = 'max';
        break;

      case ConstraintType.MIN:
        // console.log("adding min Validator" + rule.value);
        validator = Validators.min(propertyConstraint.value);
        key = 'min';
        break;

      default:
        console.error('VALIDATORS: switch-default should not happen');
        break;
    }

    const ret = { key: key, validator: validator };
    return ret;
  }
}
