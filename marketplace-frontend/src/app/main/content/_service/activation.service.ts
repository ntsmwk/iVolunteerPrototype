import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })
export class ActivationService {
  constructor(private http: HttpClient) { }

  activate(activationId: string) {
    return this.http.post(`/core/register/activate/${activationId}`, '');
  }

  createActivationLink(username: string) {
    return this.http.post(`/core/register/activate/generate-link/${username}/user`, '');
  }

  createActivationLinkViaEmail(email: string) {
    return this.http.post(`/core/register/activate/generate-link/email`, email);

  }
}
