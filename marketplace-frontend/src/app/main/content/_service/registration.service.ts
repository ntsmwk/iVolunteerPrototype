import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Volunteer } from "../_model/volunteer";

@Injectable({ providedIn: "root" })
export class RegistrationService {
  constructor(private http: HttpClient) {}

  registerVolunteer(user: Volunteer) {
    return this.http.post("/core/register/volunteer", user, {
      observe: "response"
    });
  }
}
