import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../_model/marketplace';
import { Injectable } from '@angular/core';
import { MatchingCollectorConfiguration } from '../../_model/meta/configurations';

@Injectable({
    providedIn: 'root'
})

export class MatchingCollectorConfigurationService {
    constructor(
        private http: HttpClient
    ) { }

    aggregateClassDefinitionsInSingleMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
        return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-single`);
    }

    aggregateClassDefinitionsInMultipleCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
        return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-collections`);
    }

    getSavedMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
        return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/saved-configuration`);
    }

    createMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingCollectorConfiguration) {
        return this.http.post(`${marketplace.url}/matching-collector-configuration/new`, matchingCollectorConfiguration);
    }

    updateMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingCollectorConfiguration) {
        return this.http.put(`${marketplace.url}/matching-collector-configuration/update`, matchingCollectorConfiguration);
    }

    deleteMatchingCollectorConfiguration(marketplace: Marketplace, id: string) {
        return this.http.delete(`${marketplace.url}/matching-collector-configuration/${id}/delete`);
    }






}
