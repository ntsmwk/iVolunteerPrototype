import { ImageWrapper } from './image';
import { NextcloudCredentials } from './nextcloud-credentials';

export class User {
  id: string;
  username: string;
  password: string;
  loginEmail: string;

  formOfAddress: string;
  titleBefore: string;
  firstname: string;
  lastname: string;
  titleAfter: string;

  nickname: string;

  organizationName: string;
  organizationPosition: string;

  birthday: Date;

  about: string;
  address: Address;
  timeslots: Timeslot[] = [];
  phoneNumbers: string[] = [];
  websites: string[] = [];
  emails: string[] = [];

  registeredMarketplaceIds: string[];
  subscribedTenants: TenantUserSubscription[];

  // image: ImageWrapper;

  localRepositoryLocation: LocalRepositoryLocation;
  dropboxToken: string;
  nextcloudCredentials: NextcloudCredentials;

  activated: boolean;
  accountType: AccountType;
}

export class Address {
  street: string;
  houseNumber: string;
  postcode: string;
  city: string;
  country: string;
}

export class Timeslot {
  weekday: Weekday;
  fromHours1: number;
  fromMins1: number;
  toHours1: number;
  toMins1: number;

  fromHours2: number;
  fromMins2: number;
  toHours2: number;
  toMins2: number;

  active: boolean;
}



export enum Weekday {
  MONDAY = 'MONDAY', TUESDAY = 'TUESDAY', WEDNESDAY = 'WEDNESDAY', THURSDAY = 'THURSDAY', FRIDAY = 'FRIDAY',
  SATURDAY = 'SATURDAY', SUNDAY = 'SUNDAY'
}

export namespace Weekday {
  const translationLabels = {
    'MONDAY': 'Montag',
    'TUESDAY': 'Dienstag',
    'WEDNESDAY': 'Mittwoch',
    'THURSDAY': 'Donnerstag',
    'FRIDAY': 'Freitag',
    'SATURDAY': 'Samstag',
    'SUNDAY': 'Sonntag',
  };

  export function getWeekdayLabel(weekday: Weekday) {
    return translationLabels[weekday];
  }
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
  NEXTCLOUD = 'NEXTCLOUD',
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
