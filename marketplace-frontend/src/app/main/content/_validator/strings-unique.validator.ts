
import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { isNullOrUndefined } from 'util';


// validation function
export function stringsUnique(string1: string, string2: string): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        if (isNullOrUndefined(string1) || isNullOrUndefined(string2)) {
            return null;
        }
        if (string1 === '' || string2 === '') {
            return null;
        }

        return string1 === string2 ? { 'stringsUnique': { 'stringsUnique': false, 'string1': string1, 'string2': string2 } } : null;
    };
}




