import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../_model/marketplace';
import { MatchingConfiguration } from '../../_model/meta/configurations';

@Injectable({
  providedIn: 'root'
})
export class MatchingConfigurationService {

  constructor(
    private http: HttpClient
  ) { }

  getAllMatchingConfigurations(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/matching-configuration/all`);
  }

  getOneMatchingConfiguration(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/matching-configuration/${id}`);
  }

  getMatchingConfigurationByClassConfigurationIds(marketplace: Marketplace, producerClassConfiguratorId: string, consumerClassConfiguratorId: string) {
    return this.http.get(`${marketplace.url}/matching-configuration/by-class-configurators/${producerClassConfiguratorId}/${consumerClassConfiguratorId}`);
  }

  getMatchingConfigurationByUnorderedClassConfigurationIds(marketplace: Marketplace, configuratorId1: string, configuratorId2: string) {
    return this.http.get(`${marketplace.url}/matching-configuration/by-class-configurators/${configuratorId1}/${configuratorId2}/unordered`);
  }

  saveMatchingConfiguration(marketplace: Marketplace, matchingConfiguration: MatchingConfiguration) {
    return this.http.post(`${marketplace.url}/matching-configuration/save`, matchingConfiguration);
  }

  deleteMatchingConfiguration(marketplace: Marketplace, id: string) {
    return this.http.delete(`${marketplace.url}/matching-configuration/${id}/delete`);
  }

  deleteMatchingConfigurations(marketplace: Marketplace, ids: string[]) {
    return this.http.put(`${marketplace.url}/matching-configuration/delete-multiple`, ids);
  }

}
