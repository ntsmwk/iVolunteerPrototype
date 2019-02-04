import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Property, PropertyKind } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';

// export const data: Property<string>[] = [
//   {id: '1', name: 'First Name', value: 'Tester', defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '2', name: "Last Name", value: "Müller", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '3', name: "Address", value: "Teststraße 20", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '4', name: "Post Code", value: "4020", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '5', name: "City", value: "Linz", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '6', name: "Country", value: "Austria", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '7', name: "Country Code", value: "AT", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '8', name: "E-Mail Address", value: "muzi@katzi.at", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '9', name: "Favorite Color", value: "Blue", defaultValue: this.value, kind: PropertyKind.TEXT},
//   {id: '10', name: "Description", value: "lorem ipsum dolor sit amet bla nie lian lei nal nli fnail enilö jalöi nfi", defaultValue: this.value, kind: PropertyKind.TEXT}
// ];


@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  constructor(
    private http: HttpClient
  ) { }

  // public findAllProperties(): Property<string>[] {
  //   return data;
  // }

  //return list of Properties containing id, name, value and kind
  public findAllFromServer(marketplace: Marketplace) {
    console.log("calling get /properties");
    //return this.http.get(`/textProperties`);
    return this.http.get(`${marketplace.url}/properties/list`);
  }

  //return actual full Properties
  public findAllFromServerFull(marketplace:Marketplace) {
    console.log("calling get /properties?full");
    return this.http.get(`${marketplace.url}/properties/full`);
  }

  public sendPropertytoServer(marketplace: Marketplace, property: Property<string>) {
    console.log("calling post /properties/new")
    return this.http.post(`${marketplace.url}/properties/new`, property);
  }

  public updatePropertyFromServer(marketplace: Marketplace, property: Property<string>) {
    console.log("calling post /properties/id/update");
    return this.http.put(`${marketplace.url}/properties/${property.id}/update`, property);
  }

  public findById(marketplace: Marketplace, propId: string) {
    console.log("calling get /properties/id");
    return this.http.get(`${marketplace.url}/properties/${propId}`);
  }

}
