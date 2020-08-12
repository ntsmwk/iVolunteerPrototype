import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { PropertyItem } from '../_model/meta/property/property';

// validation function
export function propertyNameUniqueValidator(properties: PropertyItem[], currentProperty: PropertyItem): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(properties)) {
            return null;
        }
        // TODO REMOVE OR ELSE IT DOESNT WORK
        else {
            return null;
        }


        const item = properties.find((prop: PropertyItem) => {
            return prop.name.toLocaleLowerCase() === control.value.toLocaleLowerCase();
        });


        // if (!isNullOrUndefined(item) && !isNullOrUndefined(currentProperty) && currentProperty.id == item.id) {
        //     item = undefined;
        // }

        // return !isNullOrUndefined(item) ? {'propertynameunique': {'propertynameunique': item, 'actual': control.value}} : null;
    };
}




