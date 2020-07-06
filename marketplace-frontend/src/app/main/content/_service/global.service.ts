import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { GlobalInfo } from "../_model/global-info";

import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class GlobalService {
  constructor(private httpClient: HttpClient) {}

  getGlobalInfo(): Observable<any> {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));

    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo);
        subscriber.complete();
      });
    } else {
      this.initializeGlobalInfo();
      return this.httpClient.get(`/core/global`);
    }
  }

  private initializeGlobalInfo() {
    this.httpClient
      .get(`/core/global`)
      .toPromise()
      .then((globalInfo: GlobalInfo) => {
        localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
      });
  }
}
