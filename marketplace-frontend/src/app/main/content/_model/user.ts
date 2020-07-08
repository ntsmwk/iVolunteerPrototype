import { Marketplace } from "./marketplace";
import { Tenant } from "./tenant";

export class User {
  id: string;
  username: string;
  password: string;

  firstname: string;
  lastname: string;
  middlename: string;
  nickname: string;

  position: string;

  birthday: Date;

  about: string;
  address: string;
  phoneNumbers: string[] = [];
  websites: string[] = [];
  emails: string[] = [];

  subscribedTenants: TenantUserSubscription[];

  image;
}

export enum UserRole {
  VOLUNTEER = "VOLUNTEER",
  HELP_SEEKER = "HELP_SEEKER",
  ADMIN = "ADMIN",
  RECRUITER = "RECRUITER",
  FLEXPROD = "FLEXPROD",
}
export class TenantUserSubscription {
  marketplace: Marketplace;
  tenant: Tenant;
  role: UserRole;
}
