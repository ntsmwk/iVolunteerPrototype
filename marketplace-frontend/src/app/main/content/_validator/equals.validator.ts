
import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { isNullOrUndefined } from 'util';


// validation function
export function equals(control1: AbstractControl, control2: AbstractControl): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        if (isNullOrUndefined(control1) || isNullOrUndefined(control2)) {
            return null;
        }

        const string1 = control1.value;
        const string2 = control2.value;


        if (string1 === '' || string2 === '') {
            return null;
        }

        if (isNullOrUndefined(string1) || isNullOrUndefined(string2)) {
            return null;
        }

        return string1 !== string2 ? { 'equals': { 'equals': false, 'string1': string1, 'string2': string2 } } : null;
    };
}




