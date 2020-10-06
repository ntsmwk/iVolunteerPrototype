import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { LocalRepositoryLocation, User, UserRole } from "../_model/user";
import { GlobalInfo } from "../_model/global-info";
import { Observable, generate } from "rxjs";
import { tap } from "rxjs/operators";
import { Router } from "@angular/router";
import { LocalRepositoryJsonServerService } from "./local-repository-jsonServer.service";
import { LocalRepositoryDropboxService } from "./local-repository-dropbox.service";
import { LocalRepositoryNextcloudService } from "./local-repository-nextcloud.service";
import { Tenant } from "../_model/tenant";
import { Marketplace } from "../_model/marketplace";
import { UserInfo } from "../_model/userInfo";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private httpClient: HttpClient,
    private router: Router,
    private lrDropboxService: LocalRepositoryDropboxService,
    private lrJsonServerService: LocalRepositoryJsonServerService,
    private lrNextcloudService: LocalRepositoryNextcloudService
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
    return this.http.put("/core/activation-status", username);
  }

  getLoggedIn(): Observable<UserInfo> {
    let globalInfo: GlobalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo.userInfo);
        subscriber.complete();
      });
    } else {
      return <Observable<UserInfo>>this.http.get("/core/userinfo");
    }
  }

  getLoggedInUserRole(): Observable<UserRole> {
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
    return this.http.post("/core/refreshToken", refreshToken).pipe(
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

  // getGlobalInfo() {
  //   const observable = new Observable((subscriber) => {
  //     let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
  //     if (globalInfo) {
  //       subscriber.next(globalInfo);
  //       subscriber.complete();
  //     } else {
  //       subscriber.error();
  //     }
  //   });
  //   return observable;
  // }

  // async generateGlobalInfo(role: UserRole, tenantIds: string[]) {
  //   let globalInfo = <GlobalInfo>(
  //     await this.httpClient
  //       .put(`/core/globalInfo/role/${role}`, tenantIds)
  //       .toPromise()
  //   );

  //   localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  // }

  getGlobalInfo(): GlobalInfo {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (!globalInfo) {
      return null;
      // eventually generate global info with role none!?
    } else {
      return globalInfo;
    }
  }

  getGlobalInfo2() {
    const observable = new Observable((subscriber) => {
      let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
      if (globalInfo) {
        subscriber.next(globalInfo);
        subscriber.complete();
      } else {
        // TODO @
        this.httpClient.get(`/core/login/globalInfo/`);
      }
    });
    return observable;
  }

  async generateGlobalInfo(currentRole: UserRole, currentTenantIds: String[]) {
    let globalInfo = <GlobalInfo>(
      await this.http.get("/core/globalInfo").toPromise()
    );

    globalInfo.currentRole = currentRole;
    globalInfo.currentTenants = globalInfo.userSubscriptions
      .filter((s) => currentTenantIds.indexOf(s.tenant.id) != -1)
      .map((s) => s.tenant);
    globalInfo.currentMarketplaces = [
      ...new Set(globalInfo.userSubscriptions.map((s) => s.marketplace)),
    ];

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }

  updateGlobalInfoRole(currentRole: UserRole, currentTenantIds: String[]) {
    let globalInfo: GlobalInfo = JSON.parse(localStorage.getItem("globalInfo"));

    globalInfo.currentRole = currentRole;
    globalInfo.currentTenants = globalInfo.userSubscriptions
      .filter((s) => currentTenantIds.indexOf(s.tenant.id) != -1)
      .map((s) => s.tenant);
    globalInfo.currentMarketplaces = [
      ...new Set(globalInfo.userSubscriptions.map((s) => s.marketplace)),
    ];

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }

  getLocalRepositoryService(volunteerInfo: UserInfo) {
    switch (volunteerInfo.localRepositoryLocation) {
      case LocalRepositoryLocation.LOCAL:
        return this.lrJsonServerService;
      case LocalRepositoryLocation.DROPBOX:
        return this.lrDropboxService;
      case LocalRepositoryLocation.NEXTCLOUD:
        return this.lrNextcloudService;
    }
  }
}
