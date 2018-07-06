import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class CoreMarketplaceService {

  private apiUrl = '/core/marketplace';

  constructor(private http: HttpClient) {
  }

  findById(marketplaceId: string) {
    return this.http.get(`${this.apiUrl}/${marketplaceId}`);
  }

  findAll() {
    return this.http.get(`${this.apiUrl}`);
  }

  registerMarketplace(marketplace: Marketplace) {
    return this.http.post(`${this.apiUrl}/`, marketplace);
  }

  deleteMarketplace(id: string) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


}
