package at.jku.cis.iVolunteer.model.core.tenant;

import at.jku.cis.iVolunteer.model.user.ParticipantRole;

public class SubscriptionRule {

	private ParticipantRole role;
	private SubscriptionOption subscriptionOption;
	boolean active;

	public ParticipantRole getRole() {
		return role;
	}

	public void setRole(ParticipantRole role) {
		this.role = role;
	}

	public SubscriptionOption getSubscriptionOption() {
		return subscriptionOption;
	}

	public void setSubscriptionOption(SubscriptionOption subscriptionOption) {
		this.subscriptionOption = subscriptionOption;
	}

}
