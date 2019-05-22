import {Project} from './project';
import {Competence} from './competence';

export class Task {
  id: string;
  name: string;
  
  description: string;
  project: Project;
  workflowKey: string;
  marketplaceId: string;
  startDate: Date;
  endDate: Date;
  status: string;
  requiredCompetences: Competence[];
  acquirableCompetences: Competence[];
}

