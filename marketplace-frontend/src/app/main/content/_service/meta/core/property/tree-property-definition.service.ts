// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Marketplace } from 'app/main/content/_model/marketplace';
// import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';
// import { isNullOrUndefined } from 'util';


// @Injectable({
//     providedIn: 'root',
// })
// export class TreePropertyDefinitionService {
//     constructor(private http: HttpClient) { }

//     getAllPropertyDefinitions(marketplace: Marketplace) {
//         return this.http.get(`${marketplace.url}/meta/core/property-definition/tree/all`);
//     }

//     getAllPropertyDefinitionsForTenant(marketplace: Marketplace, tenantId: string) {
//         return this.http.get(`${marketplace.url}/meta/core/property-definition/tree/all/${tenantId}`);
//     }

//     getPropertyDefinitionById(marketplace: Marketplace, id: string) {
//         return this.http.get(`${marketplace.url}/meta/core/property-definition/tree/${id}`);
//     }

//     getPropertyDefinitionByName(marketplace: Marketplace, name: string) {
//         return this.http.get(`${marketplace.url}/meta/core/property-definition/tree/by-name/${name}`);
//     }

//     newPropertyDefinition(marketplace: Marketplace, treePropertyDefinition: TreePropertyDefinition, tenantId?: string) {
//         if (!isNullOrUndefined(tenantId)) {
//             treePropertyDefinition.tenantId = tenantId;
//         }
//         return this.http.post(`${marketplace.url}/meta/core/property-definition/tree/new`, treePropertyDefinition);
//     }

//     savePropertyDefinition(marketplace: Marketplace, treePropertyDefinition: TreePropertyDefinition) {
//         return this.http.put(`${marketplace.url}/meta/core/property-definition/tree/save`, treePropertyDefinition);
//     }

//     deletePropertyDefinition(marketplace: Marketplace, id: string) {
//         return this.http.delete(`${marketplace.url}/meta/core/property-definition/tree/${id}/delete`);
//     }

//     deletePropertyDefinitions(marketplace: Marketplace, ids: string[]) {
//         return this.http.put(`${marketplace.url}/meta/core/property-definition/tree/delete-multiple`, ids);
//     }

// }
