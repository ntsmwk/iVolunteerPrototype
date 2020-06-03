import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { EnumConfiguration } from 'app/main/content/_model/meta/configurations';


@Injectable({
    providedIn: "root",
})
export class EnumConfigurationService {
    constructor(private http: HttpClient) { }

    getAllEnumConfigurations(marketplace: Marketplace) {
        return this.http.get(`${marketplace.url}/enum-configuration/all`);
    }

    getAllEnumConfigurationsForTenant(marketplace: Marketplace, tenantId: string) {
        return this.http.get(`${marketplace.url}/enum-configuration/all/${tenantId}`);
    }

    getEnumConfigurationById(marketplace: Marketplace, id: string) {
        return this.http.get(`${marketplace.url}/enum-configuration/${id}`);
    }

    getEnumConfigurationByName(marketplace: Marketplace, name: string) {
        return this.http.get(`${marketplace.url}/enum-configuration/by-name/${name}`);
    }

    newEnumConfiguration(marketplace: Marketplace, enumConfiguration: EnumConfiguration) {
        return this.http.post(`${marketplace.url}/enum-configuration/new`, enumConfiguration);
    }

    newEmptyEnumConfiguration(marketplace: Marketplace, name: string, description: string) {
        return this.http.post(`${marketplace.url}/enum-configuration/new-empty`, [name, description]);
    }

    saveEnumConfiguration(marketplace: Marketplace, id: string, enumConfiguration: EnumConfiguration) {
        return this.http.put(`${marketplace.url}/enum-configuration/${id}/save`, enumConfiguration);
    }

    deleteEnumConfiguration(marketplace: Marketplace, id: string) {
        return this.http.delete(`${marketplace.url}/enum-configuration/${id}/delete`);
    }

}
