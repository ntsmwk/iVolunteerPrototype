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

  getAllClassConfigurationsSortedAsc(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/class-configuration/all?sorted=asc`);
  }

  getAllClassConfigurationsSortedDesc(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/class-configuration/all?sorted=desc`);

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

  deleteClassConfiguration(marketplace: Marketplace, id: string) {
    return this.http.delete(`${marketplace.url}/class-configuration/${id}/delete`);
  }

  deleteClassConfigurations(marketplace: Marketplace, ids: string[]) {
    return this.http.put(`${marketplace.url}/class-configuration/delete-multiple`, ids);
  }



}