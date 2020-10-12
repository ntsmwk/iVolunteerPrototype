import { NextcloudCredentials } from "./nextcloud-credentials";
import { FormTimeSlot } from "../_components/common/profile/profile-form/profile-form.component";

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
  profileImagePath: string;

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
  subscribedTenants: TenantSubscription[];

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
  countryCode: number;
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
  secondActive: boolean;

  constructor(formTimeslot: FormTimeSlot) {
    this.weekday = formTimeslot.weekday;
    this.active = formTimeslot.active;
    this.secondActive = formTimeslot.secondActive;

    const from1 = formTimeslot.from1.split(":");
    const from2 = formTimeslot.from2.split(":");
    const to1 = formTimeslot.to1.split(":");
    const to2 = formTimeslot.to2.split(":");

    this.fromHours1 = Number(from1[0]);
    this.fromHours2 = Number(from2[0]);
    this.fromMins1 = Number(from1[1]);
    this.fromMins2 = Number(from2[1]);

    this.toHours1 = Number(to1[0]);
    this.toHours2 = Number(to2[0]);
    this.toMins1 = Number(to1[1]);
    this.toMins2 = Number(to2[1]);
  }
}

export enum Weekday {
  MONDAY = "MONDAY",
  TUESDAY = "TUESDAY",
  WEDNESDAY = "WEDNESDAY",
  THURSDAY = "THURSDAY",
  FRIDAY = "FRIDAY",
  SATURDAY = "SATURDAY",
  SUNDAY = "SUNDAY"
}

export namespace Weekday {
  const translationLabels = {
    MONDAY: "Montag",
    TUESDAY: "Dienstag",
    WEDNESDAY: "Mittwoch",
    THURSDAY: "Donnerstag",
    FRIDAY: "Freitag",
    SATURDAY: "Samstag",
    SUNDAY: "Sonntag"
  };

  export function getWeekdayLabel(weekday: Weekday) {
    return translationLabels[weekday];
  }
}

export enum UserRole {
  VOLUNTEER = "VOLUNTEER",
  HELP_SEEKER = "HELP_SEEKER",
  TENANT_ADMIN = "TENANT_ADMIN",
  ADMIN = "ADMIN",
  RECRUITER = "RECRUITER",
  FLEXPROD = "FLEXPROD",
  NONE = "NONE"
}

export enum LocalRepositoryLocation {
  LOCAL = "LOCAL",
  DROPBOX = "DROPBOX",
  NEXTCLOUD = "NEXTCLOUD"
}

export class TenantSubscription {
  marketplaceId: string;
  tenantId: string;
  role: UserRole;
}

export class RoleTenantMapping {
  role: UserRole;
  tenantIds: string[];
}

export enum AccountType {
  PERSON = "PERSON",
  ORGANIZATION = "ORGANIZATION"
}
