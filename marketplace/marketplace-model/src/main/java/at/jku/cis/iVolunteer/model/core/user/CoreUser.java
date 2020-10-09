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
	// TODO Philipp: boolean vs Boolean
	private Boolean activated;
	private AccountType accountType;

	public static CoreUser updateCoreUser(CoreUser original, CoreUser update) {
		if (update.getId() != null)
			original.setId(update.getId());

		if (update.getUsername() != null)
			original.setUsername(update.getUsername());

		if (update.getPassword() != null)
			original.setPassword(update.getPassword());

		if (update.getLoginEmail() != null)
			original.setLoginEmail(update.getLoginEmail());

		if (update.getFormOfAddress() != null)
			original.setFormOfAddress(update.getFormOfAddress());

		if (update.getTitleBefore() != null)
			original.setTitleBefore(update.getTitleBefore());

		if (update.getFirstname() != null)
			original.setFirstname(update.getFirstname());

		if (update.getLastname() != null)
			original.setLastname(update.getLastname());

		if (update.getTitleAfter() != null)
			original.setTitleAfter(update.getTitleAfter());

		if (update.getNickname() != null)
			original.setNickname(update.getNickname());

		if (update.getOrganizationPosition() != null)
			original.setOrganizationPosition(update.getOrganizationPosition());

		if (update.getOrganizationName() != null)
			original.setOrganizationName(update.getOrganizationName());

		if (update.getBirthday() != null)
			original.setBirthday(update.getBirthday());

		if (update.getLocations() != null)
			original.setLocations(update.getLocations());

		if (update.getAbout() != null)
			original.setAbout(update.getAbout());

		if (update.getAddress() != null)
			original.setAddress(update.getAddress());

		if (update.getTimeslots() != null)
			original.setTimeslots(update.getTimeslots());

		if (update.getPhoneNumbers() != null)
			original.setPhoneNumbers(update.getPhoneNumbers());

		if (update.getWebsites() != null)
			original.setWebsites(update.getWebsites());

		if (update.getEmails() != null)
			original.setEmails(update.getEmails());

		if (update.getImageId() != null)
			original.setImageId(update.getImageId());

		if (update.getSubscribedTenants() != null)
			original.setSubscribedTenants(update.getSubscribedTenants());

		if (update.getLocalRepositoryLocation() != null)
			original.setLocalRepositoryLocation(update.getLocalRepositoryLocation());

		if (update.getDropboxToken() != null)
			original.setDropboxToken(update.getDropboxToken());

		if (update.getNextcloudCredentials() != null)
			original.setNextcloudCredentials(update.getNextcloudCredentials());

		if (update.getFollower() != null)
			original.setFollower(update.getFollower());

		if (update.getRegisteredMarketplaceIds() != null)
			original.setRegisteredMarketplaceIds(update.getRegisteredMarketplaceIds());

		if (update.isActivated() != null)
			original.setActivated(update.isActivated());

		if (update.getAccountType() != null)
			original.setAccountType(update.getAccountType());

		return original;

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
