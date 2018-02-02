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

export class Task extends Asset {
  taskId: string;
  description: string;
  taskStatus: string;
  reservedVolunteers: string[];
  creator: string;
  taskPerformer: string[];
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
  task: string;
  taskPerformer: string[];
}

export class FinishTask extends Transaction {
  task: string;
}

// }
