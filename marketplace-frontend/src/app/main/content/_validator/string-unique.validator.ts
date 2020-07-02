import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { isNullOrUndefined } from 'util';


// validation function
export function stringUniqueValidator(strings: string[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(strings)) {
            return null;
        }

        if (isNullOrUndefined(control.value) || control.value === '') {
            return null;
        }

        let item;
        item = strings.find((s: string) => {
            return s.trim().toLocaleLowerCase() === control.value.trim().toLocaleLowerCase();
        });


        return !isNullOrUndefined(item) ? { 'stringunique': { 'stringunique': item, 'actual': control.value } } : null;
    };
}




