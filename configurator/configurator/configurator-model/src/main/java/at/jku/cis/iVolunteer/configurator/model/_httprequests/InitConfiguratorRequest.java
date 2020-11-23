package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.Tuple;

public class InitConfiguratorRequest {

	List<Tuple<String, String>> tenantIds;
	String mpUrl;
	public List<Tuple<String, String>> getTenantIds() {
		return tenantIds;
	}
	public void setTenantIds(List<Tuple<String, String>> tenantIds) {
		this.tenantIds = tenantIds;
	}
	public String getMpUrl() {
		return mpUrl;
	}
	public void setMpUrl(String mpUrl) {
		this.mpUrl = mpUrl;
	}

	

}
