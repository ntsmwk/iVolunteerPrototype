import { User, ParticipantRole } from "./user";
import { Tenant } from "./tenant";
import { Marketplace } from "./marketplace";

export class GlobalInfo {
  user: User;
  userRole: ParticipantRole;
  marketplace: Marketplace;
  tenants: Tenant[];
}
