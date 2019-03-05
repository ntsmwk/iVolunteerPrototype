import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, Property } from '../_model/properties/Property';
import { Observable } from 'rxjs';
import { isNullOrUndefined } from 'util';

@Injectable({
  providedIn: 'root'
})
export class UserDefinedTaskTemplateService {

  constructor(
    private http: HttpClient
  ) { }

  // public findAllProperties(): Property<string>[] {
  //   return data;
  // }


  //Task Templates

  public getAllTaskTemplates(marketplace: Marketplace, stub: boolean) {
    console.log("calling get /tasktemplate/user");
    //return this.http.get(`/textProperties`);
    return this.http.get(`${marketplace.url}/tasktemplate/user?stub=${stub}`);
  }

  // public sendPropertytoServer(marketplace: Marketplace, property: Property<string>) {
  //   console.log("calling post /properties/new")
  //   return this.http.post(`${marketplace.url}/properties/new`, property);
  // }

  // public updatePropertyFromServer(marketplace: Marketplace, property: Property<string>) {
  //   console.log("calling post /properties/id/update");
  //   return this.http.put(`${marketplace.url}/properties/${property.id}/update`, property);
  // }

  public getTaskTemplate(marketplace: Marketplace, templateId: string) {
    console.log("calling get " + marketplace.url +"/tasktemplate/user/id");
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }

  public newTaskTemplate(marketplace: Marketplace, name: string, description: string) {
    console.log("calling post " + marketplace.url + "/tasktemplate/user/new");

    let params: string[] = [];
    params.push(name);
    params.push(description);

    console.log(name + " " + description);

    return this.http.post(`${marketplace.url}/tasktemplate/user/new`, params);
  }

  public updateTaskTemplate(marketplace: Marketplace, templateId: string, name: string, description: string) {
    console.log("calling put " + marketplace.url + "/tasktemplate/user/update");


    let params: string[] = [];
    params.push(name);
    params.push(description);

    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}`, params);
  }

  public deleteTaskTemplate(marketplace: Marketplace, templateId: string) {
    console.log("calling delete " + marketplace.url +"/tasktemplate/user/id/")
    return this.http.delete(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }


  //Properties inside Template

  public addPropertiesToTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    console.log("calling put addPropertiesToTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/addproperties")
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/addproperties`, propIds);
  }

  public removePropertiesFromTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    console.log("calling put removePropertiesFromTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/deleteproperties");
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/deleteproperties`, propIds);
  }

  public updatePropertiesInTemplate(marketplace: Marketplace, templateId: string, properties: Property<any>[]) {
    console.log("calling put updatePropertiesInTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/updateproperties");
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/updateproperties`, properties);
  }

}
