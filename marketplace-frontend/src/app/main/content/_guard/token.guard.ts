import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { isNullOrUndefined } from "util";
import { JwtHelperService } from "@auth0/angular-jwt";
import { LoginService } from "../_service/login.service";

@Injectable({
  providedIn: "root",
})
export class TokenGuard implements CanActivate {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private router: Router, private loginService: LoginService) {}

  async canActivate(): Promise<boolean> {
    let accessToken = this.loginService.getAccessToken();
    let refreshToken = this.loginService.getRefreshToken();

    if (
      accessToken == null ||
      accessToken.length === 0 ||
      refreshToken == null ||
      refreshToken.length === 0
    ) {
      this.loginService.logout();
    }
    return true;
  }
}
