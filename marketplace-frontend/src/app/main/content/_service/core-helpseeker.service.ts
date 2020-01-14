import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoreHelpSeekerService {

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(`/core/helpseeker/all`);
  }

  findById(helpSeekerId: string) {
    return this.http.get(`/core/helpseeker/${helpSeekerId}`);
  }

  findByIds(helpSeekerIds: string[]) {
    return this.http.put(`/core/helpseeker/find-by-ids`, helpSeekerIds);
  }

  findRegisteredMarketplaces(helpSeekerId: string) {
    return this.http.get(`/core/helpseeker/${helpSeekerId}/marketplace`);
  }

  registerMarketplace(helpSeekerId: string, marketplaceId: string) {
    return this.http.post(`/core/helpseeker/${helpSeekerId}/register/${marketplaceId}`, {});
  }

}
