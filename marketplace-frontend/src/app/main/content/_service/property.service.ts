import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Property, PropertyKind } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';

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

  //return actual full Properties
  public getProperties(marketplace:Marketplace) {
    console.log("calling get /properties?full");
    return this.http.get(`${marketplace.url}/properties/full`);
  }

  public getProperty(marketplace: Marketplace, propId: string) {
    console.log("calling get /properties/id");
    return this.http.get(`${marketplace.url}/properties/${propId}`);
  }

  //property manipulation
  public addProperty(marketplace: Marketplace, property: Property<string>) {
    console.log("calling post /properties/new")
    return this.http.post(`${marketplace.url}/properties/new`, property);
  }

  public updateProperty(marketplace: Marketplace, property: Property<string>) {
    console.log("calling post /properties/id/update");
    return this.http.put(`${marketplace.url}/properties/${property.id}/update`, property);
  }

  public deleteProperty(marketplace: Marketplace, propId: string) {
    console.log("calling delete /properties/id/delete")
    //TODO
  }

  

}
