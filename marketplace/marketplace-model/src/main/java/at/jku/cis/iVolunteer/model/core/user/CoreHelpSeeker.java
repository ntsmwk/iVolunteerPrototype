package at.jku.cis.iVolunteer.model.core.user;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.user.Tenant;

@Document
public class CoreHelpSeeker extends CoreUser {
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	

	
}
