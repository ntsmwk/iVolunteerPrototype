import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(
    private http: HttpClient
  ) { }

  getAllRelationships() {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/relationship/all`);
  }

  getRelationshipById(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/relationship/${id}`);
  }

  getRelationshipsById(ids: string[]) {
    if (!isNullOrUndefined(ids)) {
      return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/relationship/multiple`, ids);
    } else {
      return of(null);
    }
  }

  getRelationshipsByStartId(startId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/relationship/start/${startId}/all`);
  }

  getRelationshipsByEndId(endId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/meta/core/relationship/end/${endId}/all`);
  }

  addRelationshipsInOneGo(relationships: Relationship[]) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/meta/core/relationship/add`, relationships);
  }

  addAndUpdateRelationships(relationships: Relationship[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/relationship/add-or-update`, relationships);
  }

  deleteRelationships(ids: String[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/meta/core/relationship/delete`, ids);
  }

}
