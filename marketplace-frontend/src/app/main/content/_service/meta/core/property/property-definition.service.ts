import { Injectable } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { HttpClient } from "@angular/common/http";
import { PropertyDefinition } from "app/main/content/_model/meta/Property";
import { PropertyConstraint } from "app/main/content/_model/meta/Constraint";


@Injectable({
    providedIn: 'root'
  })
  export class PropertyDefinitionService {


    constructor(
      private http: HttpClient
    ) { }


    getAllPropertyDefinitons(marketplace: Marketplace) {
      return this.http.get(`${marketplace.url}/meta/core/property/definition/all`);
    }

    getPropertyDefinitionById(marketplace: Marketplace, id: string) {
      return this.http.get(`${marketplace.url}/meta/core/property/definition/${id}`)
    }

    createNewPropertyDefinition(marketplace: Marketplace, propertyDefinitions: PropertyDefinition<any>[]) {
      return this.http.post(`${marketplace.url}/meta/core/property/definition/new`, propertyDefinitions);
    }

    updatePropertyDefintion(marketplace: Marketplace, propertyDefinitions: PropertyDefinition<any>[]) {
      return this.http.put(`${marketplace.url}/meta/core/property/definition/update`, propertyDefinitions);
    }

    deletePropertyDefinition(marketplace: Marketplace, id: string) {
      return this.http.delete(`${marketplace.url}/meta/core/property/definition/${id}/delete`);
    }

    addConstraintToPropertyDefinition(marketplace: Marketplace, id: string, constraints: PropertyConstraint<any>[]) {
      return this.http.patch(`${marketplace.url}/meta/core/property/definition/${id}/add-constraint`, constraints);
    }

    removeConstraintFromPropertyDefintion(marketplace: Marketplace, id: string, constraintIds: string[]) {
      return this.http.patch(`${marketplace.url}/meta/core/property/definition/${id}/remove-constraint`, constraintIds);
    }





  }