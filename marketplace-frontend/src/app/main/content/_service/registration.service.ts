import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "../_model/user";

@Injectable({ providedIn: "root" })
export class RegistrationService {
  constructor(private http: HttpClient) {}

  registerVolunteer(user: User) {
    return this.http.post("/core/register/volunteer", user, {
      observe: "response",
    });
  }
}
