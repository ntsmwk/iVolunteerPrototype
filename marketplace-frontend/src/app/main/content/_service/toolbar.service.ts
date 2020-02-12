import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';
import { MatchingOperatorRelationshipStorage } from '../_model/matching';

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

    getMatchingOperatorRelationshipByConfiguratorIds(marketplace: Marketplace, producerConfiguratorId: string, consumerConfiguratorId: string) {
      return this.http.get(`${marketplace.url}/matching/operator-relationship/by-configurators/${producerConfiguratorId}/${consumerConfiguratorId}`);
    }

    saveMatchingOperatorRelationshipStorage(marketplace: Marketplace, matchingOperatorRelationshipStorage: MatchingOperatorRelationshipStorage) {
      return this.http.post(`${marketplace.url}/matching/operator-relationship/save`, matchingOperatorRelationshipStorage);
    }

    deleteMatchingOperatorRelationshipStorage(marketplace: Marketplace, id: string) {
      return this.http.delete(`${marketplace.url}/matching/operator-relationship/${id}/delete`);
    }

  }