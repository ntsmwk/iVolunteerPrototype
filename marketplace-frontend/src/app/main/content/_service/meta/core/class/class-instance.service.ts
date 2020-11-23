import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  ClassInstance,
  ClassDefinition
} from "app/main/content/_model/meta/class";
import { User } from "app/main/content/_model/user";

@Injectable({
  providedIn: "root"
})
export class ClassInstanceService {
  constructor(private http: HttpClient) { }

  getUserClassInstancesByArcheType(
    marketplace: Marketplace,
    archetype: string,
    userId: string,
    tenantIds: string[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/user/${userId}`,
      tenantIds
    );
  }

  getClassInstanceById(marketplace: Marketplace, classInstanceId: String) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/${classInstanceId}`
    );
  }

  getClassInstancesById(marketplace: Marketplace, classInstanceIds: String[]) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instances`,
      classInstanceIds
    );
  }

  mapClassInstancesToDTOs(
    marketplace: Marketplace,
    classInstances: ClassInstance[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instanceDTOs`,
      classInstances
    );
  }

  getClassInstancesInUserInbox(
    marketplace: Marketplace,
    issuerId: string,
    tenantIds: string[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/in-user-inbox/${issuerId}`,
      tenantIds
    );
  }

  getClassInstancesInIssuerInbox(marketplace: Marketplace, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/in-issuer-inbox?tId=${tenantId}`
    );
  }

  createNewClassInstances(
    marketplace: Marketplace,
    classInstances: ClassInstance[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/new`,
      classInstances
    );
  }

  createSharedClassInstances(
    marketplace: Marketplace,
    tenantId: string,
    classInstanceId: string
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/newShared?tId=${tenantId}`,
      classInstanceId
    );
  }

  createClassInstanceByClassDefinitionId(
    marketplace: Marketplace,
    classDefinitionId: ClassDefinition,
    volunteerId: User,
    tenantId: string,
    properties
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/from-definition/${classDefinitionId}/user/${volunteerId}?tId=${tenantId}`,
      properties
    );
  }

  issueClassInstance(marketplace: Marketplace, classInstanceIds: string[]) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/instance/issue`,
      classInstanceIds
    );
  }

  getAllClassInstances(marketplace: Marketplace, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/all?tId=${tenantId}`
    );
  }

  getClassInstancesByArcheType(
    marketplace: Marketplace,
    archetype: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}?tId=${tenantId}`
    );
  }

  deleteClassInstance(marketplace: Marketplace, classInstanceId: string) {
    return this.http.delete(
      `${marketplace.url}/meta/core/class/instance/${classInstanceId}/delete`
    );
  }
}
