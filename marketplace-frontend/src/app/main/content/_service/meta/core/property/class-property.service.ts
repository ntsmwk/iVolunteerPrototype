import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassProperty, FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';

export class ClassPropertyRequestObject {
  flatPropertyDefinitionIds: string[];
  treePropertyDefinitionIds: string[];
}

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

  getClassPropertyFromDefinitionById(marketplace: Marketplace, flatPropertyDefinitionIds: string[], treePropertyDefinitionIds: string[]) {
    const requestObject: ClassPropertyRequestObject = { flatPropertyDefinitionIds: flatPropertyDefinitionIds, treePropertyDefinitionIds: treePropertyDefinitionIds };
    return this.http.put(`${marketplace.url}/meta/core/property/class/get-classproperty-from-definition-by-id/`, requestObject);
  }

  addPropertiesToClassDefinitionById(marketplace: Marketplace, id: string, propIds: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/add-properties-by-id`, propIds);
  }

  addPropertiesToClassDefinition(marketplace: Marketplace, id: string, propsToAdd: FlatPropertyDefinition<any>[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/add-properties`, propsToAdd);
  }

  removePropertiesFromClassDefinition(marketplace: Marketplace, id: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${id}/remove-properties`, propIds);
  }
}
