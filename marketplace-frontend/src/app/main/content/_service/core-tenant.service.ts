import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Tenant } from "../_model/tenant";
import { ImageService } from "./image.service";

@Injectable({
  providedIn: "root",
})
export class TenantService {
  constructor(private http: HttpClient, private imageService: ImageService) {}

  findAll() {
    return this.http.get(`/core/tenant`);
  }

  findByName(tenantName: string) {
    return this.http.get(`/core/tenant/name/${tenantName}`, {
      responseType: "text",
    });
  }

  findById(tenantId: string) {
    return this.http.get(`/core/tenant/${tenantId}`);
  }

  findByVolunteerId(volunteerId: string) {
    return this.http.get(`/core/tenant/volunteer/${volunteerId}`);
  }

  findByMarketplace(marketplaceId: string) {
    return this.http.get(`/core/tenant/marketplace/${marketplaceId}`);
  }

  save(tenant: Tenant) {
    if (tenant.id == null) {
      return this.http.post(`/core/tenant`, tenant);
    }
    return this.http.put(`/core/tenant/${tenant.id}`, tenant);
  }

  getTenantImage(tenant: Tenant) {
    return this.imageService.getImgSourceFromBytes(tenant.image);
  }

  initHeader(tenant: Tenant) {
    (<HTMLElement>document.querySelector('.header')).style.background =
      tenant.primaryColor;
  }
}
