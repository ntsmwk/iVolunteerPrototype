import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root"
})
export class CoreFlexProdService {
  constructor(private http: HttpClient) {}

  findRegisteredMarketplaces(flexProdUserId: string) {
    return this.http.get(
      `${environment.CORE_URL}/flexprod/${flexProdUserId}/marketplace`
    );
  }

  registerMarketplace(flexProdUserId: string, marketplaceId: string) {
    return this.http.post(
      `${environment.CORE_URL}/flexprod/${flexProdUserId}/register/${marketplaceId}`,
      {}
    );
  }
}
