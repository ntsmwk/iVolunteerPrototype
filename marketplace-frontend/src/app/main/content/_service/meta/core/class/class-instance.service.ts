import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassInstance } from 'app/main/content/_model/meta/Class';
import { Participant } from 'app/main/content/_model/participant';


@Injectable({
    providedIn: 'root'
  })
  export class ClassInstanceService {

    constructor(
      private http: HttpClient
    ) { }

    getClassInstancesByArcheType(marketplace: Marketplace, archetype: string, org?: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}?org=${org===null?'FF':org}`);
    }

    getUserClassInstancesByArcheType(marketplace: Marketplace, archetype: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/user`);
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

    setClassInstanceInUserRepository(marketplace: Marketplace, classInstanceIds: string[], inUserRepository: boolean) {
      return this.http.put(`${marketplace.url}/meta/core/class/instance/set-in-user-repository/${inUserRepository}`, classInstanceIds);
    }

    setClassInstanceInIssuerInbox(marketplace: Marketplace, classInstanceIds: string[], inIssuerInbox: boolean) {
      return this.http.put(`${marketplace.url}/meta/core/class/instance/set-in-issuer-inbox/${inIssuerInbox}`, classInstanceIds);
    }

  }
