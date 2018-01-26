import {Asset} from './org.hyperledger.composer.system';
import {Participant} from './org.hyperledger.composer.system';
import {Transaction} from './org.hyperledger.composer.system';
import {Event} from './org.hyperledger.composer.system';
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
export class createTask extends Transaction {
  taskId: string;
  description: string;
  creator: Organisation;
}
export class createTaskEvent extends Event {
  task: Task;
}
export class reserveTask extends Transaction {
  task: Task;
  volunteer: Volunteer;
}
export class reserveTaskEvent extends Event {
  task: Task;
  volunteer: Volunteer;
}
export class assignTask extends Transaction {
  task: Task;
  taskPerformer: Volunteer[];
}
export class assignTaskEvent extends Event {
  task: Task;
  taskPerformer: Volunteer[];
}
export class finishTask extends Transaction {
  task: Task;
}
export class finishTaskEvent extends Event {
  task: Task;
}
// }
