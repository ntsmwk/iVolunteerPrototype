import {AbstractControl} from '@angular/forms';

export const CompetenceValidator = (control: AbstractControl): { [key: string]: boolean } => {
  const requiredCompetences = control.get('requiredCompetences');
  const acquirableCompetences = control.get('acquirableCompetences');

  const commonCompetences = requiredCompetences.value.filter(function (obj) {
    return 0 <= acquirableCompetences.value.indexOf(obj);
  });
  requiredCompetences.setErrors(commonCompetences.length === 0 ? null : {duplactedCompetence: true});
  acquirableCompetences.setErrors(commonCompetences.length === 0 ? null : {duplactedCompetence: true});
  return commonCompetences.length === 0 ? null : {duplactedCompetence: true};
<<<<<<< HEAD
};
=======
};
>>>>>>> f3c9cfdf7e5b06afcbe310137f2204578a7948f9
