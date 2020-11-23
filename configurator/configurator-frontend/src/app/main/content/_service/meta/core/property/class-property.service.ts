import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassProperty, FlatPropertyDefinition } from 'app/main/content/_model/configurator/property/property';
import { environment } from 'environments/environment';

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

  getAllClassPropertiesFromClass(classDefintionId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${classDefintionId}/all`);
  }

  getClassPropertyById(classDefintionId: string, classPropertyId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${classDefintionId}/${classPropertyId}`);
  }

  updateClassProperty(classDefintionId: string, classProperty: ClassProperty<any>) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${classDefintionId}/update`, classProperty);
  }

  getClassPropertyFromDefinitionById(flatPropertyDefinitionIds: string[], treePropertyDefinitionIds: string[]) {
    const requestObject: ClassPropertyRequestObject = { flatPropertyDefinitionIds: flatPropertyDefinitionIds, treePropertyDefinitionIds: treePropertyDefinitionIds };
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/property/class/get-classproperty-from-definition-by-id`, requestObject);
  }

  addPropertiesToClassDefinitionById(id: string, propIds: String[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${id}/add-properties-by-id`, propIds);
  }

  addPropertiesToClassDefinition(id: string, propsToAdd: FlatPropertyDefinition<any>[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${id}/add-properties`, propsToAdd);
  }

  removePropertiesFromClassDefinition(id: string, propIds: string[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/property/class/${id}/remove-properties`, propIds);
  }
}
