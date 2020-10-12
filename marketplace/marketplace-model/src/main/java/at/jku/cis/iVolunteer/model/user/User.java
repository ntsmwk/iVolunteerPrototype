package at.jku.cis.iVolunteer.model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String loginEmail;

	private String formOfAddress;
	private String titleBefore;
	private String firstname;
	private String lastname;
	private String titleAfter;

	private String nickname;

	private String organizationPosition;
	private String organizationName;

	private Date birthday;

	private List<String> locations = new ArrayList<>();
	private String about;
	private Address address;
	private List<Timeslot> timeslots = initTimeslots();

	private List<String> phoneNumbers = new ArrayList<>(3);
	private List<String> websites = new ArrayList<>(3);
	private List<String> emails = new ArrayList<>(3);

	private String profileImagePath;

	private List<TenantSubscription> subscribedTenants = new ArrayList<TenantSubscription>();

	private LocalRepositoryLocation localRepositoryLocation;
	private String dropboxToken;
	private NextcloudCredentials nextcloudCredentials;

	public User() {
	}

	public User(final CoreUser coreUser) {
		this.id = coreUser.getId();
		this.username = coreUser.getUsername();
		this.password = coreUser.getPassword();
		this.loginEmail = coreUser.getLoginEmail();
		this.formOfAddress = coreUser.getFormOfAddress();
		this.titleBefore = coreUser.getTitleBefore();
		this.firstname = coreUser.getFirstname();
		this.lastname = coreUser.getLastname();
		this.titleAfter = coreUser.getTitleAfter();
		this.nickname = coreUser.getNickname();
		this.organizationPosition = coreUser.getOrganizationPosition();
		this.organizationName = coreUser.getOrganizationName();
		this.birthday = coreUser.getBirthday();
		this.locations = coreUser.getLocations();
		this.about = coreUser.getAbout();
		this.address = coreUser.getAddress();
		this.timeslots = coreUser.getTimeslots();
		this.phoneNumbers = coreUser.getPhoneNumbers();
		this.websites = coreUser.getWebsites();
		this.emails = coreUser.getEmails();
		this.profileImagePath = coreUser.getProfileImagePath();
		this.subscribedTenants = coreUser.getSubscribedTenants();
		this.localRepositoryLocation = coreUser.getLocalRepositoryLocation();
		this.dropboxToken = coreUser.getDropboxToken();
		this.nextcloudCredentials = coreUser.getNextcloudCredentials();
	}

	private List<Timeslot> initTimeslots() {
		List<Timeslot> timeslots = new ArrayList<>();
		for (Weekday day : Weekday.values()) {
			Timeslot timeslot = new Timeslot();
			timeslot.weekday = day;
			timeslots.add(timeslot);
		}
		return timeslots;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public String getFormOfAddress() {
		return formOfAddress;
	}

	public void setFormOfAddress(String formOfAddress) {
		this.formOfAddress = formOfAddress;
	}

	public String getTitleBefore() {
		return titleBefore;
	}

	public void setTitleBefore(String titleBefore) {
		this.titleBefore = titleBefore;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public void setTitleAfter(String titleAfter) {
		this.titleAfter = titleAfter;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public String getOrganizationPosition() {
		return organizationPosition;
	}

	public void setOrganizationPosition(String organizationPosition) {
		this.organizationPosition = organizationPosition;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(final List<String> locations) {
		this.locations = locations;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(final String about) {
		this.about = about;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(final List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getWebsites() {
		return websites;
	}

	public void setWebsites(final List<String> websites) {
		this.websites = websites;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(final List<String> emails) {
		this.emails = emails;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}

	public List<TenantSubscription> getSubscribedTenants() {
		return this.subscribedTenants;
	}

	public void setSubscribedTenants(final List<TenantSubscription> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}

	public LocalRepositoryLocation getLocalRepositoryLocation() {
		return this.localRepositoryLocation;
	}

	public void setLocalRepositoryLocation(LocalRepositoryLocation localRepositoryLocation) {
		this.localRepositoryLocation = localRepositoryLocation;
	}

	public String getDropboxToken() {
		return this.dropboxToken;
	}

	public void setDropboxToken(String dropboxToken) {
		this.dropboxToken = dropboxToken;
	}

	public NextcloudCredentials getNextcloudCredentials() {
		return this.nextcloudCredentials;
	}

	public void setNextcloudCredentials(NextcloudCredentials nextcloudCredentials) {
		this.nextcloudCredentials = nextcloudCredentials;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public List<TenantSubscription> addSubscribedTenant(final String marketplaceId, final String tenantId,
			final UserRole role) {
		final TenantSubscription tenantUserSubscription = findTenantUserSubscription(marketplaceId, tenantId, role);
		if (tenantUserSubscription == null) {
			this.subscribedTenants.add(new TenantSubscription(marketplaceId, tenantId, role));
		}
		return this.subscribedTenants;
	}

	private TenantSubscription findTenantUserSubscription(final String marketplaceId, final String tenantId,
			final UserRole role) {
		return this.subscribedTenants.stream().filter(st -> st.getMarketplaceId().equals(marketplaceId)
				&& st.getTenantId().equals(tenantId) && st.getRole().equals(role)).findFirst().map(f -> f).orElse(null);
	}

	public List<TenantSubscription> removeSubscribedTenant(final String marketplaceId, final String tenantId,
			final UserRole role) {
		this.subscribedTenants.removeIf(s -> s.getTenantId().equals(tenantId)
				&& s.getMarketplaceId().equals(marketplaceId) && s.getRole().equals(role));
		return this.subscribedTenants;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		return ((User) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
