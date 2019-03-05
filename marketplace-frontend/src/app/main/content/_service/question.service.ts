import { Injectable }       from '@angular/core';

import { DropdownQuestion, QuestionBase, TextboxQuestion, NumberBoxQuestion, NumberDropdownQuestion, TextAreaQuestion, 
  SlideToggleQuestion, DropdownMultipleQuestion, DatepickerQuestion } from '../_model/dynamic-forms/questions';

import { Property, PropertyKind, ListValue, Rule, RuleKind } from '../_model/properties/Property';
import { isNullOrUndefined } from 'util';
import { Validators, ValidatorFn } from '@angular/forms';

import { minDate } from "../_validator/custom.validators";

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
  questions: QuestionBase<any>[] = [];

  getQuestionsFromProperties(properties: Property<any>[]): any[] {
    this.questions = []; //reset questions
    let questions = this.questions;
    
    console.log("Question Service called");

    for (let property of properties) {
      
      console.log("Kind: " + property.kind + " Name: " + property.name);
      let question = this.createQuestion(property);

      question.key = property.id;
      question.label = property.name;
      question.order = properties.indexOf(property);

      
      questions.push(question);
    } 
    console.log("-->done with question setup");
   
    // 2nd pass for validators
    for (let i=0; i<properties.length; i++) {

      let validatorData = this.getValidatorData(properties[i].rules, properties[i].kind);

      if (!isNullOrUndefined(validatorData)) {
        let question = questions[i];
        //question = undefined;
        
        // if ID is not correct, find the correct one - should never happen
        if (!isNullOrUndefined(question) && question.key != properties[i].id) {
          console.log("id not correct - attemting to find the correct one - should never happen... it did though :(");
          question = questions.find((q: QuestionBase<any>) => {
            return q.key == properties[i].id;
          });
        }
        if (!isNullOrUndefined(question)) {
          console.log("found id");
          question.validators = validatorData.validators;
          question.required = validatorData.required;
          question.messages = validatorData.messages;
        }
       
      }
    }
   
    // console.log("Properties        Questions")
    // for (let i=0; i<properties.length; i++) {
    //   console.log(properties[i].id + "      " + questions[i].key);

    //   if (questions[i].key == "end_date") {

    //     let p = properties.find((p: Property<any>) => {
    //       return p.id == "starting_date"
            
    //     });

    //     let q = questions.find((q: QuestionBase<any>) => {
    //       return q.key == "starting_date"
            
    //     });


    //     console.log(p);
    //     console.log(p.value);
    //     console.log("======");
        
    //     console.log(questions[i]);
    //     console.log(q);
        
    //     if (isNullOrUndefined(questions[i].validators)) {
    //       questions[i].validators = [];
    //     }
        
    //     console.log("=====");
        
    //     questions[i].validators.push(minDate(q.value));
    //     console.log(questions[i]);
        
    //   }
    // }

    /**TODO Inter-Property validators:
     *  Has to be done in a second pass though all the questions (since all questions have to be prepared to be accessible)
     * 
     * Might be better if all validators are processed in one step -> faster overall since less time is spent overall searching though the rules
     * if everything is done at once - restructuring necessary
     * 
     * inter property validators need 
     * -either a reference to a property in the form (not possible with the current setup (at least in this service) - since forms are created after)
     * -or a question/value is passed directly after question setup (which is possible inside this service with a bit of wrangling around)
     * 
     * 
     *  
     * */    


    return questions.sort((a, b) => a.order - b.order);
  }

  private createQuestion(property: Property<any>): QuestionBase<any> {
    let question;
    if (property.kind === PropertyKind.TEXT) {
      if (isNullOrUndefined(property.legalValues)) {
        
        question = new TextboxQuestion( {            
          value: property.value,
        });
      
      } else {
          question = new DropdownQuestion( {
            value: property.value,
            options: this.setValues(property.legalValues),
        });
      }

    } else if (property.kind === PropertyKind.WHOLE_NUMBER || property.kind === PropertyKind.FLOAT_NUMBER) {
      
      if (isNullOrUndefined(property.legalValues)) {
        question = new NumberBoxQuestion({
          value: property.value,  
        });

      } else {
        question = new NumberDropdownQuestion({
          value: property.value,
          options: this.setValues(property.legalValues),
        });
      }

    } else if (property.kind === PropertyKind.LONG_TEXT) {
        question = new TextAreaQuestion({
          value: property.value,   
      });

    } else if (property.kind === PropertyKind.BOOL) {
        question = new SlideToggleQuestion({
          value: property.value,
      });

    } else if (property.kind === PropertyKind.LIST) {
      question = new DropdownMultipleQuestion({
        values: this.setKeys(property.values),
        value: '',
        options: this.setValues(property.legalValues),
      });

    } else if (property.kind === PropertyKind.DATE) {
      question = new DatepickerQuestion({
        value: this.setDateValue(property.value),
      });
      
      
    }

    return question;
  }

  private setValues(values: ListValue<any>[]) :any {
    let ret: {key: string, value: string}[] = [];
    if (!isNullOrUndefined(values)) {
      for (let i = 0; i < values.length; i++) {
        ret.push({key: values[i].id, value: values[i].value});
        //console.log("SETVALUES:  o-" + ret[i] + "  key-" + ret[i].key + " val-" + ret[i].value)
      }
    }
    return ret;
  }

  public setDateValue(value: any) {
    if (!isNullOrUndefined(value)) {
      return new Date(value);
    } else {
      return undefined;
    }
  }

  private setKeys(values: ListValue<any>[]) :any {
    let ret: string[] = [];
    if (!isNullOrUndefined(values)) {
      for (let val of values) {
        ret.push(val.id);
      }
    }
    return ret;
  }

  private getValidatorData(rules: Rule[], propertyKind: PropertyKind): ValidatorData {
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

  private convertRuleToValidator(rule: Rule, propertyKind: PropertyKind): SingleValidatorData {

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
        if (!isNullOrUndefined(rule.data)) {
          if (propertyKind==PropertyKind.DATE) {
            let q = this.questions.find((q: QuestionBase<any>) => {
              return q.key == rule.data;
            });
            if (!isNullOrUndefined(q)) {
              console.log("not null or undefined");
              console.log(q);
              validator = minDate(q);
              key = 'minDate';
            } else {
              console.log("is null or undefined");
              console.log(q);
              return undefined;
            }
            
          }
        } else if (!isNullOrUndefined(rule.value)) {
          validator = Validators.min(rule.value);
          key = 'min';
        } else {
          console.log("should not happen (min Validator)");
        }
        
        break;

      default:
        console.log("VALIDATORS: switch-default should not happen");
        break;
    }
   
    const ret = {key: key, validator: validator};
    return ret;
  }
}
