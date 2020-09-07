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
    let accessToken = localStorage.getItem("accessToken");

    if (isNullOrUndefined(accessToken) || accessToken.length === 0) {
      this.router.navigate(["/login"]);
    } else {
      if (this.jwtHelper.isTokenExpired(accessToken)) {
        let refreshToken = localStorage.getItem("refreshToken");
        if (
          isNullOrUndefined(refreshToken) ||
          refreshToken.length === 0 ||
          this.jwtHelper.isTokenExpired(refreshToken)
        ) {
          this.router.navigate(["/login"]);
        }

        let response: any = await this.loginService
          .refreshAccessToken(refreshToken)
          .toPromise();
        localStorage.setItem("accessToken", response.accessToken);
      }
    }
    return true;
  }
}
