import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassDefintion } from "../../../../_model/meta/Class";
import { PropertyDefinition, Property } from "../../../../_model/meta/Property";

@Injectable({
    providedIn: 'root'
  })
  export class ClassDefinitionService {
  
    constructor(
      private http: HttpClient
    ) { }

    savePropertiesLegacy(marketplace: Marketplace, classId: string, properties: Property<any>[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${classId}/save-properties-legacy`, properties);
    }

    getAllClassDefinitions(marketplace: Marketplace) {
      return this.http.get(`${marketplace.url}/meta/core/class/definition/all`);
    }

    getClassDefinitionById(marketplace: Marketplace, id: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/definition/${id}`);
    }

    createNewClassDefinition(marketplace: Marketplace, clazz: ClassDefintion) {
      return this.http.post(`${marketplace.url}/meta/core/class/definition/new`, clazz);
    }

    changeClassDefinitionName(marketplace: Marketplace, id: string, newName: string) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/change-name`, newName);
    }

    deleteClassDefinitions(marketplace: Marketplace, ids: string[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/delete`, ids);
    }



    addPropertiesToClassDefinitionById(marketplace: Marketplace, id: string, propIds: String[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/add-properties-by-id`, propIds);
    }

    addPropertiesToClassDefinition(marketplace: Marketplace, id: string, propsToAdd: PropertyDefinition<any>[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/add-properties`, propsToAdd);
    }

    removePropertiesFromClassDefinition(marketplace: Marketplace, id: string, propIds: string[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/remove-properties`, propIds);
    }

  }