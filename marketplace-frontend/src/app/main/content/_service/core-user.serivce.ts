import {
  Injectable
} from "@angular/core";
import {
  HttpClient
} from "@angular/common/http";
import {
  User, UserRole
} from "../_model/user";

@Injectable({ providedIn: "root", }) export class CoreUserService {
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

  findAllByRoleAndTenantId(tenantId: string, role: UserRole) {
    return this.http.get(`/core/user/all/role/${role}/tenant/${tenantId}`);
  }

  findById(userId: string) {
    return this.http.get(`/core/user/${userId}`);
  }

  findRegisteredMarketplaces(userId: string) {
    return this.http.get(`/core/user/${userId}/marketplaces`);
  }

  createUser(user: User) {
    return this.http.put(`/core/user/new`, user);

  }

  updateUser(user: User) {
    return this.http.put(`/core/user/update`, user);
  }

  subscribeUserToTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
    return this.http.post(`/core/user/${volunteerId}/subscribe/${marketplaceId}/${tenantId}`, {});
  }

  unsubscribeUserFromTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
    return this.http.post(`/core/volunteer / ${volunteerId}/unsubscribe/${marketplaceId}/${tenantId}`, {});
  }
}
