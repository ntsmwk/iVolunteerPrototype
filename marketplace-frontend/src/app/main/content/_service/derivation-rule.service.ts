import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Marketplace} from '../_model/marketplace';
import { DerivationRule } from '../_model/derivation-rule';

@Injectable({
  providedIn: 'root'
})
export class DerivationRuleService {

  constructor(private http: HttpClient) {
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/rule`);
  }

  save(marketplace: Marketplace, derivationRule: DerivationRule) {
    if (isNullOrUndefined(derivationRule.id)) {
      return this.http.post(`${marketplace.url}/derivation-rule`, derivationRule);
    }
    return this.http.put(`${marketplace.url}/derivation-rule/${derivationRule.id}`, derivationRule);
  }
}