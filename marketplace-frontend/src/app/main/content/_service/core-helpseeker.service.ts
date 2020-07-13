import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { CoreUserService } from './core-user.serivce';

@Injectable({
  providedIn: "root",
})
export class CoreHelpSeekerService {
  constructor(private http: HttpClient,
    private userService: CoreUserService) { }

  findAll() {
    // return this.http.get(`/core/helpseeker/all`);
    return this.userService.findAll();
  }

  findAllByTenantId(tenantId: string) {
    // return this.http.get(`/core/helpseeker/all?tId=${tenantId}`);
    return this.userService.findAllByTenantId(tenantId);
  }

  findById(helpSeekerId: string) {
    // return this.http.get(`/core/helpseeker/${helpSeekerId}`);
    return this.userService.findById(helpSeekerId);
  }

  findByIds(helpSeekerIds: string[]) {
    // return this.http.put(`/core/helpseeker/find-by-ids`, helpSeekerIds);
    return this.userService.findByIds(helpSeekerIds);
  }

  findRegisteredMarketplaces(helpSeekerId: string) {
    // return this.http.get(`/core/helpseeker/${helpSeekerId}/marketplace`);
    return this.userService.findRegisteredMarketplaces(helpSeekerId);
  }

  registerMarketplace(helpSeekerId: string, marketplaceId: string) {
    // return this.http.post(
    //   `/core/helpseeker/${helpSeekerId}/register/${marketplaceId}`,
    //   {}
    // );
    return this.userService.registerMarketplace(helpSeekerId, marketplaceId);
  }
}
