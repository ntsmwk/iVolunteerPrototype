import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassDefinition, ClassArchetype } from '../../../../_model/configurator/class';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { FormConfigurationPreviewRequest } from 'app/main/content/_model/configurator/form';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassDefinitionService {
  constructor(private http: HttpClient) { }

  getAllClassDefinitions() {
    return this.http.get(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/all`
    );
  }

  getClassDefinitionById(id: string, ) {
    return this.http.get(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/${id}`
    );
  }

  getClassDefinitionsById(ids: string[]) {
    if (!isNullOrUndefined(ids)) {
      return this.http.put(
        `${environment.CONFIGURATOR_URL}/meta/core/class/definition/multiple/`, ids
      );
    } else {
      return of(null);
    }
  }

  // TODO

  createNewClassDefinition(clazz: ClassDefinition) {
    return this.http.post(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/new`,
      clazz
    );
  }

  addOrUpdateClassDefintions(classDefinitions: ClassDefinition[]) {
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/add-or-update`, classDefinitions
    );
  }

  changeClassDefinitionName(id: string, newName: string) {
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/${id}/change-name`,
      newName
    );
  }

  deleteClassDefinitions(ids: string[]) {
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/delete`, ids
    );
  }

  getByArchetype(archetype: ClassArchetype, tenantId: string) {
    return this.http.get(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/archetype/${archetype}/tenant/${tenantId}`
    );
  }

  getFormConfigurations(ids: string[]) {
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/form-configuration`,
      ids
    );
  }

  getFormConfigurationPreview(classDefinitions: ClassDefinition[], relationships: Relationship[], rootClassDefinition: ClassDefinition) {
    const formConfigurationPreviewRequest = new FormConfigurationPreviewRequest(
      classDefinitions,
      relationships,
      rootClassDefinition
    );
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/form-configuration-preview`, formConfigurationPreviewRequest
    );
  }

  getFormConfigurationChunk(currentClassDefinitionId: string, choiceId: string) {
    const params = [currentClassDefinitionId, choiceId];
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/meta/core/class/definition/form-configuration-chunk`, params
    );
  }

}
