import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { FlatPropertyDefinition } from '../_model/configurator/property/property';
import { TreePropertyDefinition } from '../_model/configurator/property/tree-property';
import { ClassInstance, ClassDefinition } from '../_model/configurator/class';
import { ClassConfiguration, MatchingConfiguration } from '../_model/configurator/configurations';
import { Relationship } from '../_model/configurator/relationship';
import { MatchingOperatorRelationship } from '../_model/matching';

export interface SaveClassConfigurationRequest {
  classConfiguration?: ClassConfiguration;
  classDefinitions?: ClassDefinition[];
  relationships?: Relationship[];
  deletedClassDefinitionIds?: string[];
  deletedRelationshipIds?: string[];
  tenantId: string;
}

export interface SaveMatchingConfiugrationRequest {
  matchingConfiguration?: MatchingConfiguration;
  matchingOperatorRelationships?: MatchingOperatorRelationship[];
  tenantId: string;
}

@Injectable({
  providedIn: 'root'
})
export class ResponseService {

  constructor(
    private http: HttpClient
  ) { }

  public sendClassConfiguratorResponse(url: string, saveRequest: SaveClassConfigurationRequest, idsToDelete: string[], action: string, actionContext?: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-configurator`, { url, saveRequest, idsToDelete, action, actionContext });
  }

  public sendClassInstanceConfiguratorResponse(url: string, classInstance: ClassInstance) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-instance-configurator`, { url, classInstance });
  }

  public sendMatchingConfiguratorResponse(url: string, saveRequest: SaveMatchingConfiugrationRequest, idsToDelete: string[], action: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/matching-configurator`, { url, saveRequest, idsToDelete, action });
  }

  public sendPropertyConfiguratorResponse(url: string, flatPropertyDefinitions: FlatPropertyDefinition<any>[], treePropertyDefinitions: TreePropertyDefinition[], action: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/property-configurator`, { url, flatPropertyDefinitions, treePropertyDefinitions, action });
  }

}
