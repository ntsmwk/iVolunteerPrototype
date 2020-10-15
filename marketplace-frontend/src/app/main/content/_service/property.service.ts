import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Property, MultiplePropertyRet } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';
import { isNullOrUndefined } from 'util';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  constructor(
    private http: HttpClient
  ) { }

  //return list of Properties containing id, name, value and kind
  public getPropertyList(marketplace: Marketplace) {;
    return this.http.get(`${marketplace.url}/properties/list`);
  }

  public getPropertyParentItems(marketplace: Marketplace, propId: string, templateId: string, subtemplateId: string) {
    if (!isNullOrUndefined(subtemplateId)) {
      return this.http.get(`${marketplace.url}/properties/${propId}/parents?templateId=${templateId}&subtemplateId=${subtemplateId}`);
    } else {
      return this.http.get(`${marketplace.url}/properties/${propId}/parents?templateId=${templateId}`);
    }
    
  }

  //return actual full Properties
  public getProperties(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/properties/full`);
  }

  public getPropertyFromList(marketplace: Marketplace, propId: string) {
    return this.http.get(`${marketplace.url}/properties/${propId}`);
  }

  //crating new Properties
  public addSingleProperty(marketplace: Marketplace, property: Property<any>) {
    return this.http.post(`${marketplace.url}/properties/new/single`, property);
  }

  public addMultipleProperty(marketplace, property: MultiplePropertyRet) {
    return this.http.post(`${marketplace.url}/properties/new/multiple`, property);
  }

  //property manipulation
  public updateProperty(marketplace: Marketplace, property: Property<any>) {
    return this.http.put(`${marketplace.url}/properties/${property.id}/update`, property);
  }

  public deleteProperty(marketplace: Marketplace, propId: string) {
    return this.http.delete(`${marketplace.url}/properties/${propId}`)
  }

  

}
