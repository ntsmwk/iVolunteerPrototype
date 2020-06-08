import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';


@Injectable({
    providedIn: 'root',
})
export class EnumDefinitionService {
    constructor(private http: HttpClient) { }

    getAllEnumDefinitions(marketplace: Marketplace) {
        return this.http.get(`${marketplace.url}/enum-definition/all`);
    }

    getAllEnumDefinitionsForTenant(marketplace: Marketplace, tenantId: string) {
        return this.http.get(`${marketplace.url}/enum-definition/all/${tenantId}`);
    }

    getEnumDefinitionById(marketplace: Marketplace, id: string) {
        return this.http.get(`${marketplace.url}/enum-definition/${id}`);
    }

    getEnumDefinitionByName(marketplace: Marketplace, name: string) {
        return this.http.get(`${marketplace.url}/enum-definition/by-name/${name}`);
    }

    newEnumDefinition(marketplace: Marketplace, enumDefinition: EnumDefinition) {
        return this.http.post(`${marketplace.url}/enum-definition/new`, enumDefinition);
    }

    newEmptyEnumDefinition(marketplace: Marketplace, name: string, description: string, tenantId: string) {
        return this.http.post(`${marketplace.url}/enum-definition/new-empty`, [name, description, tenantId]);
    }

    saveEnumDefinition(marketplace: Marketplace, id: string, enumDefinition: EnumDefinition) {
        return this.http.put(`${marketplace.url}/enum-definition/${id}/save`, enumDefinition);
    }

    deleteEnumDefinition(marketplace: Marketplace, id: string) {
        return this.http.delete(`${marketplace.url}/enum-definition/${id}/delete`);
    }

    deleteEnumDefinitions(marketplace: Marketplace, ids: string[]) {
        return this.http.put(`${marketplace.url}/enum-definition/delete-multiple`, ids);
    }

}
