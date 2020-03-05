import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Configurator } from "app/main/content/_model/meta/Configurator";
import { Marketplace } from "app/main/content/_model/marketplace";

@Injectable({
  providedIn: "root"
})
export class ConfiguratorService {
  constructor(private http: HttpClient) {}

  getAllConfigurators(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/meta/configurator/all`);
  }

  getConfiguratorById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/meta/configurator/${id}`);
  }

  getConfiguratorByName(marketplace: Marketplace, name: string) {
    return this.http.get(
      `${marketplace.url}/meta/configurator/by-name/${name}`
    );
  }

  createNewEmptyConfigurator(
    marketplace: Marketplace,
    name: string,
    description: string
  ) {
    let params: string[] = [name, description];
    return this.http.post(
      `${marketplace.url}/meta/configurator/new-empty`,
      params
    );
  }

  createNewConfigurator(marketplace: Marketplace, configurator: Configurator) {
    return this.http.post(
      `${marketplace.url}/meta/configurator/new`,
      configurator
    );
  }

  saveConfigurator(marketplace: Marketplace, configurator: Configurator) {
    console.log("Save configurator");
    console.log(configurator);
    return this.http.put(
      `${marketplace.url}/meta/configurator/save`,
      configurator
    );
  }

  deleteConfigurator(marketplace: Marketplace, id: string) {
    return this.http.delete(
      `${marketplace.url}/meta/configurator/${id}/delete`
    );
  }
}
