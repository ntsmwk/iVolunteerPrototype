import {Asset, Event, Participant, Transaction} from './org.hyperledger.composer.system';

// export namespace at.jku.cis{
export abstract class Person extends Participant {
  email: string;
}

export class Organisation extends Person {
  orgName: string;
}

export class Volunteer extends Person {
  firstName: string;
  lastName: string;
}

export enum TaskStatus {
  CREATED,
  RESERVED,
  ASSIGNED,
  FINISHED,
}

export class Task extends Asset {
  taskId: string;
  description: string;
  taskStatus: TaskStatus;
  reservedVolunteers: Volunteer[];
  creator: Organisation;
  taskPerformer: Volunteer[];
}

export class CreateTask extends Transaction {
  taskId: string;
  description: string;
  creator: string;
}

export class ReserveTask extends Transaction {
  task: string;
  volunteer: string;
}

export class AssignTask extends Transaction {
  task: Task;
  taskPerformer: Volunteer[];
}

export class FinishTask extends Transaction {
  task: string;
}

// }
