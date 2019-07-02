import { ValidatorFn, AbstractControl, ValidationErrors, FormGroup } from "@angular/forms";
import { isNullOrUndefined } from "util";
import { QuestionBase } from "../_model/dynamic-forms/questions";


/**
 * @param keyThis - Key to the base property
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that if the value of the "base" property is filled in, 
 *              then the value of the "other" property has to be filled in as well.
 * 
 * @returns An error map with the `requiredother` property containing @param keyThis and @param keyOther if the validation check fails, otherwise `null`.
 */
export function requiredOther(keyThis: string, keyOther: string): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
    
        const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
        const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

        if (!containsKeyThis || !containsKeyOther) {
            return null;
        }

        let displayError = false;
       
        if (!isEmptyInputValue(control.get(keyThis).value) && isEmptyInputValue(control.get(keyOther).value)) {
            control.get(keyOther).setErrors({'empty': true});
            displayError = true;
        }

        if (!isEmptyInputValue(control.get(keyThis).value) && !isEmptyInputValue(control.get(keyOther).value)) {
 
            displayError = false;
        }

        return displayError ? {'requiredother': {'keyThis': keyThis, 'keyOther': keyOther}} : null;

    };
}

/**
 * TODO
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

        const value = control.value;
        const minValue = minQuestion.value;

        // Controls with NaN values after parsing should be treated as not having a
        // minimum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
        return !isNaN(value) && value < minValue ? {'mindate': {'mindate': minValue, 'actual': control.value}} : null;
    };
}

/**
 * @param keyThis - Key to the base property
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that a value of the base property to be larger than the one of the other property.
 *              based on the "max" validator
 * 
 * @returns An error map with the `maxother` property containing @param keyOther, @param valueOther, as well as @param keyThis, @param valueThis 
 *          if the validation check fails, otherwise `null`.
 */
export function maxOther(keyThis: string, keyOther: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
        const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

        if (!containsKeyThis || !containsKeyOther) {
            return null;
        }

        const thisValue = parseFloat(control.get(keyThis).value);
        const otherValue = parseFloat(control.get(keyOther).value);
        
        if (isEmptyInputValue(otherValue) || isEmptyInputValue(thisValue)) {
            return null;  // don't validate empty values to allow optional controls
        }


        let displayError = false;
        // Controls with NaN values after parsing should be treated as not having a
        // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
        if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue > thisValue) {

            control.get(keyOther).setErrors({'incorrect_max': true});
            control.get(keyThis).setErrors({'incorrect_max': true});
            displayError = true;
        } else {

            if (control.get(keyOther).hasError('incorrect_max')) {
                delete control.get(keyOther).errors['incorrect_max'];
                control.get(keyOther).updateValueAndValidity();
            }

            if (control.get(keyThis).hasError('incorrect_max')) {
                delete control.get(keyThis).errors['incorrect_max'];
                control.get(keyThis).updateValueAndValidity();
            }

            displayError = false;
        }

        return displayError ? {'maxother': {'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis}} : null;

    };
}

/**
 * @param keyThis - Key to the base property
 * @param keyOther - Key of the other property
 * 
 * @description Validator that requires that a value of the base property to be smaller than the one of the other property.
 *              based on the "min" validator
 * 
 * @returns An error map with the `minother` property containing @param keyOther, @param valueOther, as well as @param keyThis, @param valueThis 
 *          if the validation check fails, otherwise `null`.
 */
export function minOther(keyThis: string, keyOther: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
        const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

        if (!containsKeyThis || !containsKeyOther) {
            return null;
        }

        const thisValue = parseFloat(control.get(keyThis).value);
        const otherValue = parseFloat(control.get(keyOther).value);
        
        if (isEmptyInputValue(otherValue) || isEmptyInputValue(thisValue)) {
            return null;  // don't validate empty values to allow optional controls
        }

        let displayError = false;
        // Controls with NaN values after parsing should be treated as not having a
        // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
        if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue < thisValue) {
            control.get(keyOther).setErrors({'incorrect_min': true});
            control.get(keyThis).setErrors({'incorrect_min': true});
            displayError = true;
        } else {

            if (control.get(keyOther).hasError('incorrect_min')) {               
                delete control.get(keyOther).errors['incorrect_min'];
                control.get(keyOther).updateValueAndValidity();
            }

            if (control.get(keyThis).hasError('incorrect_min')) {      
                delete control.get(keyThis).errors['incorrect_min'];
                control.get(keyThis).updateValueAndValidity();
            }
            displayError = false;
        }

        return displayError ? {'minother': {'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis}} : null;

    };
}


//taken from angular validators file
function isEmptyInputValue(value: any): boolean {
    // we don't check for string here so it also works with arrays
    return value == null || value.length === 0;
}

