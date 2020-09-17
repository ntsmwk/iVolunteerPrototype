import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UserRole } from "../_model/user";
import { GlobalInfo } from "../_model/global-info";
import { Observable, generate } from "rxjs";
import { tap } from "rxjs/operators";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private httpClient: HttpClient,
    private router: Router
  ) {}

  login(username: string, password: string) {
    return this.http.post(
      "/core/login",
      { username: username, password: password },
      { observe: "response" }
    );
  }

  logout() {
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

  getActivationStatus(username: string) {
    return this.http.put("/core/login/activation-status", username);
  }

  getLoggedIn() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo.user);
        subscriber.complete();
      });
    } else {
      return this.http.get("/core/login");
    }
  }

  getLoggedInUserRole() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo.userRole);
        subscriber.complete();
      });
    } else {
      return new Observable((subscriber) => {
        subscriber.next(UserRole.NONE);
        subscriber.complete();
      });
    }
  }

  refreshAccessToken(refreshToken: string) {
    return this.http.post("/core/login/refreshToken", refreshToken).pipe(
      tap(
        (response: any) => {
          let accessToken: string = response.accessToken;
          let refreshToken: string = response.refreshToken;

          if (accessToken == null || refreshToken == null) {
            this.logout();
          } else {
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("refreshToken", refreshToken);
          }
        },
        (error) => {
          this.logout();
        }
      )
    );
  }

  getRefreshToken() {
    return localStorage.getItem("refreshToken");
  }

  getAccessToken() {
    return localStorage.getItem("accessToken");
  }

  getGlobalInfo() {
    const observable = new Observable((subscriber) => {
      let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
      if (globalInfo) {
        subscriber.next(globalInfo);
        subscriber.complete();
      } else {
        this.httpClient.get(`/core/login/globalInfo`);
      }
    });

    return observable;
  }

  async generateGlobalInfo(role: UserRole, tenantIds: string[]) {
    let globalInfo = <GlobalInfo>(
      await this.httpClient
        .put(`/core/login/globalInfo/role/${role}`, tenantIds)
        .toPromise()
    );

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }
}
