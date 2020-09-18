package at.jku.cis.iVolunteer.model.core.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.registration.AccountType;
import at.jku.cis.iVolunteer.model.user.User;

@Document
public class CoreUser extends User {
	@DBRef
	private List<CoreUser> follower = new ArrayList<>();
	private List<String> registeredMarketplaceIds = new ArrayList<>();
	private boolean activated;
	private AccountType accountType;

	public List<String> getRegisteredMarketplaceIds() {
		return registeredMarketplaceIds;
	}

	public void setRegisteredMarketplaceIds(List<String> registeredMarketplaceIds) {
		this.registeredMarketplaceIds = registeredMarketplaceIds;
	}

	public List<CoreUser> getFollower() {
		return follower;
	}

	public void setFollower(List<CoreUser> follower) {
		this.follower = follower;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	
	
}
