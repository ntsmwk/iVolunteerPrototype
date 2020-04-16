import { Volunteer } from "./volunteer";
import { ClassInstance, ClassInstanceDTO } from './meta/Class';

export class LocalRepository {
  id: string;
  volunteer: Volunteer;
  classInstances: ClassInstanceDTO[];

}