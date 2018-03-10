import {Competence} from '../competence/competence';

export class TaskType {
  id: string;
  name: string;
  description: string;
  requiredCompetences: Competence[];
  acquirableCompetences: Competence[];
}
