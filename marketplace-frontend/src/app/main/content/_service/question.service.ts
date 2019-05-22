import { Injectable }       from '@angular/core';

import { DropdownQuestion, QuestionBase, TextboxQuestion, NumberBoxQuestion, NumberDropdownQuestion, TextAreaQuestion, 
  SlideToggleQuestion, DropdownMultipleQuestion, DatepickerQuestion, MultipleQuestion, GenericQuestion } from '../_model/dynamic-forms/questions';

import { Property, PropertyKind, ListEntry, Rule, RuleKind } from '../_model/properties/Property';
import { isNullOrUndefined } from 'util';
import { Validators, ValidatorFn } from '@angular/forms';

import { minDate, maxOther, minOther, requiredOther } from "../_validator/custom.validators";

export interface ValidatorData {
    validators: ValidatorFn[];
    required: boolean;
    messages: Map<string,string>; //Key - Message
}

export interface SingleValidatorData {
  key: string;
  validator: ValidatorFn;
}


@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  //questions: QuestionBase<any>[] = [];
  key: number =  0;

  getQuestionsFromProperties(properties: Property<any>[]): any[] {
    //this.questions = []; //reset questions
    let questions: QuestionBase<any>[] = [];
    
    console.log("Question Service called");
    console.log(properties);

    questions = this.setQuestions(properties);

    console.log(questions);
    console.log("-->done with question setup");

    return questions.sort((a, b) => a.order - b.order);
  }

  private createQuestion(property: Property<any>): QuestionBase<any> {
    let question;
    if (property.kind === PropertyKind.TEXT) {
      if (isNullOrUndefined(property.legalValues)) {
        
        question = new TextboxQuestion( {            
          value: Property.getValue(property),
        });
      
      } else {
          question = new DropdownQuestion( {
            value: Property.getValue(property),
            options: this.setValuesWithoutKeys(property.legalValues),
        });
      }

    } else if (property.kind === PropertyKind.WHOLE_NUMBER || property.kind === PropertyKind.FLOAT_NUMBER) {
      
      if (isNullOrUndefined(property.legalValues)) {
        question = new NumberBoxQuestion({
          value: Property.getValue(property),
        });

      } else {
        question = new NumberDropdownQuestion({
          options: this.setValuesWithoutKeys(property.legalValues),
          value: Property.getValue(property),
        });
      }

    } else if (property.kind === PropertyKind.LONG_TEXT) {
        question = new TextAreaQuestion({
          value: Property.getValue(property),
        });

    } else if (property.kind === PropertyKind.BOOL) {
        question = new SlideToggleQuestion({
          value: Property.getValue(property),
      });

    } else if (property.kind === PropertyKind.LIST) {
      question = new DropdownMultipleQuestion({
        values: this.setKeys(property.values),
        options: this.setListValues(property.legalValues),
      });

    } else if (property.kind === PropertyKind.DATE) {
      console.log("leifnleifn");
      console.log(property);
      question = new DatepickerQuestion({
        value: this.setDateValue(Property.getValue(property)),
      });
      
      
    }

    ///TEST MultiProp List
    else if (property.kind === PropertyKind.MULTIPLE) {
      console.log("Multiple Property found:");
      console.log(property);
      question = new MultipleQuestion({
        subQuestions: this.setQuestions(property.properties),
      });
    } else {
      console.log("property kind not implemented: " + property.kind);
      question = new GenericQuestion({
           
      });

    }

    return question;
  }

  private setListValues(values: ListEntry<any>[]) :any {
    let ret: {key: string, value: any}[] = [];
    if (!isNullOrUndefined(values)) {
      for (let i = 0; i < values.length; i++) {
        ret.push({key: values[i].id, value: values[i].value});
      }
    }
    return ret;
  }

  private setValuesWithoutKeys(values: ListEntry<any>[]) :any {
    let ret: any[] = [];
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
    let ret: Date[] = [];
    ret.push(this.setDateValue(value));
    return ret;
  }


  private setKeys(values: ListEntry<any>[]) :any {
    let ret: string[] = [];
    if (!isNullOrUndefined(values)) {
      for (let val of values) {
        ret.push(val.id);
      }
    }
    return ret;
  }

  private setQuestions(properties: Property<any>[]) {
    let questions: QuestionBase<any>[] = [];
    for (let property of properties) {
      
      console.log("Kind: " + property.kind + " Name: " + property.name);
      let question = this.createQuestion(property);

      question.key = property.id;
      question.label = property.name;
      question.order = properties.indexOf(property);

      //Set Validators

      let validatorData = this.getValidatorData(property.rules, property.kind);

      if (!isNullOrUndefined(validatorData)) {
        question.validators = validatorData.validators;
        question.required = validatorData.required;
        question.messages = validatorData.messages;
      }
      questions.push(question);
    } 
    return questions;
  }

  private getValidatorData(rules: Rule[], propertyKind: PropertyKind /*, questions: QuestionBase<any>[]*/): ValidatorData {
    let validators: ValidatorFn[] = [];
    let messages: Map<string,string> = new Map<string,string>();
    let required: boolean = false;

    if (!isNullOrUndefined(rules)) {
      for (let rule of rules) {
        //console.log("processing rule: " + rule.id);

        let singleValidatorData = this.convertRuleToValidator(rule, propertyKind);
        
        if (!isNullOrUndefined(singleValidatorData)) {
          validators.push(singleValidatorData.validator);
          messages.set(singleValidatorData.key, rule.message);
        
          if (singleValidatorData.key === 'required') {
            required = true;
          }
        } else {
          console.log("undefined - done nothing - continue");
        }

        
      }
      const ret = {validators: validators, messages: messages, required: required}
      return ret;
   
    } else {
      return undefined;
    } 
  }

  private convertRuleToValidator(rule: Rule, propertyKind: PropertyKind/*, questions: QuestionBase<any>[]*/): SingleValidatorData {

    let validator: ValidatorFn;
    let key: string;

    switch (rule.kind) {
      case RuleKind.REQUIRED:
        //console.log("adding requried Validator");
        validator = Validators.required;
        key = 'required';
        break;

      case RuleKind.REQUIRED_TRUE:
        //console.log("adding requried_true Validator");
        validator = Validators.requiredTrue;
        key = 'requiredtrue';
        break;

      case RuleKind.REGEX_PATTERN:
        //console.log("adding regex_pattern Validator" + rule.regex);
        validator = Validators.pattern(rule.data);
        key = 'pattern';
        break;

      case RuleKind.MAX_LENGTH:
        //console.log("adding max_length Validator" + rule.value);
        validator = Validators.maxLength(rule.value);
        key = 'maxlength';
        break;

      case RuleKind.MIN_LENGTH:
        //console.log("adding min_length Validator: " + rule.value);
        validator = Validators.minLength(rule.value);
        key = 'minlength';
        break;

      case RuleKind.MAX:
        //console.log("adding max Validator" + rule.value);
        validator = Validators.max(rule.value);
        key = 'max';
        break;

      case RuleKind.MIN:
        //console.log("adding min Validator" + rule.value);
        validator = Validators.min(rule.value);
        key = 'min';
        break;

      /**
       * MultiProperties Validator
       */
      case RuleKind.REQUIRED_OTHER: 
        console.log("adding required other validator");
        console.log(rule);
        validator = requiredOther(rule.key, rule.keyOther);
        key = 'requiredother';
        break;

      case RuleKind.MAX_OTHER:
        validator = maxOther(rule.key, rule.keyOther);
        key = 'maxother';
        break;

      case RuleKind.MIN_OTHER:
        validator = minOther(rule.key, rule.keyOther);
        key = 'minother';
        break;

      default:
        console.error("VALIDATORS: switch-default should not happen");
        break;
    }
   
    const ret = {key: key, validator: validator};
    console.log("Q");
    console.log(ret);
    return ret;
  }
}
