// import { Injectable } from '@angular/core';
// import { Marketplace } from 'app/main/content/_model/marketplace';
// import { HttpClient } from '@angular/common/http';
// import { PropertyInstance } from 'app/main/content/_model/meta/property/property';


// @Injectable({
//   providedIn: 'root'
// })
// export class PropertyInstanceService {

//   constructor(
//     private http: HttpClient
//   ) { }


//   getAllPropertyInstances(marketplace: Marketplace, classDefinitionId: string) {
//     return this.http.get(`${marketplace.url}/meta/core/property/instance/${classDefinitionId}/all`);
//   }

//   getPropertyInstanceById(marketplace: Marketplace, classDefinitionId: string, propertyInstanceId: string) {
//     return this.http.get(`${marketplace.url}/meta/core/property/instance/${classDefinitionId}/${propertyInstanceId}/`);
//   }

//   updatePropertyInstances(marketplace: Marketplace, classInstanceId: string, propertyInstances: PropertyInstance<any>[]) {
//     return this.http.put(`${marketplace.url}/meta/core/property/instance/${classInstanceId}/update`, propertyInstances);
//   }

//   clearPropertyInstanceValues(marketplace: Marketplace, classInstanceId: string) {
//     return this.http.patch(`${marketplace.url}/meta/core/property/instance/${classInstanceId}/clear/all`, '');
//   }

//   clearPropertyInstanceValue(marketplace: Marketplace, classInstanceId: string, propertyInstanceId: string) {
//     return this.http.patch(`${marketplace.url}/meta/core/property/instance/${classInstanceId}/clear/${propertyInstanceId}`, '');
//   }

// }
