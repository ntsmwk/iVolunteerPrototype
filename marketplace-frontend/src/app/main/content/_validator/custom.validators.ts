// import { ValidatorFn, AbstractControl, ValidationErrors, FormGroup, FormControl } from '@angular/forms';
// import { isNullOrUndefined } from 'util';
// import { DynamicFormItemBase } from '../_model/dynamic-forms/item';


// /**
//  * @param keyThis - Key to the base property
//  * @param keyOther - Key of the other property
//  * 
//  * @description Validator that requires that if the value of the "base" property is filled in, 
//  *              then the value of the "other" property has to be filled in as well.
//  * 
//  * @returns An error map with the `requiredother` property containing @param keyThis and @param keyOther if the validation check fails, otherwise `null`.
//  */
// export function requiredOther(keyThis: string, keyOther: string): ValidatorFn {
//     return (control: AbstractControl): { [key: string]: any } | null => {

//         const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
//         const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

//         if (!containsKeyThis || !containsKeyOther) {
//             console.log('at least one key is not avaiable');
//             return null;
//         }

//         // if (!containsKeyThis && !containsKeyOther) {
//         //     console.log("keys_null");
//         //     return null;
//         // }

//         let displayError = false;

//         if (!isEmptyInputValue(control.get(keyThis).value) && isEmptyInputValue(control.get(keyOther).value)) {
//             control.get(keyOther).setErrors({ 'empty': true });
//             displayError = true;
//         }

//         if (!isEmptyInputValue(control.get(keyThis).value) && !isEmptyInputValue(control.get(keyOther).value)) {

//             displayError = false;
//         }

//         // return !isEmptyInputValue(control.get(keyThis).value) && isEmptyInputValue(control.get(keyOther).value) ? {'requiredother': {keyThis: keyThis, keyOther: keyOther}} : null;
//         return displayError ? { 'requiredother': { 'keyThis': keyThis, 'keyOther': keyOther } } : null;

//     };
// }

// /**
//  * TODO
//  * @param keyOther - Key of the other property
//  * 
//  * @description Validator that requires that a value of the current property to be less than another property with the key in keyOther.
//  * Based on the "min" validator.
//  * 
//  * @returns An error map with the `minother` property if the validation check fails, otherwise `null`.
//  */
// export function minDate(minQuestion: DynamicFormItemBase<Date>): ValidatorFn {
//     return (control: AbstractControl): ValidationErrors | null => {

//         if (isNullOrUndefined(minQuestion)) {
//             return null;
//         }

//         const value = control.value;
//         const minValue = minQuestion.value;

//         // Controls with NaN values after parsing should be treated as not having a
//         // minimum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
//         return !isNaN(value) && value < minValue ? { 'mindate': { 'mindate': minValue, 'actual': control.value } } : null;
//     };
// }

// /**
//  * @param keyThis - Key to the base property
//  * @param keyOther - Key of the other property
//  * 
//  * @description Validator that requires that a value of the base property to be larger than the one of the other property.
//  *              based on the "max" validator
//  * 
//  * @returns An error map with the `maxother` property containing @param keyOther, @param valueOther, as well as @param keyThis, @param valueThis 
//  *          if the validation check fails, otherwise `null`.
//  */
// export function maxOther(keyThis: string, keyOther: string): ValidatorFn {
//     return (control: AbstractControl): ValidationErrors | null => {

//         const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
//         const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

//         if (!containsKeyThis || !containsKeyOther) {
//             console.log('at least one key is not avaiable');
//             return null;
//         }

//         const thisValue = parseFloat(control.get(keyThis).value);
//         const otherValue = parseFloat(control.get(keyOther).value);

//         if (isEmptyInputValue(otherValue) || isEmptyInputValue(thisValue)) {
//             return null;  // don't validate empty values to allow optional controls
//         }


//         let displayError = false;
//         // Controls with NaN values after parsing should be treated as not having a
//         // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
//         if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue > thisValue) {

//             control.get(keyOther).setErrors({ 'incorrect_max': true });
//             control.get(keyThis).setErrors({ 'incorrect_max': true });
//             displayError = true;
//         } else {

//             if (control.get(keyOther).hasError('incorrect_max')) {
//                 delete control.get(keyOther).errors['incorrect_max'];
//                 control.get(keyOther).updateValueAndValidity();
//             }

//             if (control.get(keyThis).hasError('incorrect_max')) {
//                 delete control.get(keyThis).errors['incorrect_max'];
//                 control.get(keyThis).updateValueAndValidity();
//             }

//             displayError = false;
//         }

//         // return !isNaN(thisValue) && !isNaN(otherValue) && otherValue > thisValue ? {'maxother': {'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis}} : null;
//         return displayError ? { 'maxother': { 'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis } } : null;

//     };
// }

// /**
//  * @param keyThis - Key to the base property
//  * @param keyOther - Key of the other property
//  * 
//  * @description Validator that requires that a value of the base property to be smaller than the one of the other property.
//  *              based on the "min" validator
//  * 
//  * @returns An error map with the `minother` property containing @param keyOther, @param valueOther, as well as @param keyThis, @param valueThis 
//  *          if the validation check fails, otherwise `null`.
//  */
// export function minOther(keyThis: string, keyOther: string): ValidatorFn {
//     return (control: AbstractControl): ValidationErrors | null => {

