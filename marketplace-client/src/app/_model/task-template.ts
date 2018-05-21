import {Competence} from './competence';
import {Address} from './Address';
import {Material} from './Material';

export class TaskTemplate {
  id: string;
  name: string;
  description: string;
  address: Address;
  material: Material;
  requiredCompetences: Competence[];
  acquirableCompetences: Competence[];
}
