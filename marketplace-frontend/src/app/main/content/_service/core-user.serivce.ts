import {
  Injectable
} from "@angular/core";
import {
  HttpClient
} from "@angular/common/http";
import {
  User
} from "../_model/user";

@Injectable({ providedIn: "root", }) export class CoreUserService {
  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(`/core/user/all`);
  }

  findAllByTenantId(tenantId: string) {
    return this.http.get(`/core/volunteer/all / ${tenantId}`);
  }

  findById(volunteerId: string) {
    return this.http.get(`/core/volunteer / ${volunteerId}`);
  }

  findRegisteredMarketplaces(volunteerId: string) {
    return this.http.get(`/core/volunteer / ${volunteerId}/marketplaces`);
  }

  updateVolunteer(volunteer: User) {
    return this.http.put(`/core/volunteer / ${volunteer.id}`, volunteer);
  }

  subscribeTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
    return this.http.post(`/core/volunteer / ${volunteerId}/subscribe/${marketplaceId}/tenant/${tenantId}`, {});
  }

  unsubscribeTenant(volunteerId: string, marketplaceId: string, tenantId: string) {
    return this.http.post(`/core/volunteer / ${volunteerId}/unsubscribe/${marketplaceId}/tenant/${tenantId}`, {});
  }
}
