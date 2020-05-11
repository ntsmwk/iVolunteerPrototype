import { Injectable } from '@angular/core';
import {
  DropdownQuestion, QuestionBase, TextboxQuestion, NumberBoxQuestion, NumberDropdownQuestion, TextAreaQuestion,
  SlideToggleQuestion, DropdownMultipleQuestion, DatepickerQuestion, GenericQuestion, MultipleSelectionEnumQuestion, SingleSelectionEnumQuestion, TupleDropdownQuestion
} from '../_model/dynamic-forms/questions';
import { PropertyType, ClassProperty } from '../_model/meta/property';
import { isNullOrUndefined } from 'util';
import { Validators, ValidatorFn } from '@angular/forms';

import { PropertyConstraint, ConstraintType } from '../_model/meta/constraint';

export interface ValidatorData {
  validators: ValidatorFn[];
  required: boolean;
  messages: Map<string, string>; // Key - Message
}

export interface SingleValidatorData {
  key: string;
  validator: ValidatorFn;
}

@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  key = 0;

  public getQuestionsFromProperties(properties: ClassProperty<any>[], idPrefix?: string): QuestionBase<any>[] {
    let questions: QuestionBase<any>[] = [];
    questions = this.createQuestionsFromProperties(properties, idPrefix);

    return questions.sort((a, b) => a.order - b.order);
  }

  private createQuestionsFromProperties(templateProperties: ClassProperty<any>[], idPrefix?: string) {
    const questions: QuestionBase<any>[] = [];
    for (const property of templateProperties) {

      const question = this.createQuestionFromProperty(property);


      if (!isNullOrUndefined(idPrefix)) {
        question.key = idPrefix + '.' + property.id;
      } else {
        question.key = property.id;
      }
      question.label = property.name;
      question.order = property.position;

      const validatorData = this.getValidatorData(property.propertyConstraints, property.type, property.required);

      if (!isNullOrUndefined(validatorData)) {
        question.validators = validatorData.validators;
        question.required = validatorData.required;
        question.messages = validatorData.messages;
      }
      questions.push(question);
    }
    return questions;
  }

  private createQuestionFromProperty(property: ClassProperty<any>): QuestionBase<any> {
    let question;
    if (property.type === PropertyType.TEXT) {
      if (isNullOrUndefined(property.allowedValues) || property.allowedValues.length <= 0) {

        question = new TextboxQuestion({
          value: ClassProperty.getDefaultValue(property),
        });

      } else {

        if (property.multiple) {
          question = new DropdownMultipleQuestion({
            // values: this.setKeys(property.defaultValues),
            // options: this.setListValues(property.allowedValues),
            values: property.defaultValues,
            options: this.setAsListValues(property.allowedValues)

          });
        } else {
          question = new DropdownQuestion({
            value: ClassProperty.getDefaultValue(property),
            // options: this.setListValues(property.allowedValues),
            options: property.allowedValues
          });
        }
      }

    } else if (property.type === PropertyType.WHOLE_NUMBER || property.type === PropertyType.FLOAT_NUMBER) {

      if (isNullOrUndefined(property.allowedValues) || property.allowedValues.length <= 0) {
        question = new NumberBoxQuestion({
          value: ClassProperty.getDefaultValue(property),
        });

      } else {

        if (property.multiple) {
          question = new DropdownMultipleQuestion({
            // values: this.setKeys(property.defaultValues),
            // options: this.setListValues(property.allowedValues),
            values: property.defaultValues,
            options: property.allowedValues

          });
        } else {

          question = new NumberDropdownQuestion({
            // options: this.setListValues(property.allowedValues),
            value: ClassProperty.getDefaultValue(property),
            options: property.allowedValues

          });
        }
      }

    } else if (property.type === PropertyType.LONG_TEXT) {
      question = new TextAreaQuestion({
        value: ClassProperty.getDefaultValue(property),
      });

    } else if (property.type === PropertyType.BOOL) {
      question = new SlideToggleQuestion({
        value: ClassProperty.getDefaultValue(property),
      });

    } else if (property.type === PropertyType.ENUM) {
      if (property.multiple) {
        question = new MultipleSelectionEnumQuestion({
          // TODO
          values: property.defaultValues,
          options: property.allowedValues
        });
      } else {
        question = new SingleSelectionEnumQuestion({
          // TODO
          values: property.defaultValues,
          options: property.allowedValues
        });
      }

    } else if (property.type === PropertyType.DATE) {
      question = new DatepickerQuestion({
        value: this.setDateValue(ClassProperty.getDefaultValue(property)),
      });


      // }

      /// TEST MultiProp List
      // else if (property.type === PropertyType.MULTI) {
      //   console.log("Multiple Property found:");
      //   console.log(property);
      //   question = new MultipleQuestion({
      //     subQuestions: this.setQuestions(property.properties),
      //   });
    } else if (property.type === PropertyType.TUPLE) {
      if (!isNullOrUndefined(property.allowedValues) && property.allowedValues.length > 0) {
        question = new TupleDropdownQuestion({
          options: property.allowedValues
        });
      }

    } else {
      console.log('property kind not implemented: ' + property.type);
      question = new GenericQuestion({

      });
    }
    return question;
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


  private setValuesWithoutKeys(values: any[]): any {

    const ret: any[] = [];
    if (!isNullOrUndefined(values)) {
      for (let i = 0; i < values.length; i++) {
        ret.push(values[i].value);
      }
    }
    return ret;
  }


  private setDateValue(value: any) {
    if (!isNullOrUndefined(value)) {
      return new Date(value);
    } else {
      return undefined;
    }
  }

  private setDateValueArr(value: Date): Date[] {
    const ret: Date[] = [];
    ret.push(this.setDateValue(value));
    return ret;
  }

  private setKeys(values: any[]): any {

    const ret: string[] = [];
    if (!isNullOrUndefined(values)) {
      for (const val of values) {
        ret.push(val);
      }
    }
    return ret;
  }


  private getValidatorData(propertyConstraints: PropertyConstraint<any>[], propertyType: PropertyType, required: boolean): ValidatorData {
    const validators: ValidatorFn[] = [];
    const messages: Map<string, string> = new Map<string, string>();

    if (!isNullOrUndefined(propertyConstraints)) {
      for (const constraint of propertyConstraints) {

        const singleValidatorData = this.convertRuleToValidator(constraint, propertyType);

        if (!isNullOrUndefined(singleValidatorData)) {
          validators.push(singleValidatorData.validator);
          messages.set(singleValidatorData.key, constraint.message);

        } else {
          console.log('undefined - done nothing - continue');
        }

        if (required) {
          validators.push(Validators.required);
        }
      }
      const ret = { validators: validators, messages: messages, required: required };
      return ret;

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
