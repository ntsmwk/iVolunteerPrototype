import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../../../_model/marketplace';
import { ClassDefinition, ClassArchetype } from '../../../../_model/meta/Class';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';
import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { FormConfiguration, FormConfigurationPreviewRequest } from 'app/main/content/_model/meta/form';

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

  getAllClassDefinitionsWithoutHeadAndEnums(marketplace: Marketplace, org: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/definition/all/no-enum?org=${org}`);
  }

  getAllClassDefinitionsWithProperties(marketplace: Marketplace, configuratorId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/definition/${configuratorId}/with-properties`);
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

  // getAllChildrenIdMap(marketplace: Marketplace, rootClassIds: string[]) {
  //   return this.http.put(`${marketplace.url}/meta/core/class/definition/get-children`, rootClassIds);
  // }

  // getAllParentsIdMap(marketplace: Marketplace, childClassIds: string[]) {
  //   return this.http.put(`${marketplace.url}/meta/core/class/definition/get-parents`, childClassIds);
  // }

  getFormConfiguratorsBottomUp(marketplace: Marketplace, ids: string[]) {
    return this.getFormConfigurations(marketplace, ids, 'bottom-up');
  }

  getFormConfiguratorsTopDown(marketplace: Marketplace, ids: string[]) {
    return this.getFormConfigurations(marketplace, ids, 'top-down');
  }

  getFormConfigurations(marketplace: Marketplace, ids: string[], type: string) {
    return this.http.put(`${marketplace.url}/meta/core/class/definition/form-configuration?type=${type}`, ids);
  }

  getFromConfigurationPreview(marketplace: Marketplace, classDefinitions: ClassDefinition[], relationships: Relationship[]) {
    const formConfigurationPreviewRequest = new FormConfigurationPreviewRequest(classDefinitions, relationships);
    return this.http.put(`${marketplace.url}/meta/core/class/definition/form-configuration-preview`, formConfigurationPreviewRequest);
  }


  getClassPropertyFromPropertyDefinitionById(marketplace: Marketplace, propIds: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/class/definition/get-classproperty-from-propertydefinition-by-id`, propIds);
  }

  addPropertiesToClassDefinitionById(marketplace: Marketplace, id: string, propIds: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/add-properties-by-id`, propIds);
  }

  // addPropertiesToClassDefinition(marketplace: Marketplace, id: string, propsToAdd: Property<any>[]) {
  //   return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/add-properties`, propsToAdd);
  // }

  removePropertiesFromClassDefinition(marketplace: Marketplace, id: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/meta/core/class/definition/${id}/remove-properties`, propIds);
  }

  getEnumValuesFromEnumHeadClassDefinition(marketplace: Marketplace, classDefinitionId: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/definition/enum-values/${classDefinitionId}`);
  }

  getByArchetype(marketplace: Marketplace, archetype: ClassArchetype, organisation: string) {
    return this.http.get(`${marketplace.url}/meta/core/class/definition/archetype/${archetype}?org=${organisation}`);
  }
}
