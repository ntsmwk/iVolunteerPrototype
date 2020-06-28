import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassProperty, PropertyDefinition } from 'app/main/content/_model/meta/property';


@Injectable({
  providedIn: 'root'
})
export class ClassPropertyService {
  constructor(
    private http: HttpClient
  ) { }

  getAllClassPropertiesFromClass(marketplace: Marketplace, classDefintionId: string) {
    return this.http.get(`${marketplace.url}/meta/core/property/class/${classDefintionId}/all`);
  }

  getClassPropertyById(marketplace: Marketplace, classDefintionId: string, classPropertyId: string) {
    return this.http.get(`${marketplace.url}/meta/core/property/class/${classDefintionId}/${classPropertyId}`)
  }

  updateClassProperty(marketplace: Marketplace, classDefintionId: string, classProperty: ClassProperty<any>) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${classDefintionId}/update`, classProperty);
  }

  getClassPropertyFromDefinitionById(marketplace: Marketplace, propIds: string[], enumIds: string[]) {
    const requestObject = { propertyDefinitionIds: propIds, enumDefinitionIds: enumIds };
    return this.http.put(`${marketplace.url}/meta/core/property/class/get-classproperty-from-definition-by-id/`, requestObject);
  }

  addPropertiesToClassDefinitionById(marketplace: Marketplace, id: string, propIds: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/add-properties-by-id`, propIds);
  }

  addPropertiesToClassDefinition(marketplace: Marketplace, id: string, propsToAdd: PropertyDefinition<any>[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/add-properties`, propsToAdd);
  }

  removePropertiesFromClassDefinition(marketplace: Marketplace, id: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/remove-properties`, propIds);
  }
}
