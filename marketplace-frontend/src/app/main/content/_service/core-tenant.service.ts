import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Tenant } from "../_model/tenant";
import { FileService } from "./file.service";

@Injectable({
  providedIn: "root"
})
export class TenantService {
  constructor(private http: HttpClient, private fileService: FileService) {}

  findAll() {
    return this.http.get(`/core/tenant`);
  }

  findByName(tenantName: string) {
    return this.http.get(`/core/tenant/name/${tenantName}`, {
      responseType: "text"
    });
  }

  findById(tenantId: string) {
    return this.http.get(`/core/tenant/id/${tenantId}`);
  }

  findByUserId(userId: string) {
    return this.http.get(`/core/tenant/user/${userId}`);
  }

  findByMarketplace(marketplaceId: string) {
    return this.http.get(`/core/tenant/marketplace/${marketplaceId}`);
  }

  createTenant(tenant: Tenant) {
    return this.http.post(`/core/tenant/new`, tenant);
  }

  updateTenant(tenant: Tenant) {
    return this.http.put(`/core/tenant/update`, tenant);
  }

  initHeader(tenant: Tenant) {
    (<HTMLElement>document.querySelector(".header")).style.background =
      tenant.primaryColor;
  }
}
