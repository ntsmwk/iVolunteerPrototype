import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { environment } from 'environments/environment';


@Injectable({
    providedIn: 'root',
})
export class TreePropertyDefinitionService {
    constructor(private http: HttpClient) { }

    getAllPropertyDefinitions() {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/all`);
    }

    getAllPropertyDefinitionsForTenant(tenantId: string) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/all/tenant/${tenantId}`);
    }

    getPropertyDefinitionById(id: string) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/${id}`);
    }

    getPropertyDefinitionByName(name: string) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/by-name/${name}`);
    }

    newPropertyDefinition(treePropertyDefinition: TreePropertyDefinition) {
        return this.http.post(`${environment.CONFIGURATOR_URL}/property-definition/tree/new`, treePropertyDefinition);
    }

    savePropertyDefinition(treePropertyDefinition: TreePropertyDefinition) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/property-definition/tree/save`, treePropertyDefinition);
    }

    deletePropertyDefinition(id: string) {
        return this.http.delete(`${environment.CONFIGURATOR_URL}/property-definition/tree/${id}/delete`);
    }

    deletePropertyDefinitions(ids: string[]) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/property-definition/tree/delete-multiple`, ids);
    }

}
