import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { LoginService } from "../_service/login.service";
import { UserRole } from "../_model/user";
import { GlobalInfo } from "../_model/global-info";

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(private loginService: LoginService) {}

  canActivate(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      let globalInfo: GlobalInfo = JSON.parse(
        localStorage.getItem("globalInfo")
      );
      if (globalInfo == null || globalInfo.user == null) {
        this.loginService.logout();
      }

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
              role == UserRole.TENANT_ADMIN ||
              role == UserRole.NONE
          );
        });
    });
  }
}
