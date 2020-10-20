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
import { Marketplace } from "../_model/marketplace";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root"
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
      `${environment.CORE_URL}/auth/login`,
      { username: username, password: password },
      { observe: "response" }
    );
  }

  logout() {
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

  getActivationStatus(username: string) {
    return this.http.put(`${environment.CORE_URL}/activation-status`, username);
  }

  getLoggedIn() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable(subscriber => {
        subscriber.next(globalInfo.user);
        subscriber.complete();
      });
    } else {
      return this.http.get(`${environment.CORE_URL}/user`);
    }
  }

  getLoggedInUserRole() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable(subscriber => {
        subscriber.next(globalInfo.userRole);
        subscriber.complete();
      });
    } else {
      return new Observable(subscriber => {
        subscriber.next(UserRole.NONE);
        subscriber.complete();
      });
    }
  }

  refreshAccessToken(refreshToken: string) {
    return this.http
      .post(`${environment.CORE_URL}/auth/refreshToken`, {"refreshToken": refreshToken})
      .pipe(
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
          error => {
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
    const observable = new Observable(subscriber => {
      let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
      if (globalInfo) {
        subscriber.next(globalInfo);
        subscriber.complete();
      }
    });
    return observable;
  }

  async generateGlobalInfo(role: UserRole, tenantIds: string[]) {
    let globalInfo = <GlobalInfo>(
      await this.httpClient
        .put(`${environment.CORE_URL}/globalInfo/role/${role}`, tenantIds)
        .toPromise()
    );

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }

  getLocalRepositoryService(volunteer: User) {
    switch (volunteer.localRepositoryLocation) {
      case LocalRepositoryLocation.LOCAL:
        return this.lrJsonServerService;
      case LocalRepositoryLocation.DROPBOX:
        return this.lrDropboxService;
      case LocalRepositoryLocation.NEXTCLOUD:
        return this.lrNextcloudService;
    }
  }
}
