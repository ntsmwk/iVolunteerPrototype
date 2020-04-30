import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import {
  ClassInstance,
  ClassDefinition,
} from 'app/main/content/_model/meta/class';
import { Participant } from 'app/main/content/_model/participant';

@Injectable({
  providedIn: 'root',
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

  getClassInstanceById(
    marketplace: Marketplace,
    classInstanceId: String,
    tenantId: String
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/by-id/${classInstanceId}/tenant/${tenantId}`
    );
  }

  getClassInstancesInUserRepository(
    marketplace: Marketplace,
    userId: string,
    tenantIds: string[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/in-user-repository/${userId}`,
      tenantIds
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

  getClassInstancesInIssuerInbox(
    marketplace: Marketplace,
    issuerId: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/in-issuer-inbox/${issuerId}/tenant/${tenantId}`
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

  createClassInstanceByClassDefinitionId(
    marketplace: Marketplace,
    classDefinitionId: ClassDefinition,
    volunteerId: Participant,
    tenantId: string,
    properties
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/from-definition/${classDefinitionId}/tenant/${tenantId}/user/${volunteerId}`,
      properties
    );
  }

  setClassInstanceInUserRepository(
    marketplace: Marketplace,
    classInstanceIds: string[],
    inUserRepository: boolean
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/instance/set-in-user-repository/${inUserRepository}`,
      classInstanceIds
    );
  }

  setClassInstanceInIssuerInbox(
    marketplace: Marketplace,
    classInstanceIds: string[],
    inIssuerInbox: boolean
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/instance/set-in-issuer-inbox/${inIssuerInbox}`,
      classInstanceIds
    );
  }


  getAllClassInstances(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all`);
  }

  getClassInstancesByArcheType(marketplace: Marketplace, archetype: string, org: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}?org=${org}`);
  }

  getClassInstancesByArcheTypeBefore(marketplace: Marketplace, archetype: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/before`);
  }

  getClassInstancesByArcheTypeAfter(marketplace: Marketplace, archetype: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/after`);
  }

  getClassInstancesByArcheTypeFake(marketplace: Marketplace, archetype: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/fake`);
  }

  getClassInstancesByArcheTypeWithHash(marketplace: Marketplace, archetype: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/hashed`);
  }

  getClassInstancesByUserId(marketplace: Marketplace, userId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/by-userid/${userId}`);
  }

  createNewClassInstanceById(marketplace: Marketplace, classDefinitionId: string) {
    return this.http.post(`${marketplace.url}/meta/core/class/instance/${classDefinitionId}/new`, '');
  }

  updateClassInstance(marketplace: Marketplace, classInstance: ClassInstance) {
    return this.http.put(`${marketplace.url}/meta/core/class/instance/${classInstance.id}/update`, classInstance);
  }

  deleteClassInstance(marketplace: Marketplace, classInstanceId: string) {
    return this.http.delete(`${marketplace.url}/meta/core/class/instance/${classInstanceId}/delete`);
  }

}
