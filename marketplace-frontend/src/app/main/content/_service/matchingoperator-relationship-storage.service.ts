import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';
import { MatchingConfigurator } from '../_model/matching';

@Injectable({
  providedIn: 'root'
})
export class MatchingOperatorRelationshipStorageService {

  constructor(
    private http: HttpClient
  ) { }

  getAllMatchingOperatorRelationshipStorages(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/matching/operator-relationship/all`);
  }

  getOneMatchingOperatorRelationshipStorage(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/matching/operator-relationship/${id}`);
  }

  getMatchingOperatorRelationshipByConfiguratorIds(marketplace: Marketplace, producerClassConfiguratorId: string, consumerClassConfiguratorId: string) {
    return this.http.get(`${marketplace.url}/matching/operator-relationship/by-configurators/${producerClassConfiguratorId}/${consumerClassConfiguratorId}`);
  }

  getMatchingOperatorRelationshipByUnorderedConfiguratorIds(marketplace: Marketplace, configuratorId1: string, configuratorId2: string) {
    return this.http.get(`${marketplace.url}/matching/operator-relationship/by-configurators/${configuratorId1}/${configuratorId2}/unordered`);
  }

  saveMatchingOperatorRelationshipStorage(marketplace: Marketplace, matchingConfigurator: MatchingConfigurator) {
    return this.http.post(`${marketplace.url}/matching/operator-relationship/save`, matchingConfigurator);
  }

  deleteMatchingOperatorRelationshipStorage(marketplace: Marketplace, id: string) {
    return this.http.delete(`${marketplace.url}/matching/operator-relationship/${id}/delete`);
  }

}