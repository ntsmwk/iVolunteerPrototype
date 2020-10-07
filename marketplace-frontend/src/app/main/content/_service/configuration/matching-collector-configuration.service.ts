import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../_model/marketplace';
import { Injectable } from '@angular/core';
import { MatchingEntityMappings } from '../../_model/matching';
import { MatchingConfiguration } from '../../_model/meta/configurations';

@Injectable({
    providedIn: 'root'
})

export class MatchingEntityDataService {
    constructor(
        private http: HttpClient
    ) { }

    // aggregateClassDefinitionsInSingleMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-single`);
    // }

    // aggregateClassDefinitionsInMultipleCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-collections`);
    // }

    getMatchingData(marketplace: Marketplace, matchingConfiguration: MatchingConfiguration) {
        return this.http.put(`${marketplace.url}/matching-entity-data/`, matchingConfiguration);
    }

    // getSavedMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/saved-configuration`);
    // }

    // createMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingEntityMappings) {
    //     return this.http.post(`${marketplace.url}/matching-collector-configuration/new`, matchingCollectorConfiguration);
    // }

    // updateMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingEntityMappings) {
    //     return this.http.put(`${marketplace.url}/matching-collector-configuration/update`, matchingCollectorConfiguration);
    // }

    // deleteMatchingCollectorConfiguration(marketplace: Marketplace, id: string) {
    //     return this.http.delete(`${marketplace.url}/matching-collector-configuration/${id}/delete`);
    // }






}
