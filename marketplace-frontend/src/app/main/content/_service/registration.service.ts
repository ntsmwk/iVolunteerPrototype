import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User, UserRole, AccountType } from "../_model/user";

@Injectable({ providedIn: "root" })
export class RegistrationService {
  constructor(private http: HttpClient) { }

  registerUser(user: User, accountType: AccountType) {
    return this.http.post(`/core/register?type=${accountType}`, user, {
      observe: "response",
    });
  }
}
