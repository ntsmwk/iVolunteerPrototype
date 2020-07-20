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
  allTenants: Tenant[];

  constructor(private tenantService: TenantService) {
    this.tenantService
      .findAll()
      .toPromise()
      .then((allTenants: Tenant[]) => {
        this.allTenants = allTenants;
      });
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
        volunteerSubs.map((s) => this.getTenantNameString(s.tenantId))
      );
    }

    user.subscribedTenants
      .filter((s) => {
        return s.role != UserRole.VOLUNTEER;
      })
      .forEach((s) => {
        tenantMap.set(s.role, Array.of(this.getTenantNameString(s.tenantId)));
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

  private getTenantNameString(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    return tenant.name;
  }
}
