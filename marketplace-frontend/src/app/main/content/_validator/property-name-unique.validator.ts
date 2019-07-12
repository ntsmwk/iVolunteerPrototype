import { ValidatorFn, AbstractControl, NG_VALIDATORS, FormControl, Validator, ValidationErrors } from "@angular/forms";
import { isNullOrUndefined } from "util";

import { PropertyListItem, Property } from "../_model/configurables/Property";
import { Directive } from "@angular/core";


// validation function
export function propertyNameUniqueValidator(properties: PropertyListItem[], currentProperty: Property<any>) : ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(properties)) {
            return null;
        }
      
        let item = undefined;
        item = properties.find( (prop: PropertyListItem) => {
            return prop.name.toLocaleLowerCase() == control.value.toLocaleLowerCase();
        });

        
        if (!isNullOrUndefined(item) && !isNullOrUndefined(currentProperty) && currentProperty.id == item.id) {
            item = undefined;
        }

        return !isNullOrUndefined(item) ? {'propertynameunique': {'propertynameunique': item, 'actual': control.value}} : null;
    }
}



  