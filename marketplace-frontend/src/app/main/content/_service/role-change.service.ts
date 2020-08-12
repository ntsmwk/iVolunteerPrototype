import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";
import { UserRole, User, roleTenantMapping } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class RoleChangeService {
  onRoleChanged: BehaviorSubject<any>;
  onUpdate: Subject<any>;

  constructor() {
    this.onRoleChanged = new BehaviorSubject(UserRole.NONE);
    this.onUpdate = new Subject();
  }

  changeRole(role: UserRole) {
    this.onRoleChanged.next(role);
  }

  update() {
    this.onUpdate.next();
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
      case UserRole.TENANT_ADMIN:
        return "Organisations-Admin";
    }
  }
}
