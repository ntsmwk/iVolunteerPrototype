import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassDefinition } from "app/main/content/_model/meta/Class";


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

    getClassInstanceById(marketplace: Marketplace, id: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/instance/${id}`);
    }

    createNewClassInstance(marketplace: Marketplace, classDefinition: ClassDefinition) {
      return this.http.post(`${marketplace.url}/meta/core/class/instance/new`, classDefinition);
    }

    createNewClassInstanceById(marketplace: Marketplace, classDefinitionId: string) {
      return this.http.post(`${marketplace.url}/meta/core/class/instance/${classDefinitionId}/new`, '');
    }

    updateClassInstance(marketplace: Marketplace, classDefinition: ClassDefinition) {
      return this.http.put(`${marketplace.url}/meta/core/class/instance/${classDefinition.id}/update`, classDefinition);
    }

    deleteClassInstance(marketplace: Marketplace, classDefinitionId: string) {
      return this.http.delete(`${marketplace.url}/meta/core/class/instance/${classDefinitionId}/delete`);
    }


  }
  