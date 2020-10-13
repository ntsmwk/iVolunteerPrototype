import { Injectable } from "@angular/core";
import { ClassInstance } from "../_model/meta/class";
import { User } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export abstract class LocalRepositoryService {
  constructor() {}

  abstract findClassInstancesByVolunteer(volunteer: User);
  abstract synchronizeSingleClassInstance(
    volunteer: User,
    classInstance: ClassInstance
  );
  abstract getSingleClassInstance(volunteer: User, classInstanceId: string);
  abstract removeSingleClassInstance(volunteer: User, classInstanceId: string);

  abstract synchronizeClassInstances(
    volunteer: User,
    classInstances: ClassInstance[]
  );
  abstract removeClassInstances(volunteer: User, classInstanceIds: string[]);
  abstract overrideClassInstances(
    volunteer: User,
    classInstances: ClassInstance[]
  );

  abstract async isConnected(credentials: any);
}
