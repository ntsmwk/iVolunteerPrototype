import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatchingOperatorRelationship } from '../../_model/matching';
import { environment } from 'environments/environment';

@Injectable({ providedIn: 'root' })
export class MatchingOperatorRelationshipService {
  constructor(private httpClient: HttpClient) { }

  getMatchingOperatorRelationshipByMatchingConfiguration(matchingConfiguratorId: string) {
    return this.httpClient.get(
      `${environment.CONFIGURATOR_URL}/matching-operator-relationship/${matchingConfiguratorId}`
    );
  }

  saveMatchingOperatorRelationships(relationships: MatchingOperatorRelationship[], matchingConfiguratorId: string) {
    return this.httpClient.post(
      `${environment.CONFIGURATOR_URL}/matching-operator-relationship/${matchingConfiguratorId}`,
      relationships
    );
  }
}
