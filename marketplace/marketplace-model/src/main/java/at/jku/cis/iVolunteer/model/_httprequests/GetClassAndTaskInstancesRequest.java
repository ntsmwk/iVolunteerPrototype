package at.jku.cis.iVolunteer.model._httprequests;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

public class GetClassAndTaskInstancesRequest {

	Tenant tenant;
	CoreUser user;

	public GetClassAndTaskInstancesRequest() {
	}

	public GetClassAndTaskInstancesRequest(Tenant tenant, CoreUser user) {
		this.tenant = tenant;
		this.user = user;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public CoreUser getUser() {
		return user;
	}

	public void setUser(CoreUser user) {
		this.user = user;
	}
}
