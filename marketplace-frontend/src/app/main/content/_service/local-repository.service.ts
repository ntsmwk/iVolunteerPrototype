import { Injectable } from "@angular/core";
import { ClassInstance } from "../_model/meta/class";
import { UserInfo } from "../_model/userInfo";

@Injectable({
  providedIn: "root",
})
export abstract class LocalRepositoryService {
  constructor() {}

  abstract findClassInstancesByVolunteer(volunteerInfo: UserInfo);
  abstract synchronizeSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstance: ClassInstance
  );
  abstract getSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstanceId: string
  );
  abstract removeSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstanceId: string
  );

  abstract synchronizeClassInstances(
    volunteerInfo: UserInfo,
    classInstances: ClassInstance[]
  );
  abstract removeClassInstances(
    volunteerInfo: UserInfo,
    classInstanceIds: string[]
  );
  abstract overrideClassInstances(
    volunteerInfo: UserInfo,
    classInstances: ClassInstance[]
  );

  abstract async isConnected(credentials: any);
}
