import { NextcloudCredentials } from "./nextcloud-credentials";
import { AccountType, LocalRepositoryLocation } from "./user";

export class UserInfo {
  id: string;

  username: string;
  firstname: string;
  lastname: string;

  loginEmail: string;

  titleBefore: string;
  titleAfter: string;

  birthday: Date;

  formOfAddress: string;
  address: string;

  organizationName: string;

  localRepositoryLocation: LocalRepositoryLocation;
  dropboxToken: string;
  nextcloudCredentials: NextcloudCredentials;

  activated: boolean;
  accountType: AccountType;
}
