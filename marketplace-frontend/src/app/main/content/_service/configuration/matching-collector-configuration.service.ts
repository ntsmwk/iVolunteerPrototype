import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../_model/marketplace';
import { Injectable } from '@angular/core';
import { MatchingCollectorConfiguration } from '../../_model/configurations';

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


    // @PostMapping("matching-collector-configuration/new")
    // private MatchingCollectorConfiguration createMatchingCollectorConfiguration(@RequestBody MatchingCollectorConfiguration configuration) {
    // 	return updateMatchingCollectorConfiguration(configuration);
    // }
    createMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingCollectorConfiguration) {
        return this.http.post(`${marketplace.url}/matching-collector-configuration/new`, matchingCollectorConfiguration);
    }

    // @PutMapping("matching-collector-configuration/update")
    // private MatchingCollectorConfiguration updateMatchingCollectorConfiguration(@RequestBody MatchingCollectorConfiguration configuration) {
    // 	return matchingCollectorConfigurationRepository.save(configuration);
    // }
    updateMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingCollectorConfiguration) {
        return this.http.put(`${marketplace.url}/matching-collector-configuration/update`, matchingCollectorConfiguration);
    }

    // @DeleteMapping("matching-collector-configuration/{id}/delete")
    // private void deleteMatchingCollectorConfiguration(@PathVariable("id") String id) {
    // 	matchingCollectorConfigurationRepository.delete(id);
    // }
    deleteMatchingCollectorConfiguration(marketplace: Marketplace, id: string) {
        return this.http.delete(`${marketplace.url}/matching-collector-configuration/${id}/delete`);
    }






}
