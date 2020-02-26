import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })
export class RegistrationService {
  constructor(private http: HttpClient) {}

  register(username: string, password: string) {
    return this.http.post(
      "/core/register",
      { username: username, password: password },
      { observe: "response" }
    );
  }
}
