import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { ConfigurableClass, ConfigurableObject } from "../_model/configurables/Configurable";

@Injectable({
    providedIn: 'root'
  })
  export class ConfiguratorService {
  
    constructor(
      private http: HttpClient
    ) { }

    test(marketplace: Marketplace) {
        return this.http.get(`${marketplace.url}/configurator/test`);
    }

    getAllConfigClasses(marketplace: Marketplace) {

      return this.http.get(`${marketplace.url}/configclass/all`);

    }

    getConfigClassById(marketplace: Marketplace, id: string) {

      return this.http.get(`${marketplace.url}/configclass/${id}`);

    }

    createNewConfigClass(marketplace: Marketplace, clazz: ConfigurableClass) {

      return this.http.post(`${marketplace.url}/configclass/new`, clazz);

    }

    deleteConfigClass(marketplace: Marketplace, id: string) {
      return this.http.delete(`${marketplace.url}/configclass/${id}/delete`);

    }

    addObjectsToConfigClass(marketplace: Marketplace, id: string, objectsToAdd: ConfigurableObject[]) {
      return this.http.put(`${marketplace.url}/configclass/${id}/add-objects`, objectsToAdd);

    }

    removeObjectFromConfigClass(marketplace: Marketplace, id: string, objectIdsToRemove: string[]) {
      return this.http.put(`${marketplace.url}/configclass/${id}/remove-objects`, objectIdsToRemove);
    }


  }