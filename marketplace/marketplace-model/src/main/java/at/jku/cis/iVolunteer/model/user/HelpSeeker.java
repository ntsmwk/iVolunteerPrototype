package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class HelpSeeker extends User {
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}



	
}
