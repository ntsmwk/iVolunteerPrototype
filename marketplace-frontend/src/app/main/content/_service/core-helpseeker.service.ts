import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreHelpSeekerService {

  constructor(private http: HttpClient) {
  }

  findRegisteredMarketplaces(helpSeekerId: string) {
    return this.http.get(`/core/helpseeker/${helpSeekerId}/marketplace`);
  }

  registerMarketplace(helpSeekerId: string, marketplaceId: string) {
    return this.http.post(`/core/helpseeker/${helpSeekerId}/register/${marketplaceId}`, {});
  }
}
