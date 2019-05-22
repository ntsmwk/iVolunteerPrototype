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

  public getTemplate(marketplace: Marketplace, templateId: string) {
    console.log("calling get " + marketplace.url +"/tasktemplate/user/id");
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }

  public getSubTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string) {
    console.log("calling get " + marketplace.url +"/tasktemplate/user/id");
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}`);
  }

  public getTemplateNames(marketplace: Marketplace, templateId?: string) {
    console.log("calling getTemplateNames only");
    if (isNullOrUndefined(templateId)) {
      return this.http.get(`${marketplace.url}/tasktemplate/user/names`)
    } else {
      return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/names`)
    }
  }


  public newRootSingleTaskTemplate(marketplace: Marketplace, name: string, description: string) {
    console.log("calling post " + marketplace.url + "/tasktemplate/user/new&single");

    let params: string[] = [];
    params.push(name);
    params.push(description);

    console.log(name + " " + description);

    return this.http.post(`${marketplace.url}/tasktemplate/user/new?type=single`, params);
  }

  public newRootMultiTaskTemplate(marketplace: Marketplace, name: string, description: string) {
    console.log("calling new multi template");

    let params: string[] = [];
    params.push(name);
    params.push(description);

    console.log(params);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/new?type=multi`, params);
  }

  public newNestedSingleTaskTemplate(marketplace: Marketplace, templateId: string, name: string, description: string) {
    console.log("calling new multi template");

    let params: string[] = [];
    params.push(name);
    params.push(description);

    console.log(params);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/${templateId}/new`, params);
  }

  public newTaskTemplateFromExisting(marketplace: Marketplace, templateToCopyId: string, name: string, description: string) {
    console.log("calling new  template from existing");
    
    let params: string[] = [];
    params.push(name);
    params.push(description);

    console.log(params);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/${templateToCopyId}/new-copy`, params);
  
  }

  public updateRootTaskTemplate(marketplace: Marketplace, templateId: string, name: string, description: string) {
    console.log("calling put " + marketplace.url + "/tasktemplate/user/update");


    let params: string[] = [];
    params.push(name);
    params.push(description);

    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/update`, params);
  }

  public updateNestedTaskTemplate(marketplace, templateId: string, subtemplateId: string, name: string, description: string) {
    let params: string[] = [];
    params.push(name);
    params.push(description);

    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/update`, params);
  }
  

  public deleteRootTaskTemplate(marketplace: Marketplace, templateId: string) {
    console.log("calling delete " + marketplace.url +"/tasktemplate/user/id/")
    return this.http.delete(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }

  public deleteNestedTaskTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string) {
    console.log("calling delete " + marketplace.url +"/tasktemplate/user/id/")
    return this.http.delete(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}`);
  }


  //Properties inside Template

  //get Properties
  public getPropertyFromSubTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propId: string) {
    console.log("calling get getPropertyFromSubTemplate " + `${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/${propId}`);
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/${propId}`);
  }

  //add Properties
  public addPropertiesToSingleTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    console.log("calling put addPropertiesToTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/addproperties/?type=single");
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/addproperties/`, propIds);
  }

  public addPropertiesToNestedTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propIds: string[]) {
    console.log("calling put addPropertiesToTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/addproperties/?type=nested&subtemplateId=" + subtemplateId);
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/addproperties/`, propIds);
  }


  //delete Properties
  public removePropertiesFromSingleTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    console.log("calling put removePropertiesFromTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/deleteproperties/?type=single");
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/deleteproperties/`, propIds);
  }

  public removePropertiesFromNestedTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propIds: string[]) {
    console.log("calling put removePropertiesFromTemplate " + marketplace.url + "/tasktemplate/user/" + templateId + "/deleteproperties/?type=nested");
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/deleteproperties/`, propIds);
  }


  //update Properties
  public updateProperties(marketplace: Marketplace, templateId: string, subtemplateId: string, properties: Property<any>[]) {
    if (!isNullOrUndefined(subtemplateId)) {
      return this.updatePropertiesInSubtemplate(marketplace, templateId, subtemplateId, properties);
    } else {
      return this.updatePropertiesInTemplate(marketplace, templateId, properties);
    }
  }

  private updatePropertiesInTemplate(marketplace: Marketplace, templateId: string, properties: Property<any>[]) {    
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/updateproperties`, properties);
  }

  private updatePropertiesInSubtemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, properties: Property<any>[]) {    
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/updateproperties`, properties);
  }

}
