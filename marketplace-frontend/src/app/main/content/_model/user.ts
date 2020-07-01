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
  "VOLUNTEER",
  "HELP_SEEKER",
  "ADMIN",
  "RECRUITER",
  "FLEXPROD",
}
export class TenantUserSubscription {
  tenantId: string;
  role: UserRole;
}
