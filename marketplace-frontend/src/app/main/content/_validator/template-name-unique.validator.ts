import { ValidatorFn, AbstractControl, ValidationErrors } from "@angular/forms";
import { isNullOrUndefined } from "util";



// validation function
export function templateNameUniqueValidator(templateNames: string[]) : ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

        if (isNullOrUndefined(templateNames)) {
            return null;
        }
      
        let item = undefined;
        item = templateNames.find( (templateName: string) => {
            return templateName.toLocaleLowerCase() == control.value.toLocaleLowerCase();
        });

        return !isNullOrUndefined(item) ? {'templatenameunique': {'templatenameunique': item, 'actual': control.value}} : null;
    }
}



  