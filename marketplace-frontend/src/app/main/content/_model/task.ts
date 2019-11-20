import { Project } from './project';
import { CompetenceClassDefinition } from './meta/Class';

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
  requiredCompetences: CompetenceClassDefinition[];
  acquirableCompetences: CompetenceClassDefinition[];
}

