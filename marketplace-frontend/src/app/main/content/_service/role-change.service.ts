import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { UserRole, User } from "../_model/user";
import { Tenant } from "../_model/tenant";
import { TenantService } from "./core-tenant.service";

@Injectable({
  providedIn: "root",
})
export class RoleChangeService {
  onRoleChanged: BehaviorSubject<any>;
  currentRole: UserRole = UserRole.NONE;

  constructor() {
    this.onRoleChanged = new BehaviorSubject(this.currentRole);
  }

  changeRole(role: UserRole) {
    this.currentRole = role;
    this.onRoleChanged.next(role);
  }

  getRoleTenantMap(user: User) {
    let tenantMap = new Map<string, string[]>();
    let volunteerSubs = user.subscribedTenants.filter((s) => {
      return s.role === UserRole.VOLUNTEER;
    });

    if (volunteerSubs.length > 0) {
      tenantMap.set(
        UserRole.VOLUNTEER,
        volunteerSubs.map((s) => s.tenantId)
      );
    }

    user.subscribedTenants
      .filter((s) => {
        return s.role != UserRole.VOLUNTEER;
      })
      .forEach((s) => {
        tenantMap.set(s.role, Array.of(s.tenantId));
      });

    return tenantMap;
  }

  getRoleNameString(role: UserRole) {
    switch (role) {
      case UserRole.VOLUNTEER:
        return "Freiwillige(r)";
      case UserRole.HELP_SEEKER:
        return "Hilfesuchende(r)";
      case UserRole.RECRUITER:
        return "Recruiter";
      case UserRole.FLEXPROD:
        return "Flexprod";
      case UserRole.ADMIN:
        return "Admin";
    }
  }
}
