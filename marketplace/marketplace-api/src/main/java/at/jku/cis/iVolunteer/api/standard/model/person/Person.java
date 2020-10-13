package at.jku.cis.iVolunteer.api.standard.model.person;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {
	

	@Id
	private String id;
	private String ID;

	private String lastname;
	private String firstname;
	private String birthday;
	private String photo;
	private ContactDetail[] contactDetails;

	private String iVolunteerSource;

	public Person() {
	}

	public Person(String ID, String lastname, String firstname, String birthday, String photo,
			ContactDetail[] contactDetails, String iVolunteerSource) {
		super();
		this.setID(ID);
		this.lastname = lastname;
		this.firstname = firstname;
		this.birthday = birthday;
		this.photo = photo;
		this.contactDetails = contactDetails;
		this.iVolunteerSource = iVolunteerSource;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public ContactDetail[] getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetail[] contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getiVolunteerSource() {
		return iVolunteerSource;
	}

	public void setiVolunteerSource(String iVolunteerSource) {
		this.iVolunteerSource = iVolunteerSource;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
