import { User } from "./user";

export class RuleExecution {
  volunteer: User;
  status: RuleStatus;
  timesFired: Number;
}

export enum RuleStatus {
  FIRED = "fired",
  NOT_FIRED = "not fired",
}
