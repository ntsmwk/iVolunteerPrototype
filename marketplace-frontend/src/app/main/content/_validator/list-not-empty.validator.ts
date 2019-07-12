import { ValidatorFn, AbstractControl, NG_VALIDATORS, FormControl, Validator, ValidationErrors, FormArray } from "@angular/forms";
import { isNullOrUndefined } from "util";

import { PropertyListItem } from "../_model/configurables/Property";
import { Directive } from "@angular/core";


// validation function
export function listNotEmptyValidator() : ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {


        let valid = true;
        let msg = '';
        

        if (isNullOrUndefined(control.value)) {
            valid = false;
            msg = 'undefined'
        }

        let list = control.value as FormArray;

        
        if (list.length <= 0) {
            valid = false;
            msg = "length: " + list.length;
        }

        // console.log("Length: " + list.length + " Value: " + control.value + " Valid: " + valid + " - " + msg);



        return !valid ? {'listnotempty': {'listnotempty': "not undefined or empty", 'actual': msg}} : null;
    }
}



  