import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";
import { LoginService } from "../_service/login.service";
import { UserRole } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class RecruiterGuard implements CanActivate {
  constructor(private loginService: LoginService) {}

  canActivate(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.loginService
        .getLoggedInUserRole()
        .toPromise()
        .then((role: UserRole) => resolve(role == UserRole.RECRUITER));
    });
  }
}
