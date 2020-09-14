
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';
import { UserRole } from '../_model/user';

@Injectable({
  providedIn: 'root',
})
export class TenantTagService {
  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(`core/tags/all`);
  }

  findAllAsString() {
    return this.http.get(`core/tags/all/string`);

  }
}
