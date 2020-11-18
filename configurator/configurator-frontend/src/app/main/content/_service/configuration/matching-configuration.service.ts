import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { MatchingConfiguration } from "../../_model/configurator/configurations";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root"
})
export class MatchingConfigurationService {
  constructor(private http: HttpClient) { }

  getAllMatchingConfigurations() {
    return this.http.get(`${environment.CONFIGURATOR_URL}/matching-configuration/all`);
  }

  getAllMatchingConfigurationsByTenantId(tenantId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/matching-configuration/all/tenant/${tenantId}`);
  }

  getOneMatchingConfiguration(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/matching-configuration/${id}`);
  }

  getMatchingConfigurationByClassConfigurationIds(leftClassConfiguratorId: string, rightClassConfiguratorId: string) {
    return this.http.get(
      `${environment.CONFIGURATOR_URL}/matching-configuration/by-class-configurators/${leftClassConfiguratorId}/${rightClassConfiguratorId}`
    );
  }

  getMatchingConfigurationByUnorderedClassConfigurationIds(configuratorId1: string, configuratorId2: string) {
    return this.http.get(
      `${environment.CONFIGURATOR_URL}/matching-configuration/by-class-configurators/${configuratorId1}/${configuratorId2}/unordered`
    );
  }

  saveMatchingConfiguration(matchingConfiguration: MatchingConfiguration) {
    return this.http.post(
      `${environment.CONFIGURATOR_URL}/matching-configuration/save`,
      matchingConfiguration
    );
  }

  deleteMatchingConfiguration(id: string) {
    return this.http.delete(
      `${environment.CONFIGURATOR_URL}/matching-configuration/${id}/delete`
    );
  }

  deleteMatchingConfigurations(ids: string[]) {
    return this.http.put(
      `${environment.CONFIGURATOR_URL}/matching-configuration/delete-multiple`,
      ids
    );
  }
}
