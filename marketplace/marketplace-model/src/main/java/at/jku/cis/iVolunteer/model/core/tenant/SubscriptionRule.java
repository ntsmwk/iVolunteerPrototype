package at.jku.cis.iVolunteer.model.core.tenant;

import at.jku.cis.iVolunteer.model.user.UserRole;

public class SubscriptionRule {

	private UserRole role;
	private SubscriptionOption subscriptionOption;
	boolean active;

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public SubscriptionOption getSubscriptionOption() {
		return subscriptionOption;
	}

	public void setSubscriptionOption(SubscriptionOption subscriptionOption) {
		this.subscriptionOption = subscriptionOption;
	}

}
