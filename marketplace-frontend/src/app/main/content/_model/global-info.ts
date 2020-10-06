import { UserRole } from "./user";
import { Marketplace } from "./marketplace";
import { UserInfo } from "./userInfo";
import { Tenant } from "./tenant";

export class GlobalInfo {
  // role independent fields
  userInfo: UserInfo;
  userSubscriptions: UserSubscriptionDTO[];
  registeredMarketplaces: Marketplace[];

  // role specific fields
  currentRole: UserRole;
  currentTenants: Tenant[];
  currentMarketplaces: Marketplace[];
}

// for globalInfo only, otherwise use TenantUserSubscription
export class UserSubscriptionDTO {
  marketplace: Marketplace;
  tenant: Tenant;
  role: UserRole;
}
