import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../../../_model/marketplace';
import { ClassDefinition, ClassArchetype } from '../../../../_model/meta/class';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { FormConfigurationPreviewRequest } from 'app/main/content/_model/meta/form';

@Injectable({
  providedIn: "root",
})
export class ClassDefinitionService {
  constructor(private http: HttpClient) { }

  getAllClassDefinitions(marketplace: Marketplace, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/all/tenant/${tenantId}`
    );
  }

  getAllClassDefinitionsWithoutHeadAndEnums(
    marketplace: Marketplace,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/all/no-enum-no-head/tenant/${tenantId}`
    );
  }

  getAllClassDefinitionsWithoutRootAndEnums(
    marketplace: Marketplace,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/all/no-enum/tenant/${tenantId}`
    );
  }

  getClassDefinitionById(
    marketplace: Marketplace,
    id: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/${id}/tenant/${tenantId}`
    );
  }

  getClassDefinitionsById(
    marketplace: Marketplace,
    ids: string[],
    tenantId: string
  ) {
    if (!isNullOrUndefined(ids)) {
      return this.http.put(
        `${marketplace.url}/meta/core/class/definition/multiple/tenant/${tenantId}`,
        ids
      );
    } else {
      return of(null);
    }
  }

  // TODO

  createNewClassDefinition(marketplace: Marketplace, clazz: ClassDefinition) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/definition/new`,
      clazz
    );
  }

  addOrUpdateClassDefintions(
    marketplace: Marketplace,
    classDefinitions: ClassDefinition[]
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/definition/add-or-update`,
      classDefinitions
    );
  }

  changeClassDefinitionName(
    marketplace: Marketplace,
    id: string,
    newName: string
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/definition/${id}/change-name`,
      newName
    );
  }

  deleteClassDefinitions(marketplace: Marketplace, ids: string[]) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/definition/delete`,
      ids
    );
  }





  getEnumValuesFromEnumHeadClassDefinition(
    marketplace: Marketplace,
    classDefinitionId: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/enum-values/${classDefinitionId}/tenant/${tenantId}`
    );
  }

  getByArchetype(
    marketplace: Marketplace,
    archetype: ClassArchetype,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/archetype/${archetype}/tenant/${tenantId}`
    );
  }

  getFormConfigurations(marketplace: Marketplace, ids: string[]) {
    return this.http.put(`${marketplace.url}/meta/core/class/definition/form-configuration`, ids);
  }

  getFormConfigurationPreview(marketplace: Marketplace, classDefinitions: ClassDefinition[], relationships: Relationship[], rootClassDefinition: ClassDefinition) {
    const formConfigurationPreviewRequest = new FormConfigurationPreviewRequest(classDefinitions, relationships, rootClassDefinition);
    return this.http.put(`${marketplace.url}/meta/core/class/definition/form-configuration-preview`, formConfigurationPreviewRequest);
  }

  getFormConfigurationChunk(marketplace: Marketplace, currentClassDefinitionId: string, choiceId: string) {
    const params = [currentClassDefinitionId, choiceId];
    return this.http.put(`${marketplace.url}/meta/core/class/definition/form-configuration-chunk`, params);

  }



}
