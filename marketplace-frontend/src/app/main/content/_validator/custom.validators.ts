import { ValidatorFn, AbstractControl, ValidationErrors, FormGroup } from "@angular/forms";
import { isNullOrUndefined } from "util";
import { QuestionBase } from "../_model/dynamic-forms/questions";

// export function equalsValidator(value: number): ValidatorFn {
//     return (control: AbstractControl): {[key: string]: any} | null => {
//         return value != control.value ? {'equals': {value: control.value}}  : null
//     };
// }

// export function greaterValidator(value: number): ValidatorFn {
//     return (control: AbstractControl): {[key: string]: any} | null => {
//         return value <= control.value ? {'greater': {value: control.value}} : null
//     };
// }

// export function lessValidator(value: number): ValidatorFn {
//     return (control: AbstractControl): {[key: string]: any} | null => {
//         return value >= control.value ? {'less': {value: control.value}} : null
//     };
// }

/**
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that the FormControl contains a another property with the key specified * 
 * 
 * @returns An error map with the `requiredotherproperty` property if the validation check fails, otherwise `null`.
 */
export function requiredOtherProperty(keyOther: string): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
    
        const containsKey = isNullOrUndefined(control.get(keyOther));
        
        return !containsKey ? {'requiredotherproperty': {value: true}} : null;
    };
}

/**
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that a value of the current property to be less than another property with the key in keyOther.
 * Based on the "min" validator.
 * 
 * @returns An error map with the `minother` property if the validation check fails, otherwise `null`.
 */
export function minDate(minQuestion: QuestionBase<Date>): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(minQuestion)) {
            return null;
        }
        // console.log("11");

        
        // console.log("22");

        const value = control.value;
        const minValue = minQuestion.value;
        

        // if (value !instanceof Date) {
        //     console.log("not instance of Date") 
        //     console.log(value);
        //     console.log(min);
        //     return null;
        // }


        // Controls with NaN values after parsing should be treated as not having a
        // minimum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
        return !isNaN(value) && value < minValue ? {'mindate': {'mindate': minValue, 'actual': control.value}} : null;
    };
}

/**
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that a value of the current property to be larger than another property with the key in keyOther
 * based on the "max" validator
 * 
 * @returns An error map with the `maxother` property if the validation check fails, otherwise `null`.
 */
export function maxOther(keyOther: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(keyOther)) {
            return null;
        }

        const otherValue = parseFloat(control.get(keyOther).value);
        
        if (isEmptyInputValue(otherValue) || isEmptyInputValue(control.value)) {
            return null;  // don't validate empty values to allow optional controls
        }

        const value = parseFloat(control.value);
        // Controls with NaN values after parsing should be treated as not having a
        // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
        return !isNaN(value) && value > otherValue ? {'maxother': {'maxother': otherValue, 'actual': control.value}} : null;
    };
}



function isEmptyInputValue(value: any): boolean {
    // we don't check for string here so it also works with arrays
    return value == null || value.length === 0;
  }