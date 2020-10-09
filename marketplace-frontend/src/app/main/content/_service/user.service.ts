import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { UserRole } from "../_model/user";

@Injectable({
  providedIn: "root"
})
export class UserService {
  constructor(private http: HttpClient) {}

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/user/all`);
  }

  findAllByRole(marketplace: Marketplace, role: UserRole) {
    return this.http.get(`${marketplace.url}/user/all/role/${role}`);
  }

  findAllByTenantId(marketplace: Marketplace, tenantId: string) {
    return this.http.get(`${marketplace.url}/user/all/tenant/${tenantId}`);
  }

  findAllByRoleAndTenantId(
    marketplace: Marketplace,
    role: UserRole,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/user/all/tenant/${tenantId}/role/${role}`
    );
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/user/${id}`);
  }

  findByName(marketplace: Marketplace, name: string) {
    return this.http.get(`${marketplace.url}/user/username/${name}`);
  }
}
