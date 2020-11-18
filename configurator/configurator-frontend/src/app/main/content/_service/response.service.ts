import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { FlatPropertyDefinition } from '../_model/configurator/property/property';
import { TreePropertyDefinition } from '../_model/configurator/property/tree-property';
import { ClassInstance } from '../_model/configurator/class';

@Injectable({
  providedIn: 'root'
})
export class ResponseService {

  constructor(
    private http: HttpClient
  ) { }

  public sendClassConfiguratorResponse(url: string, idToSave: string, idsToDelete: string[], action: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-configurator`, { url, idToSave, idsToDelete, action });
  }

  public sendClassInstanceConfiguratorResponse(url: string, classInstance: ClassInstance) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-instance-configurator`, { url, classInstance });
  }

  public sendMatchingConfiguratorResponse(url: string, idToSave: string, idsToDelete: string[], action: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/matching-configurator`, { url, idToSave, idsToDelete, action });
  }

  public sendPropertyConfiguratorResponse(url: string, flatPropertyDefinitionIds: string[], treePropertyDefinitionIds: string[], action: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/property-configurator`, { url, flatPropertyDefinitionIds, treePropertyDefinitionIds, action });
  }

}
