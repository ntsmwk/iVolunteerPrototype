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
      taskCreator: Organisation;
      taskPerformer: Volunteer[];
   }
   export class CreateTask extends Transaction {
      taskId: string;
      description: string;
      creator: Organisation;
   }
   export class CreateTaskEvent extends Event {
      taskId: string;
      description: string;
   }
   export class UpdateTaskStatus extends Transaction {
      taskStatus: TaskStatus;
      task: Task;
   }
   export class UpdateTaskStatusEvent extends Event {
      taskStatus: TaskStatus;
      task: Task;
   }
// }
