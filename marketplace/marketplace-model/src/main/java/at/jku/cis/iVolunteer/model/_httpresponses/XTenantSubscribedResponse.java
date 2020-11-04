package at.jku.cis.iVolunteer.model._httpresponses;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class XTenantSubscribedResponse extends XTenant {

	private boolean subscribed;

	public XTenantSubscribedResponse() {
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
}
