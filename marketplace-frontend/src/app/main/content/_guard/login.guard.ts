import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { LoginService } from "../_service/login.service";
import { UserRole } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(private router: Router, private loginService: LoginService) {}

  canActivate(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.loginService
        .getLoggedInUserRole()
        .toPromise()
        .then((role: UserRole) => {
          resolve(
            role == UserRole.HELP_SEEKER ||
              role == UserRole.VOLUNTEER ||
              role == UserRole.RECRUITER ||
              role == UserRole.FLEXPROD ||
              role == UserRole.ADMIN ||
              role == UserRole.TENANT_ADMIN
          );
        });
    });
  }
}
