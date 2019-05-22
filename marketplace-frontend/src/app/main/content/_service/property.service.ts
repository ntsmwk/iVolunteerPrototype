import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Property, PropertyKind, MultiplePropertyRet, PropertyListItem } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';
import { Observable } from 'rxjs';
import { isNullOrUndefined } from 'util';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  constructor(
    private http: HttpClient
  ) { }

  //return list of Properties containing id, name, value and kind
  public getPropertyList(marketplace: Marketplace) {
    console.log("calling get /properties");
    //return this.http.get(`/textProperties`);
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
    console.log("calling get /properties?full");
    return this.http.get(`${marketplace.url}/properties/full`);
  }

  public getPropertyFromList(marketplace: Marketplace, propId: string) {
    console.log("calling get /properties/id");
    return this.http.get(`${marketplace.url}/properties/${propId}`);
  }

  //crating new Properties
  public addSingleProperty(marketplace: Marketplace, property: Property<any>) {
    console.log("calling post /properties/new/single")
    return this.http.post(`${marketplace.url}/properties/new/single`, property);
  }

  public addMultipleProperty(marketplace, property: MultiplePropertyRet) {
    console.log ("calling post /properties/new/multiple");
    return this.http.post(`${marketplace.url}/properties/new/multiple`, property);
  }

  //property manipulation
  public updateProperty(marketplace: Marketplace, property: Property<any>) {
    console.log("calling post /properties/id/update");
    return this.http.put(`${marketplace.url}/properties/${property.id}/update`, property);
  }

  public deleteProperty(marketplace: Marketplace, propId: string) {
    console.log("calling delete /properties/id/delete")
    return this.http.delete(`${marketplace.url}/properties/${propId}`)
  }

  

}
