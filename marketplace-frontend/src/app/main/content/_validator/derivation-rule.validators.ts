import { FormArray, FormControl, FormGroup, ValidationErrors } from '@angular/forms';

export class DerivationRuleValidators {

    static ruleValidationMessages = {
        'name': [
          { type: 'required', message: 'Regelname ist erforderlich' },
          { type: 'minlength', message: 'Regelname muss mindestens 5 Zeichen lang sein' },
          { type: 'maxlength', message: 'Username cannot be more than 25 characters long' },
          { type: 'pattern', message: 'Your username must contain only numbers and letters' },
          { type: 'uniqueRuleName', message: 'Regelname existiert bereits.' }
        ],
        'classDefinition': [
            { type: 'required', message: 'Es wurde kein Freiwilligenpasseintrag gewählt' },
          ],
        'property': [
          { type: 'required', message: 'Kein Property ausgewählt' },
        //  { type: 'pattern', message: 'Enter a valid email' }
        ],
        'comparisonOperator': [
          { type: 'required', message: 'Vergleichsoperator wurde nicht gewählt' },
       //   { type: 'areEqual', message: 'Password mismatch' }
        ],
        'aggregationOperator': [
          { type: 'required', message: 'Aggregationsoperator wurde nicht gewählt' },
       //   { type: 'areEqual', message: 'Password mismatch' }
        ],
        'password': [
          { type: 'required', message: 'Password is required' },
          { type: 'minlength', message: 'Password must be at least 5 characters long' },
          { type: 'pattern', message: 'Your password must contain at least one uppercase, one lowercase, and one number' }
        ],
        'terms': [
          { type: 'pattern', message: 'You must accept terms and conditions' }
        ],
        'value': [
          { type: 'required', message: 'Es wurde kein Wert erfasst' }
        ],
        'operator': [
          { type: 'pattern', message: 'Kein Operator gewählt' }
        ]
    }
 
 static birthYear(c: FormControl): ValidationErrors {
   const numValue = Number(c.value);
   const currentYear = new Date().getFullYear();
   const minYear = currentYear - 85;
   const maxYear = currentYear - 18;
   const isValid = !isNaN(numValue) && numValue >= minYear && numValue <= maxYear;
   const message = {
     'years': {
       'message': 'The year must be a valid number between ' + minYear + ' and ' + maxYear
     }
   };
   return isValid ? null : message;
 }

 static uniqueRuleName(c: FormControl): Promise<ValidationErrors> {
   const message = {
     'uniqueName': {
       'message': 'The name is not unique'
     }
   };
 
   // XXX to do
   return new Promise(resolve => {
     setTimeout(() => {
       resolve(c.value === 'Existing' ? message : null);
     }, 1000);
   });
 }

}