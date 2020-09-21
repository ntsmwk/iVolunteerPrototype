import { ImageWrapper } from './image';

export class User {
  id: string;
  username: string;
  password: string;
  loginEmail: string;

  titleBefore: string;
  firstname: string;
  lastname: string;
  titleAfter: string;

  nickname: string;

  organizationName: string;
  organizationPosition: string;

  birthday: Date;

  about: string;
  address: string;
  phoneNumbers: string[] = [];
  websites: string[] = [];
  emails: string[] = [];

  registeredMarketplaceIds: string[];
  subscribedTenants: TenantUserSubscription[];

  image: ImageWrapper;

  localRepositoryLocation: LocalRepositoryLocation;
  dropboxToken: string;
  activated: boolean;
  accountType: AccountType;
}

export enum UserRole {
  VOLUNTEER = 'VOLUNTEER',
  HELP_SEEKER = 'HELP_SEEKER',
  TENANT_ADMIN = 'TENANT_ADMIN',
  ADMIN = 'ADMIN',
  RECRUITER = 'RECRUITER',
  FLEXPROD = 'FLEXPROD',
  NONE = 'NONE',
}

export enum LocalRepositoryLocation {
  LOCAL = 'LOCAL',
  DROPBOX = 'DROPBOX',
}

export class TenantUserSubscription {
  marketplaceId: string;
  tenantId: string;
  role: UserRole;
}

export class RoleTenantMapping {
  role: UserRole;
  tenantIds: string[];
}

export enum AccountType {
  PERSON = 'PERSON',
  ORGANIZATION = 'ORGANIZATION',
}
