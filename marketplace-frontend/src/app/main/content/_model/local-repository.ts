import { Volunteer } from "./volunteer";
import { ClassInstanceDTO } from './meta/Class';

export class LocalRepository {
  id: string;
  volunteer: Volunteer;
  taskList: ClassInstanceDTO[];
  competenceList: ClassInstanceDTO[];

}
