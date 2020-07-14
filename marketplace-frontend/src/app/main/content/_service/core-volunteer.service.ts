// import { Injectable } from "@angular/core";
// import { HttpClient } from "@angular/common/http";
// import { User, UserRole } from "../_model/user";
// import { CoreUserService } from './core-user.serivce';

// @Injectable({
//   providedIn: "root",
// })
// export class CoreVolunteerService {
//   constructor(private http: HttpClient,
//     private userService: CoreUserService) { }

//   findAll() {
//     // return this.http.get(`/core/volunteer/all`);
//     return this.userService.findAllByRole(UserRole.VOLUNTEER);
//   }

//   findAllByTenantId(tenantId: string) {
//     // return this.http.get(`/core/volunteer/all/${tenantId}`);
//     return this.userService.findAllByRoleAndTenantId(tenantId, UserRole.VOLUNTEER);
//   }

//   findById(volunteerId: string) {
//     // return this.http.get(`/core/volunteer/${volunteerId}`);
//     return this.userService.findById(volunteerId);
//   }

//   findRegisteredMarketplaces(volunteerId: string) {
//     // return this.http.get(`/core/volunteer/${volunteerId}/marketplaces`);
//     return this.userService.findRegisteredMarketplaces(volunteerId);
//   }

//   updateVolunteer(volunteer: User) {
//     // return this.http.put(`/core/volunteer/${volunteer.id}`, volunteer);
//     return this.userService.updateUser(volunteer, true);
//   }

//   subscribeTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
//     // return this.http.post(
//     //   `/core/volunteer/${volunteerId}/subscribe/${marketplaceId}/tenant/${tenantId}`,
//     //   {}
//     // );
//     return this.userService.subscribeUserToTenant(volunteerId, marketplaceId, tenantId, UserRole.VOLUNTEER);
//   }

//   unsubscribeTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
//     // return this.http.post(
//     //   `/core/volunteer/${volunteerId}/unsubscribe/${marketplaceId}/tenant/${tenantId}`,
//     //   {}
//     // );
//     return this.userService.unsubscribeUserFromTenant(volunteerId, marketplaceId, tenantId, UserRole.VOLUNTEER);
//   }
// }
