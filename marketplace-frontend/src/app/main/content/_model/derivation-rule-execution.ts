import { Volunteer } from './volunteer';

export class RuleExecution {
  volunteer: Volunteer;
  status: RuleStatus;
  timesFired: Number;
}


export enum RuleStatus {
  FIRED = "fired",
  NOT_FIRED = "not fired",
}