//         const containsKeyThis = !isNullOrUndefined(control.get(keyThis));
//         const containsKeyOther = !isNullOrUndefined(control.get(keyOther));

//         if (!containsKeyThis || !containsKeyOther) {
//             console.log('at least one key is not avaiable');
//             return null;
//         }

//         const thisValue = parseFloat(control.get(keyThis).value);
//         const otherValue = parseFloat(control.get(keyOther).value);

//         if (isEmptyInputValue(otherValue) || isEmptyInputValue(thisValue)) {
//             return null;  // don't validate empty values to allow optional controls
//         }

//         let displayError = false;
//         // Controls with NaN values after parsing should be treated as not having a
//         // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
//         if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue < thisValue) {
//             control.get(keyOther).setErrors({ 'incorrect_min': true });
//             control.get(keyThis).setErrors({ 'incorrect_min': true });
//             displayError = true;
//         } else {

//             if (control.get(keyOther).hasError('incorrect_min')) {
//                 delete control.get(keyOther).errors['incorrect_min'];
//                 control.get(keyOther).updateValueAndValidity();
//             }

//             if (control.get(keyThis).hasError('incorrect_min')) {
//                 delete control.get(keyThis).errors['incorrect_min'];
//                 control.get(keyThis).updateValueAndValidity();
//             }
//             displayError = false;
//         }

//         // if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue >= thisValue) {

//         //     console.log("inside");
//         //     if (control.get(keyOther).hasError('incorrect_min')) {

//         //         console.log(keyOther + " has incorrect_min - deleting");
//         //         delete control.get(keyOther).errors['incorrect_min'];
//         //     }

//         //     displayError = false;
//         // }

//         // return !isNaN(thisValue) && !isNaN(otherValue) && otherValue < thisValue ? {'minother': {'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis}} : null;
//         return displayError ? { 'minother': { 'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis } } : null;

//     };
// }

// export function maxOtherNew(form: FormGroup, keyThis: string, mode: string): ValidatorFn {
//     return (control: AbstractControl): ValidationErrors | null => {

//         const thisValue = form.value[keyThis];

//         let otherValue: number;
//         if (mode === 'aussen') {
//             const choices: number[] = [];

//             if (!isNullOrUndefined(form.value['21'])) {
//                 choices.push(form.value['21']);
//             }
//             if (!isNullOrUndefined(form.value['31'])) {
//                 choices.push(form.value['31']);
//             }
//             if (!isNullOrUndefined(form.value['41'])) {
//                 choices.push(form.value['41']);
//             }
//             if (!isNullOrUndefined(form.value['51'])) {
//                 choices.push(form.value['51']);
//             }
//             if (!isNullOrUndefined(form.value['61'])) {
//                 choices.push(form.value['61']);
//             }
//             if (!isNullOrUndefined(form.value['81'])) {
//                 choices.push(form.value['81']);
//             }

//             otherValue = Math.max(...choices);
//             // console.log(choices);
//             // console.log(otherValue);
//         } else if (mode === 'innen') {

//             const choices: number[] = [];

//             if (!isNullOrUndefined(form.value['20'])) {
//                 choices.push(form.value['20']);
//             }
//             if (!isNullOrUndefined(form.value['30'])) {
//                 choices.push(form.value['30']);
//             }
//             if (!isNullOrUndefined(form.value['40'])) {
//                 choices.push(form.value['40']);
//             }
//             if (!isNullOrUndefined(form.value['50'])) {
//                 choices.push(form.value['50']);
//             }
//             if (!isNullOrUndefined(form.value['60'])) {
//                 choices.push(form.value['60']);
//             }

//             if (!isNullOrUndefined(form.value['80'])) {
//                 for (const v of form.controls['80'].value) {
//                     if (!isNullOrUndefined(v)) {
//                         choices.push(v);
//                     }
//                 }
//             }

//             // console.log(choices);
//             // console.log(otherValue);
//             otherValue = Math.max(...choices);
//         } else {
//             return null;
//         }


//         if (isEmptyInputValue(thisValue) || isEmptyInputValue(otherValue)) {
//             return null;  // don't validate empty values to allow optional controls
//         }


//         let displayError = false;
//         // Controls with NaN values after parsing should be treated as not having a
//         // maximum, per the HTML forms spec: https://www.w3.org/TR/html5/forms.html#attr-input-min
//         if (!isNaN(thisValue) && !isNaN(otherValue) && otherValue < thisValue) {

//             // console.log("error")
//             control.get(keyThis).setErrors({ 'incorrect_max': true });
//             displayError = true;
//         } else {
//             // console.log("noerror")
//             if (control.get(keyThis).hasError('incorrect_max')) {
//                 delete control.get(keyThis).errors['incorrect_max'];
//                 control.get(keyThis).updateValueAndValidity();


//             }

//             displayError = false;
//         }

//         // return !isNaN(thisValue) && !isNaN(otherValue) && otherValue > thisValue ? {'maxother': {'valueOther': otherValue, 'keyOther': keyOther, 'valueThis': thisValue, 'keyThis': keyThis}} : null;
//         return displayError ? { 'maxothernew': { 'valueOther': otherValue, 'valueThis': thisValue, 'keyThis': keyThis } } : null;

//     };
// }


// // taken from angular validators file
// function isEmptyInputValue(value: any): boolean {
//     // we don't check for string here so it also works with arrays
//     return value == null || value.length === 0;
// }

