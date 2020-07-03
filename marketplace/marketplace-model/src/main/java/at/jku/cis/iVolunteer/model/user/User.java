package at.jku.cis.iVolunteer.model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.model.TenantUserSubscription;

public class User {
	@Id
	private String id;
	private String username;
	private String password;

	private String firstname;
	private String middlename;
	private String lastname;
	private String nickname;

	private String position;

	private Date birthday;

	private List<String> locations;
	private String about;
	private String address;
	private List<String> phoneNumbers;
	private List<String> websites;
	private List<String> emails;

	private byte[] image;

	private List<TenantUserSubscription> subscribedTenants = new ArrayList<TenantUserSubscription>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		return ((User) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getWebsites() {
		return websites;
	}

	public void setWebsites(List<String> websites) {
		this.websites = websites;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<TenantUserSubscription> getSubscribedTenants() {
		return this.subscribedTenants;
	}

	public void setSubscribedTenants(List<TenantUserSubscription> subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}

	public void addSubscribedTenant(String tenantId, UserRole role) {
		if (!this.subscribedTenants.stream().map(t -> t.getTenantId()).collect(Collectors.toList())
				.contains(tenantId)) {
			this.subscribedTenants.add(new TenantUserSubscription(tenantId, role));
		}
	}

	public List<TenantUserSubscription> removeSubscribedTenant(String tenantId) {
		this.subscribedTenants.removeIf(s -> s.getTenantId() == tenantId);
		return this.subscribedTenants;
	}
}
