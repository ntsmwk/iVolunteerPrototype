// import { Injectable } from '@angular/core';
// import { Marketplace } from 'app/main/content/_model/marketplace';
// import { HttpClient } from '@angular/common/http';
// import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
// import { PropertyConstraint } from 'app/main/content/_model/meta/constraint';

// @Injectable({
//   providedIn: 'root'
// })
// export class FlatPropertyDefinitionService {


//   constructor(
//     private http: HttpClient
//   ) { }


//   getAllPropertyDefinitons(marketplace: Marketplace, tenantId: string) {
//     return this.http.get(`${marketplace.url}/meta/core/property-definition/flat/all/tenant/${tenantId}`);
//   }

//   getPropertyDefinitionById(marketplace: Marketplace, id: string, tenantId: string) {
//     return this.http.get(`${marketplace.url}/meta/core/property-definition/flat/${id}/tenant/${tenantId}`)
//   }

//   createNewPropertyDefinition(marketplace: Marketplace, propertyDefinitions: FlatPropertyDefinition<any>[]) {
//     return this.http.post(`${marketplace.url}/meta/core/property-definition/flat/new`, propertyDefinitions);
//   }

//   updatePropertyDefintion(marketplace: Marketplace, propertyDefinitions: FlatPropertyDefinition<any>[]) {
//     return this.http.put(`${marketplace.url}/meta/core/property-definition/flat/update`, propertyDefinitions);
//   }

//   deletePropertyDefinition(marketplace: Marketplace, id: string) {
//     return this.http.delete(`${marketplace.url}/meta/core/property-definition/flat/${id}/delete`);
//   }

//   addConstraintToPropertyDefinition(marketplace: Marketplace, id: string, constraints: PropertyConstraint<any>[]) {
//     return this.http.patch(`${marketplace.url}/meta/core/property-definition/flat/${id}/add-constraint`, constraints);
//   }

//   removeConstraintFromPropertyDefintion(marketplace: Marketplace, id: string, constraintIds: string[]) {
//     return this.http.patch(`${marketplace.url}/meta/core/property-definition/flat/${id}/remove-constraint`, constraintIds);
//   }





// }
