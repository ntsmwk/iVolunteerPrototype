// import { Injectable }       from '@angular/core';

// import { DropdownQuestion, QuestionBase, TextboxQuestion, NumberBoxQuestion, NumberDropdownQuestion, TextAreaQuestion, 
//   SlideToggleQuestion, RadioButtonQuestion, DropdownMultipleQuestion, DatepickerQuestion } from '../_model/dynamic-forms/questions';

//   import { Property, PropertyKind, ListValue, Rule, RuleKind } from '../_model/properties/Property';
// import { isNullOrUndefined, isNull } from 'util';
// import { Validators, ValidatorFn } from '@angular/forms';
// // import { equalsValidator, greaterValidator, lessValidator } from '../_validator/custom.validators';

// @Injectable({
//   // we declare that this service should be created
//   // by the root application injector.
//   providedIn: 'root',
// })
// export class QuestionService {

//   getQuestionsFromProperties(properties: Property<any>[]): any[] {
//     let questions: QuestionBase<any>[] = [];
//     console.log("Question Service called");

//     for (let property of properties) {
//       console.log("Kind: " + property.kind + " Name: " + property.name);
//       if (property.kind === PropertyKind.TEXT) {
//         if (isNullOrUndefined(property.legalValues)) {
//           questions.push(
//           new TextboxQuestion( {
//             key: property.id,
//             label: property.name,
//             value: property.value,
//             order: properties.indexOf(property), 
//             validators: this.setValidators(property.rules),
//             required: this.setRequired(property.rules)

//           }));
//         } else {
//           questions.push(
//             new DropdownQuestion( {
//               key: property.id,
//               label: property.name,
//               value: property.value,
//               options: this.setValues(property.legalValues),
//               order: properties.indexOf(property),
//               validators: this.setValidators(property.rules),
//               required: this.setRequired(property.rules)
//           }));
//         }
//       } else if (property.kind === PropertyKind.WHOLE_NUMBER || property.kind === PropertyKind.FLOAT_NUMBER) {
//         if (isNullOrUndefined(property.legalValues)) {
//           questions.push(
//             new NumberBoxQuestion( {
//               key: property.id,
//               label: property.name,
//               value: property.value,
//               order: properties.indexOf(property),
//               validators: this.setValidators(property.rules),
//               required: this.setRequired(property.rules)
//           }));
//         } else {
//           questions.push(
//             new NumberDropdownQuestion( {
//               key: property.id,
//               label: property.name,
//               value: property.value,
//               options: this.setValues(property.legalValues),
//               order: properties.indexOf(property),
//               validators: this.setValidators(property.rules),
//               required: this.setRequired(property.rules)
//             }));
//         }
//       } else if (property.kind === PropertyKind.LONG_TEXT) {
//         questions.push(
//           new TextAreaQuestion( {
//             key: property.id,
//             label: property.name,
//             value: property.value,
//             order: properties.indexOf(property),
//             validators: this.setValidators(property.rules),
//             required: this.setRequired(property.rules)
//         }));
//       } else if (property.kind === PropertyKind.BOOL) {
//         questions.push(
//           new SlideToggleQuestion( {
//             key: property.id,
//             label: property.name,
//             value: property.value,
//             order: properties.indexOf(property),
//             validators: this.setValidators(property.rules),
//             required: this.setRequired(property.rules)
//         }));
//       } else if (property.kind === PropertyKind.LIST) {
       
//         questions.push(new DropdownMultipleQuestion( {
//           key: property.id,
//           label: property.name,
//           values: this.setKeys(property.values),
//           value: '',
//           options: this.setValues(property.legalValues),
//           order: properties.indexOf(property),
//           validators: this.setValidators(property.rules),
//           required: this.setRequired(property.rules)
//         }));

//       } else if (property.kind === PropertyKind.DATE) {
//         questions.push(
//           new DatepickerQuestion( {
//             key: property.id,
//             label: property.name,
//             value: new Date(property.value) || '',
//             order: properties.indexOf(property),
//             validators: this.setValidators(property.rules),
//             required: this.setRequired(property.rules)
//           }));
          
//       }

//     }
    
//     console.log("-->done with question setup");

//     return questions.sort((a, b) => a.order - b.order);
//   }

//   private setValues(values: ListValue<any>[]) :any {
//     let ret: {key: string, value: string}[] = [];
//     if (!isNullOrUndefined(values)) {
//       for (let i = 0; i < values.length; i++) {
//         ret.push({key: values[i].id, value: values[i].value});
//         //console.log("SETVALUES:  o-" + ret[i] + "  key-" + ret[i].key + " val-" + ret[i].value)
//       }
//     }
//     return ret;
//   }

//   private setKeys(values: ListValue<any>[]) :any {
//     let ret: string[] = [];
//     if (!isNullOrUndefined(values)) {
//       for (let val of values) {
//         ret.push(val.id);
//       }
//     }
//     return ret;
//   }

//   private setValidators(rules: Rule[]): ValidatorFn[] {
//     let validators: ValidatorFn[] = [];
//     console.log("set Validators called");

//     //TODO iterate through rules of question and add corresponding validator
//     if (!isNullOrUndefined(rules)) {
//       for (let rule of rules) {
//         console.log("processing rule: " + rule.id);
//         validators.push(this.convertRuleToValidator(rule));
//       }
//     }
//     return validators;
//   }

//   private convertRuleToValidator(rule: Rule): ValidatorFn {

//     let validator: ValidatorFn;

//     switch (rule.kind) {
//       case RuleKind.REQUIRED:
//         console.log("adding requried Validator");
//         validator = Validators.required;
//         break;

//       case RuleKind.REQUIRED_TRUE:
//         console.log("adding requried_true Validator");
//         validator = Validators.requiredTrue;
//         break;

//       case RuleKind.REGEX_PATTERN:
//         console.log("adding regex_pattern Validator" + rule.regex);
//         validator = Validators.pattern(rule.regex);
//         break;

//       case RuleKind.MAX_LENGTH:
//         console.log("adding max_length Validator" + rule.value);
//         validator = Validators.maxLength(rule.value);
//         break;

//       case RuleKind.MIN_LENGTH:
//         console.log("adding min_length Validator: " + rule.value);
//         validator = Validators.minLength(rule.value);
//         break;

//       case RuleKind.MAX:
//         console.log("adding max Validator" + rule.value);
//         validator = Validators.max(rule.value);
//         break;

//       case RuleKind.MIN:
//         console.log("adding min Validator" + rule.value);
//         validator = Validators.min(rule.value);
//         break;

//       // case RuleKind.GREATER:
//       //   console.log("adding greater Validator" + rule.value);
//       //   validator = greaterValidator(rule.value);
//       //   break;

//       // case RuleKind.LESS:
//       //   console.log("adding less Validator" + rule.value);
//       //   validator = lessValidator(rule.value);
//       //   break;

//       // case RuleKind.EQUAL:
//       //   console.log("adding equals Validator" + rule.value);
//       //   validator = equalsValidator(rule.value);
//       //   break;

    
//       default:
//         console.log("VALIDATORS: switch-default should not happen");
//         break;
//     }

//     return validator;

//   }

//   private setRequired (rules: Rule[]) {

//     if (isNullOrUndefined(rules)) {
//       return false;
//     } else {
      
//       for (let rule of rules) {
//         if (rule.kind === RuleKind.REQUIRED || rule.kind === RuleKind.REQUIRED_TRUE) {
//           return true;   
//         }
//       }
//       return false;
//     }

//   }

// }
