import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { DerivationRule } from '../_model/derivation-rule';

@Injectable({
  providedIn: 'root'
})
export class DerivationRuleService {

  constructor(private http: HttpClient) {
  }

  findByIdAndTenantId(marketplace: Marketplace, id: string, tenantId: string){
    return this.http.get(`${marketplace.url}/rule/${id}/tenant/${tenantId}`);
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/rule/${id}`);
  }

  findByContainerAndName(marketplace: Marketplace, tenantId: string, container: string, ruleName: string){
    return this.http.get(`${marketplace.url}/rule/tenant/${tenantId}/container/${container}/rule/${ruleName}`);
  }

  findAll(marketplace: Marketplace, tenantId: string) {
    return this.http.get(`${marketplace.url}/rule/tenant/${tenantId}`);
  }

  test(marketplace: Marketplace, derivationRule: DerivationRule){
    return this.http.post(`${marketplace.url}/rule/test`, derivationRule);
  }

  getTestResults(marketplace: Marketplace, derivationRule: DerivationRule){
    return this.http.get(`${marketplace.url}/rule/test/${derivationRule.container}`);
  }

  save(marketplace: Marketplace, derivationRule: DerivationRule) {
    if (isNullOrUndefined(derivationRule.id)) {
      return this.http.post(`${marketplace.url}/rule`, derivationRule);
    }
    return this.http.put(`${marketplace.url}/rule/${derivationRule.id}`, derivationRule);
  }

  getGeneralProperties(marketplace: Marketplace, tenantId: string){
    return this.http.get(`${marketplace.url}/rule/tenant/${tenantId}/general/properties`);
  }
}
