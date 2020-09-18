import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { LoginService } from "../_service/login.service";
import { UserRole } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class HelpSeekerOrTenantAdminGuard implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) { }

  canActivate(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.loginService
        .getLoggedInUserRole()
        .toPromise()
        .then((role: UserRole) => {
          resolve(
            role === UserRole.TENANT_ADMIN || role === UserRole.HELP_SEEKER
          );
        });
    });
  }
}
