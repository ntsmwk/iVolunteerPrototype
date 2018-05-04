import {Competence} from './competence';

export class TaskType {
  id: string;
  name: string;
  description: string;
  requiredCompetences: Competence[];
  acquirableCompetences: Competence[];
}
