import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "../_model/user";

@Injectable({ providedIn: "root" })
export class ActivationService {
  constructor(private http: HttpClient) { }

  activate(activationId: string) {
    return this.http.put(`/core/register/activate/${activationId}`, '');
  }
}
