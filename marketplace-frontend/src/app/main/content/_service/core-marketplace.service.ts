import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { isNullOrUndefined } from "util";

@Injectable({
  providedIn: "root",
})
export class MarketplaceService {
  constructor(private http: HttpClient) {}

  findAll() {
    return this.http.get(`/core/marketplace`);
  }

  findById(marketplaceId: string) {
    return this.http.get(`/core/marketplace/${marketplaceId}`);
  }

  save(marketplace: Marketplace) {
    if (isNullOrUndefined(marketplace.id)) {
      return this.http.post(`/core/marketplace`, marketplace);
    }
    return this.http.put(`/core/marketplace/${marketplace.id}`, marketplace);
  }
}
