import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, UserRole } from '../_model/user';

@Injectable({ providedIn: 'root', }) export class CoreUserService {
  constructor(private http: HttpClient) { }

  findAll() {
    console.log(`findAll()`);
    return this.http.get(`/core/user/all`);
  }

  findAllByTenantId(tenantId: string) {
    console.log(`findAllByTenantId(${tenantId})`);

    return this.http.get(`/core/user/all/tenant/${tenantId}`);
  }

  findAllByRole(role: UserRole) {
    console.log(`findAllByRole(${role})`);
    return this.http.get(`/core/user/all/role/${role}`);
  }

  findAllByRoleAndTenantId(tenantId: string, role: UserRole) {
    console.log(`findAllByRoleAndTenantId(${tenantId},${role})`);
    return this.http.get(`/core/user/all/role/${role}/tenant/${tenantId}`);
  }

  findById(userId: string) {
    console.log(`findById(${userId})`);
    return this.http.get(`/core/user/${userId}`);
  }

  findByUsername(username: string) {
    console.log(`findByUsername(${username})`);
    return this.http.get(`/core/user/name/${username}`);
  }

  findByIds(userIds: string[]) {
    console.log(`findByIds(${userIds})`);
    return this.http.put(`/core/user/find-by-ids`, userIds);
  }

  findRegisteredMarketplaces(userId: string) {
    console.log(`findRegisteredMarketplaces(${userId})`);
    return this.http.get(`/core/user/${userId}/marketplaces`);
  }

  registerMarketplace(userId: string, marketplaceId: string) {
    console.log(`registerMarketplace(${userId},${marketplaceId})`);
    return this.http.post(`/core/user/${userId}/register/${marketplaceId}`, {});
  }

  createUser(user: User, updateMarketplaces: boolean) {
    console.log(`createUser(${user.id},${updateMarketplaces})`);
    return this.http.post(`/core/user/new?updateMarketplaces=${updateMarketplaces}`, user);
  }

  updateUser(user: User, updateMarketplaces: boolean) {
    console.log(`createUser(${user.id},${updateMarketplaces})`);
    return this.http.put(`/core/user/update?updateMarketplaces=${updateMarketplaces}`, user);
  }

  subscribeUserToTenant(userId: string, marketplaceId: string, tenantId: string, role: UserRole) {
    console.log(`subscribeUserToTenant(${userId},${marketplaceId},${tenantId},${role})`);
    return this.http.put(`/core/user/${userId}/subscribe/${marketplaceId}/${tenantId}/${role}`, {});
  }

  unsubscribeUserFromTenant(userId: string, marketplaceId: string, tenantId: string, role: UserRole) {
    console.log(`unsubscribeUserFromTenant(${userId},${marketplaceId},${tenantId},${role})`);
    return this.http.put(`/core/user/${userId}/unsubscribe/${marketplaceId}/${tenantId}/${role}`, {});
  }
}
