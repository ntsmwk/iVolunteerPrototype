import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Participant } from "../_model/participant";

@Injectable({
  providedIn: "root"
})
export class CoreVolunteerService {
  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(`/core/volunteer/all`);
  }

  findAllByTenantId(tenantId: string) {
    return this.http.get(`/core/volunteer/all/${tenantId}`);
  }

  findById(volunteerId: string) {
    return this.http.get(`/core/volunteer/${volunteerId}`);
  }

  findRegisteredMarketplaces(volunteerId: string) {
    return this.http.get(`/core/volunteer/${volunteerId}/marketplaces`);
  }

  updateVolunteer(volunteer: Participant) {
    return this.http.put(`/core/volunteer/${volunteer.id}`, volunteer);
  }

  subscribeTenant(
    volunteerId: string,
    marketplaceId: string,
    tenantId: string
  ) {
    return this.http.post(
      `/core/volunteer/${volunteerId}/subscribe/${marketplaceId}/tenant/${tenantId}`,
      {}
    );
  }

  unsubscribeTenant(
    volunteerId: string,
    marketplaceId: string,
    tenantId: string
  ) {
    return this.http.post(
      `/core/volunteer/${volunteerId}/unsubscribe/${marketplaceId}/tenant/${tenantId}`,
      {}
    );
  }
}
