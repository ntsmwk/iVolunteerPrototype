import { User, UserRole } from "./user";
import { Tenant } from "./tenant";
import { Marketplace } from "./marketplace";

export class GlobalInfo {
  user: User;
  userRole: UserRole;
  marketplace: Marketplace;
  tenants: Tenant[];
}
