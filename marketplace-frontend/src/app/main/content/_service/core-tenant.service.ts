import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Tenant } from "../_model/tenant";
import { FileService } from "./file.service";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root"
})
export class TenantService {
  constructor(private http: HttpClient, private fileService: FileService) {}

  findAll() {
    return this.http.get(`${environment.CORE_URL}/tenant`);
  }

  findByName(tenantName: string) {
    return this.http.get(`${environment.CORE_URL}/tenant/name/${tenantName}`, {
      responseType: "text"
    });
  }

  findById(tenantId: string) {
    return this.http.get(`${environment.CORE_URL}/tenant/id/${tenantId}`);
  }

  findByUserId(userId: string) {
    return this.http.get(`${environment.CORE_URL}/tenant/user/${userId}`);
  }

  findByMarketplace(marketplaceId: string) {
    return this.http.get(
      `${environment.CORE_URL}/tenant/marketplace/${marketplaceId}`
    );
  }

  createTenant(tenant: Tenant) {
    return this.http.post(`${environment.CORE_URL}/tenant/new/not-x`, tenant);
  }

  updateTenant(tenant: Tenant) {
    return this.http.put(`${environment.CORE_URL}/tenant/update`, tenant);
  }

  initHeader(tenant: Tenant) {
    (<HTMLElement>document.querySelector(".header")).style.background =
      tenant.primaryColor;
  }
}
