package at.jku.cis.iVolunteer.model.user;

import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Volunteer extends User {
	private ArrayList<String> subscribedTenants = new ArrayList<String>();


	public ArrayList<String> getSubscribedTenants() {
		return subscribedTenants;
	}
	
	public void setSubscribedTenants(ArrayList<String> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}
	

	public void addSubscribedTenant(String tenantId) {
		this.subscribedTenants.add(tenantId);
	}

	



}
