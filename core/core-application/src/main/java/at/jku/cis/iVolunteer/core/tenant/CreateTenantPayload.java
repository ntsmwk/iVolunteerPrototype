package at.jku.cis.iVolunteer.core.tenant;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class CreateTenantPayload {
	private XTenant tenant;
	private String marketplaceId;

	public CreateTenantPayload() {
	}

	public XTenant getTenant() {
		return tenant;
	}

	public void setTenant(XTenant tenant) {
		this.tenant = tenant;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
}
