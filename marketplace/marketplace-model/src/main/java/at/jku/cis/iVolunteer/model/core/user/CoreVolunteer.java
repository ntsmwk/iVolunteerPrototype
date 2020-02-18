package at.jku.cis.iVolunteer.model.core.user;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CoreVolunteer extends CoreUser {
	private ArrayList<String> subscribedTenants = new ArrayList<String>();


	public ArrayList<String> getSubscribedTenants() {
		return subscribedTenants;
	}
	
	public void setSubscribedTenants(ArrayList<String> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}
	

	public void addSubscribedTenant(String tenantId) {
		if(!subscribedTenants.contains(tenantId)) {
		this.subscribedTenants.add(tenantId);
		}
	}

}
