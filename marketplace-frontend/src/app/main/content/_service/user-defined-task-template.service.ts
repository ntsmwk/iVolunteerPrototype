import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, Property } from '../_model/properties/Property';
import { Observable } from 'rxjs';
import { isNullOrUndefined } from 'util';
import { UserDefinedTaskTemplate } from '../_model/user-defined-task-template';

@Injectable({
  providedIn: 'root'
})
export class UserDefinedTaskTemplateService {

  constructor(
    private http: HttpClient
  ) { }

  //Task Templates

  public getAllTaskTemplates(marketplace: Marketplace, stub: boolean) {

    return this.http.get(`${marketplace.url}/tasktemplate/user?stub=${stub}`);
  }

  public getTemplate(marketplace: Marketplace, templateId: string) {
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }

  public getSubTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string) {
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}`);
  }

  public getTemplateNames(marketplace: Marketplace, templateId?: string) {
    if (isNullOrUndefined(templateId)) {
      return this.http.get(`${marketplace.url}/tasktemplate/user/names`)
    } else {
      return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/names`)
    }
  }


  public newRootSingleTaskTemplate(marketplace: Marketplace, name: string, description: string) {
    let params: string[] = [];
    params.push(name);
    params.push(description);

    return this.http.post(`${marketplace.url}/tasktemplate/user/new?type=single`, params);
  }

  public newRootMultiTaskTemplate(marketplace: Marketplace, name: string, description: string) {
    let params: string[] = [];
    params.push(name);
    params.push(description);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/new?type=multi`, params);
  }

  public newNestedSingleTaskTemplate(marketplace: Marketplace, templateId: string, name: string, description: string) {
    let params: string[] = [];
    params.push(name);
    params.push(description);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/${templateId}/new`, params);
  }

  public newTaskTemplateFromExisting(marketplace: Marketplace, templateToCopyId: string, name: string, description: string) {    
    let params: string[] = [];
    params.push(name);
    params.push(description);
  
    return this.http.post(`${marketplace.url}/tasktemplate/user/${templateToCopyId}/new-copy`, params);
  
  }

  public updateRootTaskTemplate(marketplace: Marketplace, templateId: string, name: string, description: string) {
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
    return this.http.delete(`${marketplace.url}/tasktemplate/user/${templateId}`);
  }

  public deleteNestedTaskTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string) {
    return this.http.delete(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}`);
  }
  
  public updateTemplateOrderNested(marketplace: Marketplace, templateId: string, templates: UserDefinedTaskTemplate[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/updatetemplateorder`, templates);

  }

  //Properties inside Template

  //get Properties
  public getPropertyFromSubTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propId: string) {
    return this.http.get(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/${propId}`);
  }

  //add Properties
  public addPropertiesToSingleTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/addproperties/`, propIds);
  }

  public addPropertiesToNestedTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/addproperties/`, propIds);
  }

  //delete Properties
  public removePropertiesFromSingleTemplate(marketplace: Marketplace, templateId: string, propIds: string[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/deleteproperties/`, propIds);
  }

  public removePropertiesFromNestedTemplate(marketplace: Marketplace, templateId: string, subtemplateId: string, propIds: string[]) {
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

  public updatePropertyOrderSingle(marketplace: Marketplace, templateId: string, properties: Property<any>[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/updatepropertyorder`, properties);
  }

  public updatePropertyOrderNested(marketplace: Marketplace, templateId: string, subtemplateId: string, properties: Property<any>[]) {
    return this.http.put(`${marketplace.url}/tasktemplate/user/${templateId}/${subtemplateId}/updatepropertyorder`, properties);
  }


}
