import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassDefinition, ClassInstance } from "app/main/content/_model/meta/Class";


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

    getClassInstancesByArcheType(marketplace: Marketplace, archeType: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/instance/all/byArchetype/${archeType}`);
    }

    getClassInstanceById(marketplace: Marketplace, classInstanceId: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/instance/${classInstanceId}`);
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


  }
  