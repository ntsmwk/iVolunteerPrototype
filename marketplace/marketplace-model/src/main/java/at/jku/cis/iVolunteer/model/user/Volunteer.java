package at.jku.cis.iVolunteer.model.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Volunteer extends User {
	private List<String> subscribedTenants = new ArrayList<String>();

	public List<String> getSubscribedTenants() {
		return subscribedTenants;
	}

	public void setSubscribedTenants(List<String> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}

	public void addSubscribedTenant(String tenantId) {
		this.subscribedTenants.add(tenantId);
	}

}
