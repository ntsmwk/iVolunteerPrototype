import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { User, UserRole } from "../_model/user";
import { FileService } from "./file.service";
import { isNullOrUndefined } from "util";

@Injectable({ providedIn: "root" })
export class CoreUserService {
  constructor(private http: HttpClient, private fileService: FileService) {}

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
    return this.http.put(
      `/core/user/all/roles?includeNoRole=${includeNoRole}`,
      roles
    );
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

  getUserProfileImage(user: User) {
    if (isNullOrUndefined(user.profileImagePath)) {
      return "/assets/images/avatars/profile.jpg";
    }
    return user.profileImagePath;
  }

  registerMarketplace(userId: string, marketplaceId: string) {
    return this.http.post(`/core/user/${userId}/register/${marketplaceId}`, {});
  }

  // createUser(user: User, updateMarketplaces: boolean) {
  //   return this.http.post(
  //     `/core/user/new?updateMarketplaces=${updateMarketplaces}`,
  //     user
  //   );
  // }

  updateUser(user: User, updateMarketplaces: boolean) {
    return this.http.put(
      `/core/user/update?updateMarketplaces=${updateMarketplaces}`,
      user
    );
  }

  subscribeUserToTenant(tenantId: string, role: UserRole) {
    return this.http.put(`/core/user/subscribe/${tenantId}`, role, {
      headers: new HttpHeaders({ "Content-Type": "application/json" })
    });
  }

  unsubscribeUserFromTenant(tenantId: string, role: UserRole) {
    return this.http.put(`/core/user/unsubscribe/${tenantId}`, role, {
      headers: new HttpHeaders({ "Content-Type": "application/json" })
    });
  }

  subscribeOtherUserToTenant(tenantId: string, role: UserRole, userId: string) {
    return this.http.put(
      `/core/user/subscribe/${tenantId}/user/${userId}`,
      role,
      { headers: new HttpHeaders({ "Content-Type": "application/json" }) }
    );
  }

  unsubscribeOtherUserFromTenant(
    tenantId: string,
    role: UserRole,
    userId: string
  ) {
    return this.http.put(
      `/core/user/unsubscribe/${tenantId}/user/${userId}`,
      role,
      { headers: new HttpHeaders({ "Content-Type": "application/json" }) }
    );
  }
}
