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
    const token = localStorage.getItem("accessToken");
    if (
      isNullOrUndefined(token) ||
      token.length === 0 ||
      this.jwtHelper.isTokenExpired(token)
    ) {
      let refreshToken = localStorage.getItem("refreshToken");

      let response: any = await this.loginService
        .refreshAccessToken(refreshToken)
        .toPromise();

      localStorage.setItem("accessToken", response.accessToken);
    }
    return true;
  }
}
