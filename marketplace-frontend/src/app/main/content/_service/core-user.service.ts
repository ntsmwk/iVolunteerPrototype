import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User, UserRole } from "../_model/user";

@Injectable({ providedIn: "root" })
export class CoreUserService {
  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(`/core/user/all`);
  }

  findAllByTenantId(tenantId: string) {
    return this.http.get(`/core/user/all/tenant/${tenantId}`);
  }

  findAllByRole(role: UserRole) {
    return this.http.get(`/core/user/all/role/${role}`);
  }

  findAllByRoles(roles: UserRole[], includeNoRole: boolean) {
    return this.http.put(`/core/user/all/roles?includeNoRole=${includeNoRole}`, roles);
  }

  findAllByRoleAndTenantId(tenantId: string, role: UserRole) {
    return this.http.get(`/core/user/all/role/${role}/tenant/${tenantId}`);
  }

  findById(userId: string) {
    return this.http.get(`/core/user/${userId}`);
  }

  findByUsername(username: string) {
    return this.http.get(`/core/user/name/${username}`);
  }

  findByIds(userIds: string[]) {
    return this.http.put(`/core/user/find-by-ids`, userIds);
  }

  findRegisteredMarketplaces(userId: string) {
    return this.http.get(`/core/user/${userId}/marketplaces`);
  }

  registerMarketplace(userId: string, marketplaceId: string) {
    return this.http.post(`/core/user/${userId}/register/${marketplaceId}`, {});
  }

  createUser(user: User, updateMarketplaces: boolean) {
    return this.http.post(
      `/core/user/new?updateMarketplaces=${updateMarketplaces}`,
      user
    );
  }

  updateUser(user: User, updateMarketplaces: boolean) {
    return this.http.put(
      `/core/user/update?updateMarketplaces=${updateMarketplaces}`,
      user
    );
  }

  subscribeUserToTenant(
    userId: string,
    marketplaceId: string,
    tenantId: string,
    role: UserRole
  ) {
    console.log(
      `subscribeUserToTenant(${userId},${marketplaceId},${tenantId},${role})`
    );
    return this.http.put(
      `/core/user/${userId}/subscribe/${marketplaceId}/${tenantId}/${role}`,
      {}
    );
  }

  unsubscribeUserFromTenant(
    userId: string,
    marketplaceId: string,
    tenantId: string,
    role: UserRole
  ) {
    console.log(
      `unsubscribeUserFromTenant(${userId},${marketplaceId},${tenantId},${role})`
    );
    return this.http.put(
      `/core/user/${userId}/unsubscribe/${marketplaceId}/${tenantId}/${role}`,
      {}
    );
  }
}
