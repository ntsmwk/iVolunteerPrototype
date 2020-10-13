import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../../../../_model/marketplace';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(
    private http: HttpClient
  ) { }

  getAllRelationships(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/all`);
  }

  getRelationshipById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/${id}`);
  }

  getRelationshipsById(marketplace: Marketplace, ids: string[]) {
    if (!isNullOrUndefined(ids)) {
      return this.http.put(`${marketplace.url}/meta/core/relationship/multiple`, ids);
    } else {
      return of(null);
    }
  }

  getRelationshipsByStartId(marketplace: Marketplace, startId: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/start/${startId}/all`);
  }

  getRelationshipsByEndId(marketplace: Marketplace, endId: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/end/${endId}/all`);
  }

  addRelationshipsInOneGo(marketplace: Marketplace, relationships: Relationship[]) {
    return this.http.post(`${marketplace.url}/meta/core/relationship/add`, relationships);
  }

  addAndUpdateRelationships(marketplace: Marketplace, relationships: Relationship[]) {
    return this.http.put(`${marketplace.url}/meta/core/relationship/add-or-update`, relationships);
  }

  deleteRelationships(marketplace: Marketplace, ids: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/relationship/delete`, ids);
  }

}
