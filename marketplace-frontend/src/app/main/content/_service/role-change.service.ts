import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";
import { UserSubscriptionDTO } from "../_model/global-info";
import { UserRole, User, RoleTenantMapping } from "../_model/user";

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

  getRoleTenantMappings(userSubscirptions: UserSubscriptionDTO[]) {
    let map: RoleTenantMapping[] = [];

    let volunteerSubs = userSubscirptions.filter((s) => {
      return s.role === UserRole.VOLUNTEER;
    });

    if (volunteerSubs.length > 0) {
      let data = new RoleTenantMapping();
      data.role = volunteerSubs[0].role;
      data.tenantIds = volunteerSubs.map((s) => s.tenant.id);
      map.push(data);
    }

    userSubscirptions
      .filter((s) => {
        return s.role != UserRole.VOLUNTEER;
      })
      .forEach((s) => {
        let data = new RoleTenantMapping();
        data.role = s.role;
        data.tenantIds = Array.of(s.tenant.id);
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
