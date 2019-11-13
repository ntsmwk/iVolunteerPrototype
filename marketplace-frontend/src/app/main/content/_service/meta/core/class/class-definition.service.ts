import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassDefinition } from "../../../../_model/meta/Class";
import { PropertyDefinition } from "../../../../_model/meta/Property";
import { isNullOrUndefined } from "util";
import { of } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
  export class ClassDefinitionService {
  
    constructor(
      private http: HttpClient
    ) { }

    getAllClassDefinitions(marketplace: Marketplace) {
      return this.http.get(`${marketplace.url}/meta/core/class/definition/all`);
    }

    getClassDefinitionById(marketplace: Marketplace, id: string) {
      return this.http.get(`${marketplace.url}/meta/core/class/definition/${id}`);
    }

    getClassDefinitionsById(marketplace: Marketplace, ids: string[]) {
      if (!isNullOrUndefined(ids)) {
        return this.http.put(`${marketplace.url}/meta/core/class/definition/multiple`, ids);
      } else {
        return of(null);
      }
    }

    createNewClassDefinition(marketplace: Marketplace, clazz: ClassDefinition) {
      return this.http.post(`${marketplace.url}/meta/core/class/definition/new`, clazz);
    }

    addOrUpdateClassDefintions(marketplace: Marketplace, classDefinitions: ClassDefinition[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/add-or-update`, classDefinitions);
    }

    changeClassDefinitionName(marketplace: Marketplace, id: string, newName: string) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/change-name`, newName);
    }

    deleteClassDefinitions(marketplace: Marketplace, ids: string[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/delete`, ids);
    }


    getClassPropertyFromPropertyDefinitionById(marketplace: Marketplace, propIds: String[]) {
      return this.http.put(`${marketplace.url}/meta/core/class/definition/get-classproperty-from-propertydefinition-by-id`, propIds);
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