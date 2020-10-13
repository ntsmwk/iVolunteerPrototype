import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassConfiguration } from '../../_model/meta/configurations';

@Injectable({
  providedIn: 'root'
})
export class ClassConfigurationService {

  constructor(
    private http: HttpClient
  ) { }

  getAllClassConfigurations(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/class-configuration/all`);
  }

  getAllClassConfigurationById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/class-configuration/${id}`);
  }

  getClassConfigurationByName(marketplace: Marketplace, name: string) {
    return this.http.get(`${marketplace.url}/class-configuration/by-name/${name}`);
  }

  getClassConfigurationsByTenantId(marketplace: Marketplace, tenantId: string) {
    return this.http.get(`${marketplace.url}/class-configuration/all/tenant/${tenantId}`);

  }

  getAllForClassConfigurationInOne(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/class-configuration/all-in-one/${id}`);
  }

  createNewEmptyClassConfiguration(marketplace: Marketplace, name: string, description: string) {
    return this.http.post(`${marketplace.url}/class-configuration/new-empty`, [name, description]);
  }

  createNewClassConfiguration(marketplace: Marketplace, tenantId: string, name: string, description: string) {
    const params: string[] = [];
    params.push(tenantId);
    params.push(name);
    params.push(description);

    return this.http.post(`${marketplace.url}/class-configuration/new`, params);
  }

  saveClassConfiguration(marketplace: Marketplace, classConfiguration: ClassConfiguration) {
    return this.http.put(`${marketplace.url}/class-configuration/save`, classConfiguration);
  }


  saveClassConfigurationMeta(marketplace: Marketplace, id: string, name: string, description: string) {
    return this.http.put(`${marketplace.url}/class-configuration/${id}/save-meta/`, [name, description]);
  }

  deleteClassConfiguration(marketplace: Marketplace, id: string) {
    return this.http.delete(`${marketplace.url}/class-configuration/${id}/delete`);
  }

  deleteClassConfigurations(marketplace: Marketplace, ids: string[]) {
    return this.http.put(`${marketplace.url}/class-configuration/delete-multiple`, ids);
  }



}