import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreFlexProdService {

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(flexProdUserId: string) {
    return this.http.get(`/core/flexprod/${flexProdUserId}/marketplace`);
  }

  registerMarketplace(flexProdUserId: string, marketplaceId: string) {
    return this.http.post(`/core/flexprod/${flexProdUserId}/register/${marketplaceId}`, {});
  }
}
