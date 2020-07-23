import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { UserRole, User, roleTenantMapping } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class RoleChangeService {
  onRoleChanged: BehaviorSubject<any>;

  constructor() {
    this.onRoleChanged = new BehaviorSubject(UserRole.NONE);
  }

  changeRole(role: UserRole) {
    this.onRoleChanged.next(role);
  }

  getRoleTenantMappings(user: User) {
    let map: roleTenantMapping[] = [];

    let volunteerSubs = user.subscribedTenants.filter((s) => {
      return s.role === UserRole.VOLUNTEER;
    });

    if (volunteerSubs.length > 0) {
      let data = new roleTenantMapping();
      data.role = volunteerSubs[0].role;
      data.tenantIds = volunteerSubs.map((s) => s.tenantId);
      map.push(data);
    }

    user.subscribedTenants
      .filter((s) => {
        return s.role != UserRole.VOLUNTEER;
      })
      .forEach((s) => {
        let data = new roleTenantMapping();
        data.role = s.role;
        data.tenantIds = Array.of(s.tenantId);
        map.push(data);
      });

    return map;
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
