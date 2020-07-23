import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../_model/marketplace";

@Injectable({ providedIn: "root" })
export class ServiceNameService {
  constructor(private httpClient: HttpClient) {}

  getMatchingOperatorRelationshipByMatchingConfiguration(
    marketplace: Marketplace,
    matchingConfiguratorId: string
  ) {
    return this.httpClient.get(
      `${marketplace.url}/matching-operator-relationship/${matchingConfiguratorId}`
    );
  }
}
