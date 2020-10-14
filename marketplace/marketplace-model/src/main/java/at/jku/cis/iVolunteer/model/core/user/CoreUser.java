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
	private Boolean activated;
	private AccountType accountType;

	public CoreUser updateCoreUser(CoreUser update) {
		if (update.getId() != null)
			this.setId(update.getId());

		if (update.getUsername() != null)
			this.setUsername(update.getUsername());

		if (update.getPassword() != null)
			this.setPassword(update.getPassword());

		if (update.getLoginEmail() != null)
			this.setLoginEmail(update.getLoginEmail());

		if (update.getFormOfAddress() != null)
			this.setFormOfAddress(update.getFormOfAddress());

		if (update.getTitleBefore() != null)
			this.setTitleBefore(update.getTitleBefore());

		if (update.getFirstname() != null)
			this.setFirstname(update.getFirstname());

		if (update.getLastname() != null)
			this.setLastname(update.getLastname());

		if (update.getTitleAfter() != null)
			this.setTitleAfter(update.getTitleAfter());

		if (update.getNickname() != null)
			this.setNickname(update.getNickname());

		if (update.getOrganizationPosition() != null)
			this.setOrganizationPosition(update.getOrganizationPosition());

		if (update.getOrganizationName() != null)
			this.setOrganizationName(update.getOrganizationName());

		if (update.getBirthday() != null)
			this.setBirthday(update.getBirthday());

		if (update.getLocations() != null)
			this.setLocations(update.getLocations());

		if (update.getAbout() != null)
			this.setAbout(update.getAbout());

		if (update.getAddress() != null)
			this.setAddress(update.getAddress());

		if (update.getTimeslots() != null)
			this.setTimeslots(update.getTimeslots());

		if (update.getPhoneNumbers() != null)
			this.setPhoneNumbers(update.getPhoneNumbers());

		if (update.getWebsites() != null)
			this.setWebsites(update.getWebsites());

		if (update.getEmails() != null)
			this.setEmails(update.getEmails());

		if (update.getProfileImagePath() != null)
			this.setProfileImagePath(update.getProfileImagePath());

		if (update.getSubscribedTenants() != null)
			this.setSubscribedTenants(update.getSubscribedTenants());

		if (update.getLocalRepositoryLocation() != null)
			this.setLocalRepositoryLocation(update.getLocalRepositoryLocation());

		if (update.getDropboxToken() != null)
			this.setDropboxToken(update.getDropboxToken());

		if (update.getNextcloudCredentials() != null)
			this.setNextcloudCredentials(update.getNextcloudCredentials());

		if (update.getFollower() != null)
			this.setFollower(update.getFollower());

		if (update.getRegisteredMarketplaceIds() != null)
			this.setRegisteredMarketplaceIds(update.getRegisteredMarketplaceIds());

		if (update.isActivated() != null)
			this.setActivated(update.isActivated());

		if (update.getAccountType() != null)
			this.setAccountType(update.getAccountType());

		return this;
	}

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

	public Boolean isActivated() {
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
