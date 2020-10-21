import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { isNullOrUndefined } from "util";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root",
})
export class MarketplaceService {
  constructor(private http: HttpClient) {}

  findAll() {
    return this.http.get(`${environment.CORE_URL}/marketplace`);
  }

  findById(marketplaceId: string) {
    return this.http.get(
      `${environment.CORE_URL}/marketplace/${marketplaceId}`
    );
  }

  save(marketplace: Marketplace) {
    if (isNullOrUndefined(marketplace.id)) {
      return this.http.post(`${environment.CORE_URL}/marketplace`, marketplace);
    }
    return this.http.put(
      `${environment.CORE_URL}/marketplace/${marketplace.id}`,
      marketplace
    );
  }
}
