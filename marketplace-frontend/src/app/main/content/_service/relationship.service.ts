import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";

@Injectable({
    providedIn: 'root'
  })
  export class RelationshipService {
  
    constructor(
      private http: HttpClient
    ) { }

    getAllRelationships(marketplace: Marketplace) {
      return this.http.get(`${marketplace.url}/configclass/relationship/all`);
    }

    getRelationshipById(marketplace: Marketplace, id: string) {
      return this.http.get(`${marketplace.url}/configclass/relationship/${id}`);
    }

    getRelationshipsByStartId(marketplace: Marketplace, startId: string) {
      return this.http.get(`${marketplace.url}/configclass/relationship/start/${startId}/all`);
    }

    getRelationshipsByEndId(marketplace: Marketplace, endId: string) {
      return this.http.get(`${marketplace.url}/configclass/relationship/end/${endId}/all`);

    }

  }