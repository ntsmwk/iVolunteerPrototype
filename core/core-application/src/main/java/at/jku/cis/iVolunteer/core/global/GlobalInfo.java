package at.jku.cis.iVolunteer.core.global;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.core.security.ParticipantRole;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;

public class GlobalInfo {

	private User participant;
	private ParticipantRole participantRole;
	private List<Tenant> tenants = new ArrayList<>();
	private Marketplace marketplace;

	public GlobalInfo() {
	}

	public User getParticipant() {
		return participant;
	}

	public void setParticipant(User participant) {
		this.participant = participant;
	}

	public ParticipantRole getParticipantRole() {
		return participantRole;
	}

	public void setParticipantRole(ParticipantRole participantRole) {
		this.participantRole = participantRole;
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
