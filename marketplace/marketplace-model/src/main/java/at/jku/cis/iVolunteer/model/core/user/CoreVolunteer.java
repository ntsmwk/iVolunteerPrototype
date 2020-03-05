package at.jku.cis.iVolunteer.model.core.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CoreVolunteer extends CoreUser {
	private List<String> subscribedTenants = new ArrayList<String>();


	public List<String> getSubscribedTenants() {
		return subscribedTenants;
	}
	
	public void setSubscribedTenants(List<String> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}
	

	public void addSubscribedTenant(String tenantId) {
		if(!subscribedTenants.contains(tenantId)) {
		this.subscribedTenants.add(tenantId);
		}
	}

}
