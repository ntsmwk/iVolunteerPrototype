import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../_model/marketplace";
import { MatchingOperatorRelationship } from "../../_model/matching";

@Injectable({ providedIn: "root" })
export class MatchingOperatorRelationshipService {
  constructor(private httpClient: HttpClient) {}

  getMatchingOperatorRelationshipByMatchingConfiguration(
    marketplace: Marketplace,
    matchingConfiguratorId: string
  ) {
    return this.httpClient.get(
      `${marketplace.url}/matching-operator-relationship/${matchingConfiguratorId}`
    );
  }

  saveMatchingOperatorRelationships(
    marketplace: Marketplace,
    relationships: MatchingOperatorRelationship[]
  ) {
    return this.httpClient.post(
      `${marketplace.url}/matching-operator-relationship`,
      relationships
    );
  }
}
