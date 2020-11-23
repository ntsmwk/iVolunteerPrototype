import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../../../_model/marketplace";
import { ClassDefinition, ClassArchetype } from "../../../../_model/meta/class";
import { isNullOrUndefined } from "util";
import { of } from "rxjs";
import { Relationship } from "app/main/content/_model/meta/relationship";
// import { FormConfigurationPreviewRequest } from "app/main/content/_model/meta/form";

@Injectable({
  providedIn: "root"
})
export class ClassDefinitionService {
  constructor(private http: HttpClient) { }

  getAllClassDefinitions(marketplace: Marketplace, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/all/tenant/${tenantId}`
    );
  }

  getClassDefinitionById(marketplace: Marketplace, id: string, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/${id}/tenant/${tenantId}`
    );
  }

  getClassDefinitionsById(marketplace: Marketplace, ids: string[], tenantId: string) {
    if (!isNullOrUndefined(ids)) {
      return this.http.put(
        `${marketplace.url}/meta/core/class/definition/multiple/tenant/${tenantId}`,
        ids
      );
    } else {
      return of(null);
    }
  }

  getByArchetype(
    marketplace: Marketplace,
    archetype: ClassArchetype,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/definition/archetype/${archetype}/tenant/${tenantId}`
    );
  }

  getFormConfigurations(marketplace: Marketplace, ids: string[]) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/definition/form-configuration`,
      ids
    );
  }

}
