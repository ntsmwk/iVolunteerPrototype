package at.jku.cis.iVolunteer.model._httpresponses;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class TenantSubscribedResponse {

	private XTenant tenant;
	private boolean subscribed;

	public TenantSubscribedResponse() {
	}

	public TenantSubscribedResponse(XTenant tenant, boolean subscribed) {
		this.tenant = tenant;
		this.subscribed = subscribed;
	}

	public XTenant getTenant() {
		return tenant;
	}

	public void setTenant(XTenant tenant) {
		this.tenant = tenant;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
}
