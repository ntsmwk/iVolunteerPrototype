import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AccountType } from "../_model/user";
import { environment } from "environments/environment";

@Injectable({ providedIn: "root" })
export class ActivationService {
  constructor(private http: HttpClient) {}
  activate(activationId: string) {
    return this.http.post(
      `${environment.CORE_URL}/auth/activate/${activationId}`,
      ""
    );
  }

  createActivationLink(username: string, type: AccountType) {
    return this.http.post(
      `${environment.CORE_URL}/auth/activate/generate-link/${username}/user?type=${type}`,
      ""
    );
  }

  createActivationLinkViaEmail(email: string, type: AccountType) {
    return this.http.post(
      `${environment.CORE_URL}/auth/activate/generate-link/email?type=${type}`,
      email
    );
  }
}
