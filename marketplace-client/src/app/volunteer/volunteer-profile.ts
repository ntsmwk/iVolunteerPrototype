import {TaskEntry} from './task-entry';
import {CompetenceEntry} from './competence-entry';
import {Volunteer} from './volunteer';

export class VolunteerProfile {
  id: string;
  volunteer: Volunteer;
  taskList: Array<TaskEntry>;
  competenceList: Array<CompetenceEntry>;
}


