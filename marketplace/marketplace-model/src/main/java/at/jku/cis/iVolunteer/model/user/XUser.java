package at.jku.cis.iVolunteer.model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XUser {
	private String id;
	private String username;

	private String titleBefore;
	private String firstName;
	private String lastName;
	private String titleAfter;

	private Date birthDate;

	private XAddress address;
	private String email;

	private List<String> phoneNumbers = new ArrayList<>(3);

	private String profileImagePath;

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

	public String getTitleBefore() {
		return titleBefore;
	}

	public void setTitleBefore(String titleBefore) {
		this.titleBefore = titleBefore;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public void setTitleAfter(String titleAfter) {
		this.titleAfter = titleAfter;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthday) {
		this.birthDate = birthday;
	}

	public XAddress getAddress() {
		return address;
	}

	public void setAddress(XAddress address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

}
