import { Injectable }       from '@angular/core';

import { DropdownQuestion, QuestionBase, TextboxQuestion, NumberBoxQuestion, NumberDropdownQuestion, TextAreaQuestion, SlideToggleQuestion, RadioButtonQuestion, DropdownMultipleQuestion, DatepickerQuestion } from '../_model/dynamic-forms/questions';
import { Property, PropertyKind, ListValue } from '../_model/properties/Property';
import { isNullOrUndefined, isNull } from 'util';

@Injectable({
  // we declare that this service should be created
  // by the root application injector.
  providedIn: 'root',
})
export class QuestionService {

  getQuestionsFromProperties(properties: Property<any>[]): any[] {
    let questions: QuestionBase<any>[] = [];
    console.log("Question Service called");
    for (let property of properties) {
      console.log("Kind: " + property.kind);
      if (property.kind === PropertyKind.TEXT) {
        if (isNullOrUndefined(property.legalValues)) {
          questions.push(
          new TextboxQuestion( {
            key: property.id,
            label: property.name,
            value: property.value,
            required: true,
            order: properties.indexOf(property)

          }));
        } else {
          questions.push(
            new DropdownQuestion( {
              key: property.id,
              label: property.name,
              value: property.value,
              options: this.setValues(property.legalValues),
              required: true,
              order: properties.indexOf(property)
          }));
        }
      } else if (property.kind === PropertyKind.WHOLE_NUMBER || property.kind === PropertyKind.FLOAT_NUMBER) {
        if (isNullOrUndefined(property.legalValues)) {
          questions.push(
            new NumberBoxQuestion( {
              key: property.id,
              label: property.name,
              value: property.value,
              required: true,
              order: properties.indexOf(property)
          }));
        } else {
          questions.push(
            new NumberDropdownQuestion( {
              key: property.id,
              label: property.name,
              value: property.value,
              options: this.setValues(property.legalValues),
              required: true,
              order: properties.indexOf(property)
            }));
        }
      } else if (property.kind === PropertyKind.LONG_TEXT) {
        questions.push(
          new TextAreaQuestion( {
            key: property.id,
            label: property.name,
            value: property.value,
            required: true,
            order: properties.indexOf(property)
        }));
      } else if (property.kind === PropertyKind.BOOL) {
        questions.push(
          new SlideToggleQuestion( {
            key: property.id,
            label: property.name,
            value: property.value,
            required: true,
            order: properties.indexOf(property)
        }));
      } else if (property.kind === PropertyKind.LIST) {
       
        questions.push(new DropdownMultipleQuestion( {
          key: property.id,
          label: property.name,
          values: this.setKeys(property.values),
          value: '',
          options: this.setValues(property.legalValues),
          // selection: property.legalValues.filter((str: string) => { 
          //   if (!isNullOrUndefined(property.values)) {
          //     console.log("entered");
          //     return property.values.find( (astr: string) => astr === str)
          //   };
          //   return null;
           
          // }),
          required: true,
          order: properties.indexOf(property)
        }));

        




       

      } else if (property.kind === PropertyKind.DATE) {
        questions.push(
          new DatepickerQuestion( {
            key: property.id,
            label: property.name,
            value: new Date(property.value) || '',
            required: true,
            order: properties.indexOf(property)
          }));
          
      }

    }
    

    return questions.sort((a, b) => a.order - b.order);
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

  private setKeys(values: ListValue<any>[]) :any {
    let ret: string[] = [];
    if (!isNullOrUndefined(values)) {
      for (let val of values) {
        ret.push(val.id);
      }
    }
    return ret;
  }



  // TODO: get from a remote source of question metadata
  // TODO: make asynchronous
  getQuestions() {
    let questions: QuestionBase<any>[] = [

      new DropdownQuestion({
        key: 'brave',
        label: 'Bravery Rating',
        options: [
          {key: 'solid',  value: 'Solid'},
          {key: 'great',  value: 'Great'},
          {key: 'good',   value: 'Good'},
          {key: 'unproven', value: 'Unproven'}
        ],
        order: 3
      }),

      new TextboxQuestion({
        key: 'firstName',
        label: 'First name',
        value: 'Bombasto',
        required: true,
        order: 1
      }),

      new TextboxQuestion({
        key: 'emailAddress',
        label: 'Email',
        type: 'email',
        order: 2
      })
    ];

    return questions.sort((a, b) => a.order - b.order);
  }
}