import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AccountType } from '../_model/user';

@Injectable({ providedIn: 'root' })
export class ActivationService {
  constructor(private http: HttpClient) { }

  activate(activationId: string) {
    return this.http.post(`/core/register/activate/${activationId}`, '');
  }

  createActivationLink(username: string, type: AccountType) {
    return this.http.post(`/core/register/activate/generate-link/${username}/user?type=${type}`, '');
  }

  createActivationLinkViaEmail(email: string, type: AccountType) {
    return this.http.post(`/core/register/activate/generate-link/email?type=${type}`, email);

  }
}
