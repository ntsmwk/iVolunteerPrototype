package at.jku.cis.iVolunteer.model.core.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CoreVolunteer extends CoreUser {
	private String[] subscribedTenants;

	public String[] getSubscribedTenants() {
		return subscribedTenants;
	}

	public void setSubscribedTenants(String[] subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}

}
