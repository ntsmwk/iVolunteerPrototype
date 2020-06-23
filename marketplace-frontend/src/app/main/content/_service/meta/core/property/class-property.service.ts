import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassProperty } from 'app/main/content/_model/meta/property';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ClassPropertyService {
  constructor(
    private http: HttpClient
  ) { }

  getClassProperties(marketplace: Marketplace, classDefintionId: string, tenantId: string): Promise<any> {
    return this.http.get(`${marketplace.url}/meta/core/property/class/${classDefintionId}/all/tenant/${tenantId}`)
                 .toPromise();
                 //.catch(this.handleError);
  }
    // return this.getAllClassPropertiesFromClass(marketplace, classDefintionId, tenantId);

  getAllClassPropertiesFromClass(marketplace: Marketplace, classDefintionId: string, tenantId: string) {
    return this.http.get(`${marketplace.url}/meta/core/property/class/${classDefintionId}/all/tenant/${tenantId}`);
  }

  getClassPropertyById(marketplace: Marketplace, classDefintionId: string, classPropertyId: string, tenantId: string) {
    return this.http.get(`${marketplace.url}/meta/core/property/class/${classDefintionId}/${classPropertyId}/tenant/${tenantId}`)
  }

  updateClassProperty(marketplace: Marketplace, classDefintionId: string, classProperty: ClassProperty<any>) {
    return this.http.put(`${marketplace.url}/meta/core/property/class/${classDefintionId}/update`, classProperty);
  }

}
