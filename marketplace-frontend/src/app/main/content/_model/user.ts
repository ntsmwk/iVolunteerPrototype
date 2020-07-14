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

  registeredMarketplaceIds: string[];
  subscribedTenants: TenantUserSubscription[];

  image;
}

export enum UserRole {
  VOLUNTEER = 'VOLUNTEER',
  HELP_SEEKER = 'HELP_SEEKER',
  ADMIN = 'ADMIN',
  RECRUITER = 'RECRUITER',
  FLEXPROD = 'FLEXPROD',
}
export class TenantUserSubscription {
  marketplaceId: string;
  tenantId: string;
  role: UserRole;
}
