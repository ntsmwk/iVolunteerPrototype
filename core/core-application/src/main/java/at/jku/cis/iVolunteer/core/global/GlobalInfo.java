package at.jku.cis.iVolunteer.core.global;

import java.util.List;

import at.jku.cis.iVolunteer.core.security.model.UserInfo;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

public class GlobalInfo {
	// role independent fields
	private UserInfo userInfo;
	private List<UserSubscriptionDTO> userSubscriptions;
	private List<Marketplace> registeredMarketplaces;

	// role specific fields
	// irrelevant on server side
	// private UserRole currentRole;
	// private List<Tenant> currentTenants;
	// private List<Marketplace> currentMarketplaces;

	public GlobalInfo() {
	}

	public GlobalInfo(UserInfo userInfo, List<UserSubscriptionDTO> subscribedTenants,
			List<Marketplace> registeredMarketplaces) {
		this.userInfo = userInfo;
		this.userSubscriptions = subscribedTenants;
		this.registeredMarketplaces = registeredMarketplaces;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserSubscriptionDTO> getSubscribedTenants() {
		return this.userSubscriptions;
	}

	public void setSubscribedTenants(List<UserSubscriptionDTO> subscribedTenants) {
		this.userSubscriptions = subscribedTenants;
	}

	public List<Marketplace> getRegisteredMarketplaces() {
		return this.registeredMarketplaces;
	}

	public void setRegisteredMarketplaces(List<Marketplace> registeredMarketplaces) {
		this.registeredMarketplaces = registeredMarketplaces;
	}

	// public UserRole getCurrentRole() {
	// return this.currentRole;
	// }

	// public void setCurrentRole(UserRole currentRole) {
	// this.currentRole = currentRole;
	// }

	// public List<Tenant> getCurrentTenants() {
	// return this.currentTenants;
	// }

	// public void setCurrentTenants(List<Tenant> currentTenants) {
	// this.currentTenants = currentTenants;
	// }

	// public List<Marketplace> getCurrentMarketplaces() {
	// return this.currentMarketplaces;
	// }

	// public void setCurrentMarketplaces(List<Marketplace> currentMarketplaces) {
	// this.currentMarketplaces = currentMarketplaces;
	// }

}
