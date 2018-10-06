import { Volunteer } from "./volunteer";
import { CompetenceEntry } from "./competence-entry";
import { TaskEntry } from "./task-entry";

export class VolunteerProfile {
  id: string;
  volunteer: Volunteer;
  taskList: TaskEntry[];
  competenceList: CompetenceEntry[];
}

export class CompetenceTableRow {
  competenceId: string;
  competenceName: string;
  marketplaceActions: string[];
  
}
