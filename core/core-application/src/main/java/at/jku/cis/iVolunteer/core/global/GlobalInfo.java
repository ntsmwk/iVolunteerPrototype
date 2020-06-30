package at.jku.cis.iVolunteer.core.global;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

public class GlobalInfo {

	private User user;
	private UserRole userRole;
	private List<Tenant> tenants = new ArrayList<>();
	private Marketplace marketplace;

	public GlobalInfo() {
	}

	public User getParticipant() {
		return user;
	}

	public void setParticipant(User participant) {
		this.user = participant;
	}

	public UserRole getParticipantRole() {
		return userRole;
	}

	public void setParticipantRole(UserRole participantRole) {
		this.userRole = participantRole;
	}

	public Marketplace getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

}
