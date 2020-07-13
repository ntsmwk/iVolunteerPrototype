import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "../_model/user";
import { GlobalInfo } from "../_model/global-info";
import { MarketplaceService } from "./core-marketplace.service";
import { TenantService } from "./core-tenant.service";
import { Marketplace } from "../_model/marketplace";
import { Tenant } from "../_model/tenant";
import { Observable } from "rxjs";
import { isNullOrUndefined } from "util";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private marketplaceService: MarketplaceService,
    private tenantService: TenantService,
    private httpClient: HttpClient
  ) {}

  login(username: string, password: string) {
    return this.http.post(
      "/core/login",
      { username: username, password: password },
      { observe: "response" }
    );
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
      return this.http.get("/core/login/role");
    }
  }

  getGlobalInfo() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));

    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo);
        subscriber.complete();
      });
    } else {
      this.httpClient.get(`/core/login/globalInfo`);
    }
  }

  async generateGlobalInfo() {
    let globalInfo = <GlobalInfo>(
      await this.httpClient.get(`/core/login/globalInfo`).toPromise()
    );

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }
}
