import { ClassInstanceDTO } from './meta/class';

export class LocalRepository {
  id: string;
  volunteerUsername: string;
  classInstances: ClassInstanceDTO[];

  constructor(id: string, volunteerUsername: string) {
    this.id = id;
    this.volunteerUsername = volunteerUsername;
    this.classInstances = [];
  }
}