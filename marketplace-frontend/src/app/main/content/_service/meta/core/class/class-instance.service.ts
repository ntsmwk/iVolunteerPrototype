import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { Participant } from 'app/main/content/_model/participant';


@Injectable({
  providedIn: 'root'
})
export class ClassInstanceService {

  constructor(
    private http: HttpClient
  ) { }

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

  getUserClassInstancesByArcheType(marketplace: Marketplace, archetype: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/user`);
  }

  getClassInstancesByUserId(marketplace: Marketplace, userId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/by-userid/${userId}`);
  }

  getClassInstanceById(marketplace: Marketplace, classInstanceId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/${classInstanceId}`);
  }

  getClassInstancesInUserRepository(marketplace: Marketplace, userId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/in-user-repository/${userId}`);
  }

  getClassInstancesInUserInbox(marketplace: Marketplace, issuerId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/in-user-inbox/${issuerId}`);
  }

  getClassInstancesInIssuerInbox(marketplace: Marketplace, issuerId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/instance/in-issuer-inbox/${issuerId}`);
  }

  createNewClassInstances(marketplace: Marketplace, classInstances: ClassInstance[]) {
    return this.http.post(`${marketplace.url}/meta/core/class/instance/new`, classInstances);
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

  setClassInstanceInUserRepository(marketplace: Marketplace, classInstanceIds: string[], inUserRepository: boolean) {
    return this.http.put(`${marketplace.url}/meta/core/class/instance/set-in-user-repository/${inUserRepository}`, classInstanceIds);
  }

  setClassInstanceInIssuerInbox(marketplace: Marketplace, classInstanceIds: string[], inIssuerInbox: boolean) {
    return this.http.put(`${marketplace.url}/meta/core/class/instance/set-in-issuer-inbox/${inIssuerInbox}`, classInstanceIds);
  }

}
