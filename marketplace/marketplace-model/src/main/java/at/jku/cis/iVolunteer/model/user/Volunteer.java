package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Volunteer extends User {
	private String[] subscribedTenantIds;

	public String[] getSubscribedTenantIds() {
		return subscribedTenantIds;
	}

	public void setSubscribedTenantIds(String[] subscribedTenantIds) {
		this.subscribedTenantIds = subscribedTenantIds;
	}



}
